package com.luoyx.hauyne.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.luoyx.hauyne.mybatisplus.injector.MySqlInjector;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.LogicDeleteProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author 罗英雄
 * 经测试，@MapperScan可以支持通配符，*匹配任意一层目录，**匹配任意多层目录
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LogicDeleteProperties.class)
@MapperScan("com.luoyx.hauyne.**.mapper")
public class MybatisPlusAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = MetaObjectHandler.class)
    public MetaObjectHandler metaObjectHandler() {
        MetaObjectHandler auditMetaObjectHandler = new AuditMetaObjectHandler();
        log.info("MyBatis-Plus配置: 已创建自动审计Bean -> {}", AuditMetaObjectHandler.class);

        return auditMetaObjectHandler;
    }

    @Bean
    public ISqlInjector sqlInjector(LogicDeleteProperties logicDeleteProperties) {
        ISqlInjector sqlInjector = new MySqlInjector(logicDeleteProperties);
        log.info("MyBatis-Plus配置: 已自定义Mapper通用方法 -> {}", MySqlInjector.class);

        return sqlInjector;
    }
}
