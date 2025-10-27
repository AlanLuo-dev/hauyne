//package com.luoyx.hauyne.exception;
//
//import com.luoyx.hauyne.openfeign.api.ResultCode;
//
///**
// * 类<code>BizException</code>
// * 用于：TODO
// *
// * @author zt19191
// * @version 1.0
// * @date 2021/6/10 14:50
// */
//public class BizException extends RuntimeException {
//
//    private static final long serialVersionUID = -2909748528316703603L;
//
//    private ResultCode resultCode;
//
//    public BizException() {
//        super();
//    }
//
//    public BizException(ResultCode resultCode, String... args) {
//        super(resultCode.parseMessage(args));
//        this.resultCode = resultCode;
//    }
//
////    private static String getFormatedMsg(ResultCode resultCode, String... args) {
////        String msg = resultCode.getMsg();
////        if (args != null && args.length > 0) {
////            msg = String.format(msg, args);
////        }
////        return msg;
////    }
//
//    public String getCode() {
//        return this.resultCode.getFullCode();
//    }
//
//    @Override
//    public String toString() {
//        return "BizException{" +
//                "code=" + this.getCode() + ", " +
//                "message=" + this.getMessage() + "}";
//    }
//
//}