//package com.luoyx.hauyne.security.util;
//
//import lombok.Getter;
//import lombok.Setter;
////import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * OAuth2 properties define properties for OAuth2-based microservices.
// * OAuth2属性定义了基于OAuth2的微服务的属性。
// *
// * @author copy
// */
//@Component
////@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
//public class OAuth2Properties {
//    private WebClientConfiguration webClientConfiguration = new WebClientConfiguration();
//
//    private SignatureVerification signatureVerification = new SignatureVerification();
//
//    public WebClientConfiguration getWebClientConfiguration() {
//        return webClientConfiguration;
//    }
//
//    public SignatureVerification getSignatureVerification() {
//        return signatureVerification;
//    }
//
//    @Getter
//    @Setter
//    public static class WebClientConfiguration {
//
//        /**
//         * 客户端id
//         */
//        private String clientId = "web_app";
//
//        /**
//         * 密钥
//         */
//        private String secret = "changeit";
//
//        /**
//         * Holds the session timeout in seconds for non-remember-me sessions.
//         * After so many seconds of inactivity, the session will be terminated.
//         * Only checked during token refresh, so long access token validity may
//         * delay the session timeout accordingly.
//         * <p>
//         * 非记住我会话的会话超时时间（以秒为单位）。
//         * 在多少秒的不活动后，会话将被终止。
//         * 仅在令牌刷新期间进行检查，因此长时间的访问令牌有效性可能会延迟会话超时时间。
//         */
//        private int sessionTimeoutInSeconds = 1800;
//
//        /**
//         * 定义Cookie的域。如果指定，则将在该域上设置Cookie。
//         * 如果未配置，则将在发送请求的顶级域上设置Cookie。
//         * 例如，如果您发送请求到<code>app1.your-domain.com</code>，
//         * 则Cookie将设置在<code>.your-domain.com</code>上，以使其对<code>app2.your-domain.com</code>也有效。
//         */
//        private String cookieDomain;
//    }
//
//    /**
//     * 签名验证配置
//     */
//    @Getter
//    @Setter
//    public static class SignatureVerification {
//
//        /**
//         * Maximum refresh rate for public keys in ms.
//         * We won't fetch new public keys any faster than that to avoid spamming UAA in case
//         * we receive a lot of "illegal" tokens.
//         * <p>
//         * 公钥刷新速率上限（以毫秒为单位）。
//         * 为了避免在收到大量“非法”令牌时向UAA发送过多的请求，我们不会超过该速率获取新的公钥。
//         */
//        private long publicKeyRefreshRateLimit = 10 * 1000L;
//
//        /**
//         * Maximum TTL for the public key in ms.
//         * The public key will be fetched again from UAA if it gets older than that.
//         * That way, we make sure that we get the newest keys always in case they are updated there.
//         * <p>
//         * 公钥的最大存活时间（以毫秒为单位）。
//         * 如果公钥的年龄超过该值，将再次从UAA获取。
//         * 通过这种方式，我们确保始终获取最新的密钥，以防它们在UAA中更新。
//         */
//        private long ttl = 24 * 60 * 60 * 1000L;
//
//        /**
//         * Endpoint where to retrieve the public key used to verify token signatures.
//         * <p>
//         * 用于获取用于验证令牌签名的公钥的端点
//         */
//        private String publicKeyEndpointUri = "http://uaa/oauth/token_key";
//    }
//}
