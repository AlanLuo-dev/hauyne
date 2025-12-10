package com.luoyx.hauyne.web.me.swagger;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.WildcardType;
import java.util.Optional;

/**
 * 为方法参数（query/path/header）中的 CodeEnum 添加 enum 列表 & 描述
 */
//@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 尽早执行，保证能覆盖 schema
public class EnumParameterCustomizer implements ParameterCustomizer, CodeEnumResolver {

    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        if (parameterModel == null || methodParameter == null) {
            return parameterModel;
        }

        Class<?> rawClass = resolveParameterClass(methodParameter.getGenericParameterType());

        if (rawClass == null) {
            rawClass = methodParameter.getParameterType();
        }

        // 处理数组类型
        if (rawClass.isArray()) {
            rawClass = rawClass.getComponentType();
        }

        if (isCodeEnum(rawClass)) {
            Schema<?> schema = parameterModel.getSchema();
            if (schema == null) {
                schema = new Schema<>();
                parameterModel.setSchema(schema);
            }
            fillCodeEnumSchema(schema, rawClass);

            // 也把说明加入到 parameter 的 description（一些 UI/模板会显示 parameter.description）
            String existing = Optional.ofNullable(parameterModel.getDescription()).orElse("");
            String enumDesc = schema.getDescription();

            // 关键：避免重复追加
            if (!existing.contains(enumDesc)) {
                parameterModel.setDescription(existing + enumDesc);
            }

        }

        return parameterModel;
    }

    /**
     * 简单尝试解析泛型/参数化类型以获得真实的 Class（用于处理 Optional<TestCodeEnum> / List<TestCodeEnum> 等场景）
     */
    private Class<?> resolveParameterClass(Type type) {
        if (type instanceof Class<?> c) {
            return c;
        }
        if (type instanceof ParameterizedType pt) {
            Type raw = pt.getRawType();
            // 常见：Optional<TestCodeEnum> / List<TestCodeEnum>
            Type[] args = pt.getActualTypeArguments();
            if (args != null && args.length > 0) {
                Type first = args[0];
                if (first instanceof Class<?> fc) {
                    return fc;
                }
                if (first instanceof ParameterizedType fpt && fpt.getRawType() instanceof Class<?> frc) {
                    return frc;
                }
            }
            if (raw instanceof Class<?> rc) {
                return rc;
            }
        }
        if (type instanceof GenericArrayType gat) {
            Type comp = gat.getGenericComponentType();
            if (comp instanceof Class<?> cc) {
                return cc;
            }
        }
        if (type instanceof WildcardType wt) {
            Type[] upper = wt.getUpperBounds();
            if (upper != null && upper.length > 0 && upper[0] instanceof Class<?> uc) {
                return uc;
            }
        }
        return null;
    }
}
