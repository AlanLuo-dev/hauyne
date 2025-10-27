/**
 * ============================================================================
 * 用户快照同步模块（User Snapshot Module）
 * ============================================================================
 *
 * <p>功能描述：
 * 本包提供微服务之间同步用户快照的核心功能，封装了消息消费、快照同步逻辑及相关基础类型，
 * 支持通过 RabbitMQ FANOUT 交换机将用户信息（id、真实姓名、昵称、头像、更新时间）广播到
 * 多个微服务，并统一入库。
 *
 * <p>核心特性：
 * <ul>
 *     <li>消息消费：提供统一的 RabbitMQ 消费者监听用户快照队列</li>
 *     <li>快照同步：提供抽象 Service，实现幂等同步逻辑（新增或更新快照）</li>
 *     <li>抽象基类：BaseUserSnapshot、AbstractUserSnapshotService，方便业务微服务继承复用</li>
 * </ul>
 *
 * <p>模块结构：
 * <pre>
 * com.luoyx.hauyne.usersnapshot
 * ├── amqp             # RabbitMQ 消费者类
 * ├── autoconfigure    # 自动配置类、Spring Boot Starter 配置
 * ├── converter        # DTO 与实体类的Bean转换器
 * ├── entity           # 基础实体 BaseUserSnapshot
 * ├── msg              # MQ 消息类 UserSnapshotMessage
 * └── service          # 抽象 Service 接口与实现类 AbstractUserSnapshotService
 * </pre>
 *
 * <p>使用说明：
 * <ol>
 *     <li>在微服务中引入本模块依赖</li>
 *     <li>定义业务实体类继承 BaseUserSnapshot</li>
 *     <li>定义 Mapper 接口继承 MyBatis-Plus BaseMapper</li>
 *     <li>编写业务 Service 实现类继承 AbstractUserSnapshotService</li>
 *     <li>通过 RabbitMQ FANOUT 交换机发送 UserSnapshotMessage，实现微服务间同步</li>
 * </ol>
 *
 * <p>注意事项：
 * <ul>
 *     <li>本模块提供的是半成品封装，业务侧仍需提供实体、Mapper 和 Service 实现</li>
 *     <li>RabbitMQ 配置需保证交换机类型为 FANOUT，队列绑定正确</li>
 *     <li>使用 MapStruct 时，注意不要使用泛型方法，否则会编译失败</li>
 *     <li>建议所有微服务使用统一的 UserSnapshotMessage DTO 格式</li>
 * </ul>
 *
 * @author Alan
 * @since 2025-09-24
 */

package com.luoyx.hauyne.usersnapshot;