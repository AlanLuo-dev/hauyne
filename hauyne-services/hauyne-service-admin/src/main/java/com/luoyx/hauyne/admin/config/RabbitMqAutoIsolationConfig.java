//package com.luoyx.hauyne.admin.config;
//
//import org.apache.hc.client5.http.classic.methods.HttpPut;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
//import org.apache.hc.client5.http.impl.classic.HttpClients;
//import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetAddress;
//import java.nio.charset.StandardCharsets;
//
//@Configuration
//public class RabbitMqAutoIsolationConfig {
//
//    // 共享 RabbitMQ 服务器信息
//    @Value("${spring.rabbitmq.host}")
//    private String rabbitHost;
//
//    @Value("${spring.rabbitmq.port}")
//    private int rabbitPort;
//
//    @Value("${spring.rabbitmq.admin-port}")
//    private int adminPort;
//
//    @Value("${spring.rabbitmq.admin-username}")
//    private String adminUsername;
//
//    @Value("${spring.rabbitmq.admin-password}")
//    private String adminPassword;
//
//    // 动态获取本地电脑名作为隔离标识（核心）
//    private String getLocalHostName() {
//        try {
//            // 获取本机电脑名（如 "zhangsan-pc"、"lisi-mac"）
//            return InetAddress.getLocalHost().getHostName();
//        } catch (Exception e) {
//            // 异常时默认使用 "unknown-dev"（避免启动失败）
//            return "unknown-dev";
//        }
//    }
//
//    // 生成专属虚拟主机名称（格式：/dev_电脑名）
//    private String getVirtualHost() {
//        return "/dev_" + getLocalHostName().replaceAll("[^a-zA-Z0-9_]", "_"); // 替换特殊字符
//    }
//
//    // 生成专属用户名（与电脑名一致，便于识别）
//    private String getUsername() {
//        return "dev_" + getLocalHostName().replaceAll("[^a-zA-Z0-9_]", "_");
//    }
//
//    // 生成随机密码（固定规则，避免每次启动变化）
//    private String getPassword() {
//        // 简单加密电脑名作为密码（实际可更复杂）
//        return "pwd_" + getLocalHostName().hashCode();
//    }
//
//    // 调用 RabbitMQ HTTP API 创建虚拟主机
//    private void createVirtualHost() throws Exception {
//        String vhost = getVirtualHost();
//        String url = "http://" + rabbitHost + ":" + adminPort + "/api/vhosts/" + vhost;
//
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPut httpPut = new HttpPut(url);
//            // 管理员账号认证
//            httpPut.addHeader("Authorization", "Basic " +
//                    java.util.Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes()));
//            // 发送请求（创建 VH 无需请求体）
//            client.execute(httpPut, response -> {
//                int statusCode = response.getCode();
//                // 201=创建成功，409=已存在（忽略）
//                if (statusCode != 201 && statusCode != 409) {
//                    throw new RuntimeException("创建虚拟主机失败：" + statusCode);
//                }
//                return response;
//            });
//        }
//    }
//
//    // 调用 RabbitMQ API 创建用户并分配 VH 权限
//    private void createUserAndPermission() throws Exception {
//        String username = getUsername();
//        String password = getPassword();
//        String vhost = getVirtualHost();
//
//        // 1. 创建用户
//        String userUrl = "http://" + rabbitHost + ":" + adminPort + "/api/users/" + username;
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPut httpPut = new HttpPut(userUrl);
//            httpPut.addHeader("Authorization", "Basic " +
//                    java.util.Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes()));
//            httpPut.addHeader("Content-Type", "application/json");
//            // 用户信息：密码、角色（仅需访问自己的 VH，赋予 minimal 角色即可）
//            String userJson = "{\"password\":\"" + password + "\", \"tags\":\"minimal\"}";
//            httpPut.setEntity(new StringEntity(userJson, StandardCharsets.UTF_8));
//
//            client.execute(httpPut, response -> {
//                int statusCode = response.getCode();
//                if (statusCode != 201 && statusCode != 409) { // 409=用户已存在
//                    throw new RuntimeException("创建用户失败：" + statusCode);
//                }
//                return response;
//            });
//        }
//
//        // 2. 分配用户对 VH 的权限（配置、写、读全权限）
//        String permUrl = "http://" + rabbitHost + ":" + adminPort + "/api/permissions/" + vhost + "/" + username;
//        HttpPut permPut = new HttpPut(permUrl);
//        permPut.addHeader("Authorization", "Basic " +
//                java.util.Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes()));
//        permPut.addHeader("Content-Type", "application/json");
//        // 权限配置：配置权限、写权限、读权限（.* 表示所有资源）
//        String permJson = "{\"configure\":\".*\", \"write\":\".*\", \"read\":\".*\"}";
//        permPut.setEntity(new StringEntity(permJson, StandardCharsets.UTF_8));
//
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            client.execute(permPut, response -> {
//                int statusCode = response.getCode();
//                if (statusCode != 201 && statusCode != 409) { // 409=权限已配置
//                    throw new RuntimeException("分配权限失败：" + statusCode);
//                }
//                return response;
//            });
//        }
//    }
//
//    // 动态配置 RabbitMQ 连接工厂（核心：连接到专属 VH）
//    @Bean
//    public ConnectionFactory connectionFactory() throws Exception {
//        // 1. 自动创建 VH、用户和权限（首次启动时执行，后续存在则跳过）
//        createVirtualHost();
//        createUserAndPermission();
//
//        // 2. 配置连接到专属 VH
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost(rabbitHost);
//        factory.setPort(rabbitPort);
//        factory.setVirtualHost(getVirtualHost()); // 动态设置为专属 VH
//        factory.setUsername(getUsername());     // 动态设置为专属用户
//        factory.setPassword(getPassword());     // 动态设置密码
//
//        return factory;
//    }
//}
