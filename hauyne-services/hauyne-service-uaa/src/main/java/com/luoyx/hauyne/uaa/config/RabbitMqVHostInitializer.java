/*
package com.luoyx.hauyne.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class RabbitMqVHostInitializer implements ApplicationRunner {

//    @Value("${rabbitmq.admin.username}")
//    private String username;
//
//    @Value("${rabbitmq.admin.password}")
//    private String password;
//
//    @Value("${rabbitmq.host}")
//    private String host;
//
    @Value("${rabbitmq.vhost}")
    private String vhost;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Override
    public void run(ApplicationArguments args) {
        try {
            String url = "https://" + rabbitProperties.getHost() + "/api/vhosts/"
                    + URLEncoder.encode(vhost, "UTF-8");

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            String auth = rabbitProperties.getUsername() + ":" + rabbitProperties.getPassword();
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
            connection.setRequestMethod("PUT"); // PUT 会自动创建 VHost
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == 201 || responseCode == 204) {
                System.out.println("✅ Virtual host created successfully: " + vhost);
            } else if (responseCode == 400 || responseCode == 403) {
                System.err.println("❌ Permission denied or bad request when creating vhost");
            } else if (responseCode == 404) {
                System.err.println("❌ RabbitMQ HTTP API not reachable. Check management plugin and host config.");
            } else {
                System.err.println("❌ Failed to create virtual host: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
