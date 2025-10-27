
任务一、@GetMapping(value = "/currentLoginedUser") 要迁移到MeController
而且前端代码的NgRx状态管理要重新适配数据结构

任务二、OpenFeign Starter获取 微服务名的逻辑要改
应该尝试从 PlatformFeignErrorDecoder 中获取微服务名，然后组装成错误信息，
设置到 MsStackHolder 中，然后在 MsStackHolder 中获取微服务名，然后组装成错误信息




# OAuth2 问题

在第一次调用（Redis 是空的）成功登录后，主动注销了，在服务运行期，我清空了Redis，然后再登录，我发现auth-server不会再以【客户端模式】去获取accessToken，于是被调用方的findByUserName直接返回401

自己找到的关键源码处：

org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor#getToken

————————————————————————————————

另： 有时候，登录成功，在浏览器看不到refresh token ， 是否和登录过程中的auth-server服务以客户端模式获取令牌 有关？？

### ChatGPT 说：

你发现的问题**非常关键**，本质上是：

> 清空 Redis 后，auth-server 在调用其它服务（比如 findByUserName）时，并没有自动重新获取 token，而是继续使用内存中已有的旧 token，结果导致调用失败（401 Unauthorized）。
>

---

### ✅ 发生了什么？

1. **登录成功后**，客户端模式（`client_credentials`）拿到了 token，Spring OAuth2 client 将其缓存了起来（同时也存入 Redis）。
2. 你调用 `/logout`，但客户端模式的 token 默认**不会被主动清除**（除非你显式做了清除）。
3. 然后你**清空 Redis**，于是 Redis 中再也找不到这个 token 的信息。
4. 当 `auth-server` 继续使用内存中旧 token 去访问 service-admin 时：
    - service-admin 使用 `RedisTokenStore` 校验 token；
    - 因 Redis 中没有该 token，对应认证信息找不到，直接返回 `401 Unauthorized`。

---

### ✅ 为什么不会重新获取 token？

因为 Spring Security OAuth2 的默认 `OAuth2RestTemplate` 或 WebClient 支持类使用了缓存逻辑：

- 它只在**没有可用 token**时才触发 `/oauth/token` 请求。
- **不会因为 token 在 Redis 失效了**就重新发起客户端模式的 token 获取。

换句话说：

> OAuth2 client 以为自己“还有 token”，但 resource server 却说“token 无效了”。
>

---

### ✅ 解决方案

你可以选择下面其中一种方式解决问题：

---

### ✅ **方案一：自定义 AccessTokenProvider，加入 Redis 校验逻辑**

> 在发起请求前检查当前 token 是否还存在 Redis 中，如果不存在就重新获取。
>

```java
java
复制编辑
public class RedisAwareAccessTokenProvider extends ClientCredentialsAccessTokenProvider {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisAwareAccessTokenProvider(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ClientContext context, OAuth2ProtectedResourceDetails resource)
            throws UserRedirectRequiredException {
        OAuth2AccessToken token = context.getAccessToken();
        if (token != null && token.getValue() != null) {
            // 你这里需要写出 Redis 中 token 存储的 key 规则
            String redisKey = "access:" + token.getValue();
            if (!redisTemplate.hasKey(redisKey)) {
                // Redis 中 token 已经没了，清空本地缓存
                context.setAccessToken(null);
            }
        }
        return super.obtainAccessToken(context, resource);
    }
}

```

然后在配置中用这个 Provider。

---

### ✅ **方案二：主动刷新 token 或每次都强制获取新 token（调试用）**

比如：

```java
java
复制编辑
OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails);
restTemplate.getOAuth2ClientContext().setAccessToken(null); // 强制清除现有 token

```

> 注意：这只适合调试或非常特殊的场景。
>

---

### ✅ **方案三：logout 时主动移除客户端模式 token**

这就需要你实现注销时同时清理：

- Redis 中相关的 token
- 本地 `OAuth2ClientContext` 中保存的 token（如 `SecurityContextHolder` 或 OAuth2RestTemplate 中的）

---

### ✅ 总结

| 问题 | 解释 |
| --- | --- |
| Redis 清空后仍返回 401 | 客户端仍在使用已丢失的旧 token |
| 为什么不会重新获取 token？ | OAuth2 client 认为旧 token 仍有效，未触发刷新逻辑 |
| 如何解决？ | 自定义 token provider 或在 token 缺失时强制刷新 |

---

如果你希望我帮你写一个完整的 Spring Boot 配置类来接入这个自定义 `AccessTokenProvider`，我可以立刻生成。需要吗？