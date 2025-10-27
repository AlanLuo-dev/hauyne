package com.luoyx.hauyne.audit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列 return回调
 * 每个RabbitTemplate只能配置一个ReturnCallback，因此需要在项目启动过程中配置
 *
 * @author Alan.Luo
 */
@Slf4j
@Configuration
public class RabbitMQReturnCallback implements ApplicationContextAware {
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 设置ReturnCallback
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            /**
             * Returned message callback.
             *
             * @param returned the returned message and metadata.
             */
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.info("收到消息的Return Callback, 交换机: {}, 路由键: {}, 消息: {}, 回复码: {}, 回复文本: {}",
                        returned.getExchange(),
                        returned.getRoutingKey(),
                        returned.getMessage(),
                        returned.getReplyCode(),
                        returned.getReplyText()
                );
            }
        });
    }
}
