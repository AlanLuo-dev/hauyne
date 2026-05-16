//package com.luoyx.hauyne.web.enumsupport.convert;
//
//import com.luoyx.hauyne.api.enumsupport.EnumSpec;
//import org.springframework.core.convert.TypeDescriptor;
//import org.springframework.core.convert.converter.ConditionalGenericConverter;
//
//import java.util.Objects;
//import java.util.Set;
//
//
//public class EnumSpecGenericConverter
//        implements ConditionalGenericConverter {
//
//    @Override
//    public Set<ConvertiblePair> getConvertibleTypes() {
//
//        return Set.of(
//                new ConvertiblePair(String.class, Enum.class)
//        );
//    }
//
//    @Override
//    public boolean matches(TypeDescriptor sourceType,
//                           TypeDescriptor targetType) {
//
//        return EnumSpec.class.isAssignableFrom(
//                targetType.getType()
//        );
//    }
//
//    @Override
//    public Object convert(
//            Object source,
//            TypeDescriptor sourceType,
//            TypeDescriptor targetType) {
//
//        if (source == null) {
//            return null;
//        }
//
//        Class<?> enumType = targetType.getType();
//
//        Object[] enumConstants = enumType.getEnumConstants();
//
//        for (Object constant : enumConstants) {
//
//            EnumSpec<?, ?> enumSpec =
//                    (EnumSpec<?, ?>) constant;
//
//            if (Objects.equals(
//                    Objects.toString(enumSpec.getValue()),
//                    source.toString())) {
//
//                return constant;
//            }
//        }
//
//        return null;
//    }
//}
