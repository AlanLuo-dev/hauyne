package com.luoyx.hauyne.audit.config;

import com.luoyx.hauyne.audit.api.constant.MQConstant;
import com.luoyx.hauyne.audit.base.ExchangeName;
import com.luoyx.hauyne.audit.base.QueueNames;
import com.luoyx.hauyne.audit.base.RoutingKeyName;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.luoyx.hauyne.admin.api.sys.constant.MQConstant.USER_SNAPSHOT_FANOUT_EXCHANGE;

/**
 * 消息队列配置
 */
@Configuration
public class QueueConfig {

    /**
     * 默认交换机
     *
     * @return 交换机
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(ExchangeName.DEFAULT.name());
    }

    /**
     * 通知消息交换机
     *
     * @return 交换机
     */
    @Bean
    public DirectExchange notifyMsgDirectExchange() {
        return new DirectExchange(ExchangeName.NOTIFY_MSG.name());
    }

    /**
     * 通知消息队列
     *
     * @return 队列
     */
    @Bean
    public Queue notifyMsgQueue() {
        return new Queue(QueueNames.NOTIFY_MSG_QUEUE, true);
    }

    @Bean
    public Binding notifyMsgQueueBinding() {
        return BindingBuilder
                .bind(notifyMsgQueue())
                .to(notifyMsgDirectExchange())
                .with(RoutingKeyName.NOTIFY_MSG);
    }


    /*~ 审计日志队列 配置 START ***************************************************************************/
    @Bean
    public DirectExchange auditExchange() {
        return new DirectExchange(MQConstant.AUDIT_EXCHANGE);
    }

    /**
     * 通知消息队列
     *
     * @return 队列
     */
    @Bean
    public Queue auditQueue() {
        return new Queue(MQConstant.AUDIT_QUEUE, true);
    }

    @Bean
    public Binding auditQueueBinding() {
        return BindingBuilder
                .bind(auditQueue())
                .to(auditExchange())
                .with(MQConstant.AUDIT_ROUTING);
    }
    /*~ 审计日志队列 配置 END   ***************************************************************************/

    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange("test.exchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue");
    }

    @Bean
    public Binding testQueueBinding() {
        return BindingBuilder
                .bind(testQueue())
                .to(testExchange())
                .with("test.routing");
    }

}
