package com.luoyx.hauyne.web.handler;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeignLogMessageResolver {

    private static final Map<Integer, String> STATUS_MESSAGES;

    static {
        Map<Integer, String> map = new HashMap<>();

        // 4xx 客户端错误
        map.put(400, "Feign请求被下游拒绝（400 - 请求参数错误）");
        map.put(401, "Feign请求被下游拒绝（401 - 未授权访问）");
        map.put(403, "Feign请求被下游拒绝（403 - 无访问权限）");
        map.put(404, "Feign请求被下游拒绝（404 - 资源不存在）");

        // 5xx 服务器错误
        map.put(500, "Feign调用失败：下游服务内部异常（500）");
        map.put(502, "Feign调用失败：下游服务网关错误（502）");
        map.put(503, "Feign调用失败：下游服务不可用（503）");
        map.put(504, "Feign调用失败：下游服务响应超时（504）");

        STATUS_MESSAGES = Collections.unmodifiableMap(map);
    }

    /**
     * 根据状态码返回日志提示信息。
     * 未知状态码返回默认提示。
     *
     * @param statusCode HTTP 状态码
     * @return 日志提示语
     */
    public static String getMessageForStatus(final int statusCode) {
        if (STATUS_MESSAGES.containsKey(statusCode)) {
            return STATUS_MESSAGES.get(statusCode);
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        if (httpStatus.is4xxClientError()) {
            return "Feign请求被下游拒绝（4xx 客户端错误）";
        } else if (httpStatus.is5xxServerError()) {
            return "Feign调用失败：下游服务异常（5xx）";
        } else {
            return "Feign调用失败：HTTP状态码 " + statusCode;
        }
    }
}
