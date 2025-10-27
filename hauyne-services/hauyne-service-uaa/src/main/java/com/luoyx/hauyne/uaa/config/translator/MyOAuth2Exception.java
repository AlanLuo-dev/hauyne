//package com.luoyx.hauyne.uaa.config.translator;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//
///**
// * 自定义OAuth2.0异常（使用自定义的序列化和反序列化策略）
// *
// * @author LuoTao
// */
//@JsonSerialize(using = MyOAuth2ExceptionJackson2Serializer.class)
//@JsonDeserialize(using = MyOAuth2ExceptionJackson2Deserializer.class)
//public class MyOAuth2Exception extends OAuth2Exception {
//
//    private static final long serialVersionUID = -6059182053623479091L;
//
//    /**
//     * 远程服务返回的Http状态码
//     */
//    private int remoteHttpErrorCode;
//
//    /**
//     * 构造函数
//     *
//     * @param remoteHttpErrorCode 远程服务返回的Http状态码
//     * @param msg                 错误信息
//     * @param t                   异常
//     */
//    public MyOAuth2Exception(int remoteHttpErrorCode, String msg, Throwable t) {
//        super(msg, t);
//        this.remoteHttpErrorCode = remoteHttpErrorCode;
//    }
//
//    /**
//     * 构造函数
//     *
//     * @param msg 错误信息
//     * @param t   异常
//     */
//    public MyOAuth2Exception(String msg, Throwable t) {
//        super(msg, t);
//    }
//
//    public MyOAuth2Exception(String msg) {
//        super(msg);
//    }
//
//    /**
//     * The HTTP error code associated with this error.
//     *
//     * @return The HTTP error code associated with this error.
//     */
//    @Override
//    public int getHttpErrorCode() {
//        return this.remoteHttpErrorCode;
//    }
//}
