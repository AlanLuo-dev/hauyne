package com.luoyx.hauyne;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;

/**
 * xx
 *
 * @Author 罗英雄
 * @Date 2023-04-08 10:32:07
 */
@SpringBootTest
public class AppTests {

    @Test
    public void createTemplate() throws IOException {

        // 创建模板加载器
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

        // 模板文件所在目录
        resolver.setPrefix("templates/java/");

        // 后缀
        resolver.setSuffix(".java.txt");
        resolver.setCharacterEncoding("UTF-8");

        // 不缓存
        resolver.setCacheable(false);


        /* ------------------------------------------------------------------  */

        // 创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();

        // 将加载器放入模板引擎
        templateEngine.setTemplateResolver(resolver);

        // 创建Context对象（存放Model）
        Context context = new Context();

        // 放入数据
        context.setVariable("controllerPackage", "com.luoyx.hauyne.controller");
        context.setVariable("servicePackage", "com.luoyx.hauyne.service");
        context.setVariable("javaTableName", "SysUser");

        // 创建字符输出流并且自定义输出文件的位置和文件名
        // 创建静态文件， “service”是模板的文件名称
        FileWriter writer = new FileWriter("/home/luoyingxiong/IdeaProjects/HAUYNE/SysUserService.java");
        templateEngine.process("service", context, writer);

        // 创建字符输出流并且自定义输出文件的位置和文件名
//        writer = new FileWriter("/home/luoyingxiong/IdeaProjects/HAUYNE/SysUserController.java");
//        templateEngine.process("controller", context, writer);

        System.out.println("执行完毕鸟。。。");
    }
}
