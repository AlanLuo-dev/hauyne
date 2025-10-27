//package com.luoyx.hauyne.message.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
///**
// * @author 罗英雄
// * @date 2021/9/9 16:44
// */
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Autowired
//    private UserInfoTokenServices userInfoTokenServices;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic", "/queue");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                // WebSocket连接时进行token验证
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String login = accessor.getFirstNativeHeader("login");
//                    if (login == null || !login.equals("false")) {
//                        String accessToken = accessor.getFirstNativeHeader("access_token");
//                        OAuth2Authentication authentication = userInfoTokenServices.loadAuthentication(accessToken);
//                        if (!ObjectUtils.isEmpty(authentication)) {
//                            accessor.setUser(authentication);
//                        }
//                    }
//                }
//
//                return message;
//            }
//        });
//    }
//}
