package com.luoyx.hauyne.web.validation.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


/**
 * 参数校验配置
 *
 * @author 罗英雄
 */
@Slf4j
@Configuration
public class ValidatorAutoConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()

                //failFast的意思只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
                .failFast(true)
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        log.info("jakarta.validation配置: 已创建Bean -> {}", Validator.class);

        return validator;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        log.info("jakarta.validation配置: 已创建Bean -> {}", MethodValidationPostProcessor.class);

        return methodValidationPostProcessor;
    }
}
