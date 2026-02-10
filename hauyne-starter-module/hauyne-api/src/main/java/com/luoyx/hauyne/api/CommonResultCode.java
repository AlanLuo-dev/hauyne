package com.luoyx.hauyne.api;

/**
 * 类<code>CommonResultCode</code>
 * 用于：TODO
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:49
 */
public class CommonResultCode {

    private static final String MSID = "000";

    public static final ResultCode LOCK_FAILED = new ResultCode(MSID, "001001", "锁获取失败");

    public static final ResultCode UNKNOW_ERROR = new ResultCode(MSID, "001002", "未知错误[%s]");

    public static final ResultCode UNLOGIN = new ResultCode(MSID, "001003", "未登录");

    public static final ResultCode BAD_HTTP_REQUEST = new ResultCode(MSID, "001005", "请求错误 %s");

    public static final ResultCode SERVER_ERROR = new ResultCode(MSID, "001006", "服务器错误 %s");

    public static final ResultCode MISS_PARAM = new ResultCode(MSID, "001010", "缺少参数 %s");

    public static final ResultCode AUTHORIZATION = new ResultCode(MSID, "001014", "未经授权的访问");

    public static final ResultCode ILLEGAL_OPERATION = new ResultCode(MSID, "001015", "非法的操作请求");

    public static final ResultCode FORBIDDEN = new ResultCode(MSID, "001016", "没有权限, 禁止访问");

    public static final ResultCode NOT_FOUND = new ResultCode(MSID, "001017", "您访问的资源不存在");

    public static final ResultCode ILLEGAL_ACCESS = new ResultCode(MSID, "001018", "非法访问 %s");

    public static final ResultCode PROHIBIT_DUPLICATE = new ResultCode(MSID, "001019", "请勿重复提交");


}