package com.luoyx.hauyne.web.enumsupport.validate;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

public class EnumSpecStartupValidator implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String basePackage = application.getMainApplicationClass().getPackageName();
        scanAndValidate(basePackage);
    }

    private void scanAndValidate(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(EnumSpec.class));
        Set<BeanDefinition> components = scanner.findCandidateComponents(basePackage);

        for (BeanDefinition bd : components) {
            try {

                Class<?> clazz = Class.forName(bd.getBeanClassName());

                if (clazz.isEnum()) {
                    EnumSpecValidator.validate((Class<? extends Enum>) clazz);
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}