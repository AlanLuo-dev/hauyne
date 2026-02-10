package com.luoyx.hauyne.message.service;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:41
 */
public interface SmsService {

    /**
     *
     * @param userId 短信接收人手机号码，多个号码之间用逗号分隔，一次提交不要超过100个手机号码
     * @param mobile 短信接收人手机号码，多个号码之间用逗号分隔，一次提交不要超过100个手机号码
     * @param message 短信内容
     * @param messageType 短信类型，取值包括： 1=营销类短信；2=通知类短信； 3=验证码短信
     * @return 结果
     */
    Boolean sendMessage(String userId, String mobile, String message, Integer messageType);

    Boolean needCodeForSend(String mobile);

    String send(String mobile);

    boolean check(String mobile, String code);
}
