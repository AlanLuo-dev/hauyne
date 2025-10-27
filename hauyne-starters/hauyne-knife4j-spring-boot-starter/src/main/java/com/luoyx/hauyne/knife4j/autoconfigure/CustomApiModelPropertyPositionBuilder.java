//package com.luoyx.hauyne.knife4j.autoconfigure;
//
//import com.fasterxml.jackson.databind.introspect.AnnotatedField;
//import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
//import io.swagger.annotations.ApiModelProperty;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
//import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
//import springfox.documentation.swagger.common.SwaggerPluginSupport;
//import springfox.documentation.swagger.schema.ApiModelProperties;
//
//import java.lang.reflect.Field;
//import java.util.Optional;
//
//import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
//
///**
// * 自定义Swagger2插件，使 @ApiModelProperty标记的字段按定义顺序显示（默认是按字段首写字母排序，很不友好）
// * <p>
// * 摘抄自：
// * https://icode.best/i/83757440799730
// * https://blog.csdn.net/weixin_44775299/article/details/116725564
// *
// * @author 罗英雄
// */
//public class CustomApiModelPropertyPositionBuilder implements ModelPropertyBuilderPlugin {
//
//    private static final Log log = LogFactory.getLog(CustomApiModelPropertyPositionBuilder.class);
//
//    @Override
//    public boolean supports(DocumentationType delimiter) {
//        return SwaggerPluginSupport.pluginDoesApply(delimiter);
//    }
//
//    @Override
//    public void apply(ModelPropertyContext context) {
//        Optional<BeanPropertyDefinition> beanPropertyDefinitionOpt = context.getBeanPropertyDefinition();
//        Optional<ApiModelProperty> annotation = Optional.empty();
//        if (context.getAnnotatedElement().isPresent()) {
//            annotation = Optional.of(annotation.orElseGet(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get())::get));
//        }
//        if (context.getBeanPropertyDefinition().isPresent()) {
//            annotation = Optional.of(annotation.orElseGet(findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class)::get));
//        }
//
//        if (beanPropertyDefinitionOpt.isPresent()) {
//            BeanPropertyDefinition beanPropertyDefinition = beanPropertyDefinitionOpt.get();
//            if (annotation.isPresent() && annotation.get().position() != 0) {
//                return;
//            }
//            AnnotatedField field = beanPropertyDefinition.getField();
//            Class<?> clazz = field.getDeclaringClass();
//            Field[] declaredFields = clazz.getDeclaredFields();
//            Field declaredField;
//            try {
//                declaredField = clazz.getDeclaredField(field.getName());
//            } catch (NoSuchFieldException | SecurityException e) {
//                log.error("", e);
//                return;
//            }
//            int indexOf = -1;
//            for (int i = 0; i < declaredFields.length; i++) {
//                if (declaredFields[i].equals(declaredField)) {
//                    indexOf = i;
//                    break;
//                }
//            }
//            if (indexOf != -1) {
//                context.getBuilder().position(indexOf);
//            }
//        }
//    }
//}
