package com.luoyx.hauyne.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类<code>MsStack</code>
 * 用于：TODO
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeignCallRecord {

    /**
     * 微服务名（即spring.application.name）
     */
    private String targetService;

    /**
     * 微服务主机
     */
    private String targetHost;

    /**
     * 微服务方法
     */
    private String targetMethod;

}
