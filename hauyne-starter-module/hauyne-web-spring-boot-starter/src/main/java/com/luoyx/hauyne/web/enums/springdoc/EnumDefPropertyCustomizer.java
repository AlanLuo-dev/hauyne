package com.luoyx.hauyne.web.enums.springdoc;

import com.fasterxml.jackson.databind.JavaType;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverterContextImpl;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumDefPropertyCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("rawtypes")
    public Schema customize(Schema schema, AnnotatedType annotatedType) {
        System.out.println("————————————————————————————————————————————————————————————————————————————");
        System.out.println("schame: " + schema.getDescription() + "  schema Type: " + schema.getType());
        if (annotatedType.getType() instanceof JavaType type && type.isEnumType() && isCodeEnum(type.getRawClass())) {
            List<EnumDefinition<? extends Serializable, ?>> enumConstants =
                    List.of((EnumDefinition<? extends Serializable, ?>[]) type.getRawClass().getEnumConstants());

            String description = enumConstants.stream()
                    .map(enumDef -> enumDef.getValue() + " = " + enumDef.getLabel())
                    .collect(Collectors.joining("，", "<b>（", "）</b>"));
            String existDescription = schema.getDescription();
            Optional<? extends Serializable> optional = enumConstants.stream().map(EnumDefinition::getValue).findFirst();
            if (optional.isPresent()) {
                Serializable serializable = optional.get();
                schema = PrimitiveType.createProperty(serializable.getClass());
                schema.setExample(serializable);
            }

            schema.setEnum(enumConstants.stream().map(EnumDefinition::getValue).toList());
            schema.setDescription(existDescription + description);


            Function<AnnotatedType, Schema> jsonUnwrappedHandler = annotatedType.getJsonUnwrappedHandler();
            if (Objects.isNull(jsonUnwrappedHandler)) {
                return schema;
            }
            ModelConverterContextImpl modelConverterContext = getArg3FromLambda(jsonUnwrappedHandler);
            HashSet<AnnotatedType> processedTypesFromContext = getProcessedTypesFromContext(modelConverterContext);

            boolean isResponseParam = isResponseParam(processedTypesFromContext);
            if (isResponseParam) {
                EnumDefinition<? extends Serializable, ?> enumConstant = enumConstants.stream().findFirst().orElse(null);
                if (Objects.nonNull(enumConstant)) {
                    schema = createObjectSchema(enumConstant, existDescription, description);
                }
            }
            return schema;
        }
        // ~ START  ====== 处理表单 集合/Array/Map请求参数的场景，合并items的枚举描述到顶层 ===============
        formRequestContainerParameterHandler(schema, annotatedType);
        // ~ END    ====== 处理表单 集合/Array/Map请求参数的场景，合并items的枚举描述到顶层 ===============

        // ~ START ========= 为返回参数的枚举的 Schema 实现对象化（即转为 ObjectSchema） ========================================
        Function<AnnotatedType, Schema> jsonUnwrappedHandler = annotatedType.getJsonUnwrappedHandler();
        if (Objects.isNull(jsonUnwrappedHandler)) {
            return schema;
        }
        ModelConverterContextImpl modelConverterContext = getArg3FromLambda(jsonUnwrappedHandler);
        HashSet<AnnotatedType> processedTypesFromContext = getProcessedTypesFromContext(modelConverterContext);
        Map<Schema, List<EnumDefinition<? extends Serializable, ?>>> schemaEnumMap = new HashMap<>();

        // 收集 枚举类型的字段的 schema 和 枚举的对应关系，组成HashMap
        for (AnnotatedType _annotatedType : processedTypesFromContext) {
            if (_annotatedType.getType() instanceof JavaType type
                    && type.isEnumType()
                    && isCodeEnum(type.getRawClass())
                    && modelConverterContext != null) {
                Schema resolve = modelConverterContext.resolve(_annotatedType);
                schemaEnumMap.put(resolve, List.of((EnumDefinition<? extends Serializable, ?>[]) type.getRawClass().getEnumConstants()));
            }
        }

        // ----------------------------------------------------------------
        // 为返回参数的 example 执行对象化
        if (isResponseParam(processedTypesFromContext)) {
            final String existDescription = schema.getDescription();
            Schema existItems = schema.getItems();
            if (schemaEnumMap.containsKey(existItems)) {
                List<EnumDefinition<? extends Serializable, ?>> enumConstants = schemaEnumMap.get(existItems);
                Optional<EnumDefinition<? extends Serializable, ?>> optional = enumConstants.stream().findFirst();
                if (optional.isPresent()) {
                    String enumDesc = existItems.getDescription().substring(existItems.getDescription().indexOf("<b>（"));

                    final Schema newItems = createObjectSchema(optional.get(), existDescription, enumDesc);
                    schema.items(newItems);
                }
            }
        }
        // ~ END ========= 为返回参数的枚举的 Schema 实现对象化（即转为 ObjectSchema） ========================================

        return schema;
    }


    /**
     * 处理表单 集合/Array/Map请求参数的场景
     *
     * @param annotatedType AnnotatedType 实例
     */
    private void formRequestContainerParameterHandler(Schema schema, AnnotatedType annotatedType) {

        // 参数类型是 数组/Collection/Map
        final JavaType content;
        if (annotatedType.getType() instanceof JavaType type
                && type.isContainerType()
                && (content = type.getContentType()) != null
                && content.isEnumType()
                && EnumDefinition.class.isAssignableFrom(content.getRawClass())
        ) {

            Function<AnnotatedType, Schema> jsonUnwrappedHandler = annotatedType.getJsonUnwrappedHandler();
            if (Objects.nonNull(jsonUnwrappedHandler)) {
                ModelConverterContextImpl modelConverterContext = getArg3FromLambda(jsonUnwrappedHandler);
                HashSet<AnnotatedType> processedTypes = getProcessedTypesFromContext(modelConverterContext);

                boolean isJsonRequest = hasAnnotation(processedTypes, RequestBody.class);
                boolean isFormRequest = hasAnnotation(processedTypes, Parameter.class) && !isJsonRequest;

                // 只针对 表单 请求参数
                if (isFormRequest) {
                    Schema itemsSchema = schema.getItems();
                    // 检查 items 是否是枚举类型且包含拼接的枚举描述
                    if (itemsSchema != null && itemsSchema.getDescription() != null
                            && itemsSchema.getDescription().contains("<b>（")
                            && schema.getDescription() != null ) {
                        // 将items的枚举描述合并到数组字段的顶层description
                        String enumDesc = itemsSchema.getDescription().substring(
                                itemsSchema.getDescription().indexOf("<b>（"));
                        schema.setDescription(schema.getDescription() + enumDesc);
                    }
                }
            }

        }
    }

    private boolean hasAnnotation(Set<AnnotatedType> types, Class<? extends Annotation> annoClass) {
        for (AnnotatedType at : types) {
            for (Annotation a : at.getCtxAnnotations()) {
                if (annoClass.isInstance(a)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 创建 对象化的 Schema
     *
     * @param enumConstant      枚举
     * @param existiDescription 已存在的描述
     * @param description       枚举描述
     * @return ObjectSchema 对象
     */
    private Schema createObjectSchema(EnumDefinition<? extends Serializable, ?> enumConstant,
                                      String existiDescription,
                                      String description) {
        Schema items = new ObjectSchema();

        Schema valueSchema = PrimitiveType.createProperty(enumConstant.getValue().getClass());
        valueSchema.setDescription("枚举实际值" + description);
        valueSchema.setExample(enumConstant.getValue());

        items.addProperty("value", valueSchema);

        Schema labelSchema = new StringSchema();
        labelSchema.setDescription("枚举标签");
        labelSchema.setExample(enumConstant.getLabel());

        items.addProperty("label", labelSchema);
        items.setDescription(existiDescription);

        return items;
    }

    private boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口(更直接、更可靠)
        return EnumDefinition.class.isAssignableFrom(rawClass);
    }

    /**
     * 从 Lambda 表达式（jsonUnwrappedHandler）中反射获取 arg$3 字段（ModelConverterContextImpl 实例）
     */
    private ModelConverterContextImpl getArg3FromLambda(Function<AnnotatedType, Schema> lambdaInstance) {
        Class<?> lambdaClass = lambdaInstance.getClass();
        Field arg3Field = null;
        for (Field field : lambdaClass.getDeclaredFields()) {   // 遍历所有声明字段（包括合成字段、私有字段，必须用 getDeclaredFields()，不能用 getFields()）
            if ("arg$3".equals(field.getName())) {              // 匹配字段名 arg$3（注意：字段名是固定的，与你调试观察一致）
                arg3Field = field;
                break;
            }
        }
        if (arg3Field != null) {
            arg3Field.setAccessible(true);                          // 暴力突破访问权限检查（关键：私有字段必须设置 setAccessible(true)）

            // 获取字段值并强转为 ModelConverterContextImpl
            try {
                Object fieldValue = arg3Field.get(lambdaInstance);
                if (fieldValue instanceof ModelConverterContextImpl) {
                    return (ModelConverterContextImpl) fieldValue;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 从 ModelConverterContextImpl 中反射获取 processedTypes 字段
     */
    private HashSet<AnnotatedType> getProcessedTypesFromContext(ModelConverterContextImpl context) {
        try {
            Field processedTypesField = ModelConverterContextImpl.class.getDeclaredField("processedTypes");

            // 同样需要突破访问权限
            processedTypesField.setAccessible(true);
            Object fieldValue = processedTypesField.get(context);

            if (fieldValue instanceof HashSet) {
                return (HashSet<AnnotatedType>) fieldValue;
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return new HashSet<>(Collections.emptySet());
    }

    /**
     * 判断当前 AnnotatedType 是否是返回参数
     *
     * @param processedTypesFromContext 已处理的 AnnotatedType 集合
     * @return true = 当前Schema是返回参数，false = 当前Schema是请求参数
     */
    private boolean isResponseParam(HashSet<AnnotatedType> processedTypesFromContext) {
        // 返回参数标识：true = 当前Schema是返回参数，false = 当前Schema是请求参数
        boolean isResponseParam = true;
        outerLoop:
        // 定义外层循环标签
        for (AnnotatedType _annotatedType : processedTypesFromContext) {
            for (Annotation ctxAnnotation : _annotatedType.getCtxAnnotations()) {
                if (ctxAnnotation instanceof RequestBody
                        || ctxAnnotation instanceof Parameter
                        || ctxAnnotation instanceof Validated) {
                    isResponseParam = false;
                    break outerLoop; // 跳出外层循环
                }
            }
        }

        return isResponseParam;
    }
}
