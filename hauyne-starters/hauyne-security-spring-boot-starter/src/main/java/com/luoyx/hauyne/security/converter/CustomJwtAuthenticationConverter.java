//package com.luoyx.hauyne.security.converter;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        // 获取自定义的权限字段
//        Collection<String> authorities = jwt.getClaimAsStringList("authorities");
//
//        if (authorities == null) {
//            authorities = List.of(); // 默认空权限
//        }
//
//        // 将权限字符串转换为 GrantedAuthority
//        return authorities.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//}
