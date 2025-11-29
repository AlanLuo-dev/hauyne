package com.luoyx.hauyne.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.luoyx.hauyne.api.enums.ErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * 错误信息封装类
 *
 * @param <T> 返回数据的类型
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class APIError<T> {

    /**
     * 错误码
     */
    @Schema(description = "错误码")
    private String code;

    /**
     * 错误描述
     */
    @Schema(description = "错误描述")
    private String msg;

    /**
     * 错误提示
     */
    @Schema(description = "错误提示")
    private String errorTips;

    /**
     * 返回数据（为null则不返回该字段）
     */
    @Schema(description = "返回数据（为null则不返回该字段）")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 微服务名
     */
    private String targetService;

    /**
     * 微服务调用栈
     */
    private List<FeignCallRecord> feignCallRecords;

    /**
     * 构造函数
     *
     * @param errorCodeEnum 错误代码枚举对象
     */
    public APIError(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMsg());
    }

    /**
     * 构造函数
     *
     * @param errorCodeEnum 错误代码枚举对象
     * @param errorTips     错误提示
     */
    public APIError(ErrorCodeEnum errorCodeEnum, String errorTips) {
        this(errorCodeEnum, errorTips, null);
    }

    /**
     * 构造函数
     *
     * @param errorCodeEnum 错误代码枚举对象
     * @param data          数据
     */
    public APIError(ErrorCodeEnum errorCodeEnum, T data) {
        this(errorCodeEnum, errorCodeEnum.getMsg(), data);
    }

    /**
     * 构造函数
     *
     * @param errorCodeEnum 错误码枚举对象
     * @param errorTips     错误提示
     * @param data          数据
     */
    public APIError(ErrorCodeEnum errorCodeEnum, String errorTips, T data) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
        this.errorTips = errorTips;
        this.data = data;
    }

    /**
     * 请求成功，不返回数据
     *
     * @param <U> 返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> success() {
        return success(null);
    }

    /**
     * 请求成功
     *
     * @param data 返回的数据
     * @param <U>  返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> success(U data) {
        String errorTips = ErrorCodeEnum.SUCCESS.getMsg();
        return new APIError<>(ErrorCodeEnum.SUCCESS, errorTips, data);
    }

    /**
     * 用户请求参数错误
     *
     * @param errorTips 错误提示（具体的错误细节）
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> invalidParam(String errorTips) {
        return new APIError<>(ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR, errorTips);
    }

    /**
     * 请求的资源不存在
     *
     * @param errorTips 错误提示
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> resourceNotFound(String errorTips) {
        return new APIError<>(ErrorCodeEnum.RESOURCE_NOT_FOUND, errorTips);
    }

    /**
     * 请求的URL不存在
     *
     * @param errorTips 错误提示
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> urlNotFound(String errorTips) {
        return new APIError<>(ErrorCodeEnum.URL_NOT_FOUND, errorTips);
    }

    /**
     * 无权限
     *
     * @param <U> 返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> noPermissionToUseApi(String errorTips) {
        return new APIError<>(ErrorCodeEnum.NO_PERMISSION_TO_USE_API, errorTips);
    }

    /**
     * 缺失必填参数
     *
     * @param errorTips 错误提示
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> missRequiredParam(String errorTips) {
        return new APIError<>(ErrorCodeEnum.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, errorTips);
    }

    /**
     * 服务器错误
     *
     * @param errorTips 提示信息
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> internalServerError(String errorTips) {
        return new APIError<>(ErrorCodeEnum.USER_REQUEST_SERVICE_EXCEPTION, errorTips);
    }

    /**
     * 请求失败 (不返回数据)
     *
     * @param <U> 返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> fail() {
        return fail(null);
    }

    /**
     * 请求失败 (返回数据)
     *
     * @param data 要返回的数据
     * @param <U>  返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> fail(U data) {
        return new APIError<>(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, data);
    }

    /**
     * 请求失败（自定义错误提示）
     *
     * @param errorTips 错误提示
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> fail(String errorTips) {
        return new APIError<>(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, errorTips);
    }

    /**
     * 不支持的Http请求方法
     *
     * @param <U> 返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> requestMethodNotSupported() {
        return new APIError<>(ErrorCodeEnum.REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * 未认证（未登录）
     *
     * @param errorTips 错误提示
     * @param <U>       返回数据的类型
     * @return APIError<U>
     */
    public static <U> APIError<U> unAuth(String errorTips) {
        return new APIError<>(ErrorCodeEnum.USER_IDENTITY_VERIFICATION_FAILED, errorTips);
    }
}