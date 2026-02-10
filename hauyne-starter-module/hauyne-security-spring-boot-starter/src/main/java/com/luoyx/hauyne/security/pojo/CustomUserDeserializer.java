package com.luoyx.hauyne.security.pojo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.luoyx.hauyne.security.context.UserContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @link https://blog.csdn.net/m13012606980/article/details/125291005
 * @author luoyingxiong
 */
public class CustomUserDeserializer extends JsonDeserializer<User> {

    private static final TypeReference<Set<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Set<SimpleGrantedAuthority>>() {
    };

    /**
     * This method will create {@link User} object. It will ensure successful object
     * creation even if password key is null in serialized json, because credentials
     * may be removed from the {@link User} by invoking
     * {@link User#eraseCredentials()}. In that case there won't be any password key
     * in serialized json.
     *
     * @param jp   the JsonParser
     * @param ctxt the DeserializationContext
     * @return the user
     * @throws IOException             if a exception during IO occurs
     * @throws JsonProcessingException if an error during JSON processing occurs
     */
    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        // Map对应授权服务器的UserDetails.(在此Demo中，UserDetails的实现为CurrentSysUser,Map中必然包含该类的字段，挨个取出，构建实例。)
        Collection<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
                SIMPLE_GRANTED_AUTHORITY_SET);

        final Long id = readJsonNode(jsonNode, "id").asLong();
        UserContextHolder.setUserId(id);
        String username = readJsonNode(jsonNode, "username").asText();

//        boolean enabled = map.get("enabled") != null && (boolean) map.get("enabled");
//        boolean accountNonExpired = map.get("accountNonExpired") != null && (boolean) map.get("accountNonExpired");
//        boolean credentialsNonExpired = map.get("credentialsNonExpired") != null && (boolean) map.get("credentialsNonExpired");
//        boolean accountNonLocked = map.get("accountNonLocked") != null && (boolean) map.get("accountNonLocked");

        boolean enabled = readJsonNode(jsonNode, "enabled").asBoolean();
        boolean accountNonExpired = readJsonNode(jsonNode, "accountNonExpired").asBoolean();
        boolean credentialsNonExpired = readJsonNode(jsonNode, "credentialsNonExpired").asBoolean();
        boolean accountNonLocked = readJsonNode(jsonNode, "accountNonLocked").asBoolean();

        String nickname = readJsonNode(jsonNode, "nickname").asText();
        String realName = readJsonNode(jsonNode, "realName").asText();
        String avatar = readJsonNode(jsonNode, "avatar").asText();
        String phone = readJsonNode(jsonNode, "phone").asText();
        String email = readJsonNode(jsonNode, "email").asText();
        String position = readJsonNode(jsonNode, "position").asText();
        String department = readJsonNode(jsonNode, "department").asText();
        List<Menu> menus = mapper.convertValue(jsonNode.get("menus"), new TypeReference<List<Menu>>() {
        });

        CurrentSysUser currentSysUser = new CurrentSysUser(
                username,
                "N/A",
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                new HashSet<>(authorities),
                id,
                nickname,
                realName,
                avatar,
                phone,
                email,
                position,
                department,
                menus
        );

        return currentSysUser;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
