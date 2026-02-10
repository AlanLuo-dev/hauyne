package com.luoyx.hauyne.common.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 罗英雄
 */
@Getter
@Setter
public class RequestInfo {

    /**
     * ip地址
     */
    private String ip;

    /**
     * URL
     */
    private String url;

    /**
     * http方法
     */
    private String httpMethod;

    /**
     * 类方法
     */
    private String classMethod;

    /**
     * 请求参数
     */
    private Object requestParams;

    /**
     * 结果
     */
    private Object result;

    /**
     * 耗时
     */
    private Long timeCost;
}
