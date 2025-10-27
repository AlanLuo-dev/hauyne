package com.luoyx.hauyne.uaa.listener;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.security.pojo.CurrentSysUser;
import com.luoyx.hauyne.uaa.amqp.LoginHistoryProducer;
import com.luoyx.hauyne.uaa.authentication.captcha.CaptchaGrantAuthenticationToken;
import com.luoyx.hauyne.uaa.enums.LoginHistoryResultEnum;
import com.luoyx.hauyne.uaa.enums.LoginHistoryTypeEnum;
import com.luoyx.hauyne.uaa.util.IpAddressUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * 认证事件监听器
 *
 * @author 罗英雄 luo_yingxiong@163.com
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationEventListener {

    private final AsyncTaskExecutor taskExecutor;
    private final LoginHistoryProducer loginHistoryProducer;

    /**
     * 监听认证抽象事件【这里的事件监听不能加@Async异步注解】
     * 否则报错：java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
     *
     * @param event
     */
    @EventListener
    public void authenticationEventHandler(AbstractAuthenticationEvent event) {

        /**
         * 以下三个事件源都被框架视为AuthenticationSuccessEvent
         * 1、获取token成功
         * 2、token验证成功【即token可用】
         * 3、OAuth2.0的客户端认证成功【即clientid secret的验证】
         *
         * 这里是记录登录成功的日志，所以我们针对【获取token成功】事件源记录登录历史【CaptchaAuthenticationToken是自定义的token类，增加了验证码】
         */
        if (event instanceof AuthenticationSuccessEvent) {
            if (event.getSource() instanceof CaptchaGrantAuthenticationToken) {

                CaptchaGrantAuthenticationToken token = (CaptchaGrantAuthenticationToken) event.getSource();
                CurrentSysUser user = (CurrentSysUser) token.getPrincipal();
                log.info("登录成功");
                saveLoginHistory(
                        LoginHistoryTypeEnum.LOGIN,
                        LoginHistoryResultEnum.LOGIN_SUCCESS, "登录成功",
                        user
                );
            } else {
                log.warn("其他类型的事件源 -> {}", event.getSource().getClass().getName());
            }
        } else if (event instanceof AbstractAuthenticationFailureEvent) {
            AbstractAuthenticationFailureEvent failEvent = (AbstractAuthenticationFailureEvent) event;
            log.warn("登录失败 -> {}", failEvent.getException());
            LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) ((CaptchaGrantAuthenticationToken) failEvent.getSource()).getDetails();
//            saveLoginHistory(LoginHistoryTypeEnum.LOGIN, LoginHistoryResultEnum.LOGIN_FAIL, failEvent.getException().getMessage(), linkedHashMap.get("username"));
        } else {
            log.warn("其他事件 -> {}", event.getClass().getName());
        }
    }


    /**
     * 发布 记录日志事件
     *
     * @param loginHistoryTypeEnum   类型（1=登录；0=注销）
     * @param loginHistoryResultEnum 登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）
     * @param failReason             失败原因
     */
    public void saveLoginHistory(LoginHistoryTypeEnum loginHistoryTypeEnum,
                                 LoginHistoryResultEnum loginHistoryResultEnum,
                                 String failReason,
                                 CurrentSysUser user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SaveLoginHistoryDTO saveLoginHistoryDTO = new SaveLoginHistoryDTO();
        saveLoginHistoryDTO.setType(loginHistoryTypeEnum.getValue());
        saveLoginHistoryDTO.setResult(loginHistoryResultEnum.getValue());
        saveLoginHistoryDTO.setFailReason(failReason);

        //输入的用户名不存在则记为0
        saveLoginHistoryDTO.setUserId(user.getId());

        String ip = IpAddressUtil.getHttpServletRequestIpAddress(request);
        saveLoginHistoryDTO.setIpAddress(ip);
        saveLoginHistoryDTO.setLocation(IpAddressUtil.getCityInfo(ip));

        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        Browser browser = userAgent.getBrowser();
        saveLoginHistoryDTO.setBrowser(browser.getGroup().getName());
        saveLoginHistoryDTO.setBrowserVersion(browser.getVersion(userAgentStr).getVersion());
        saveLoginHistoryDTO.setOsName(userAgent.getOperatingSystem().getName());

        loginHistoryProducer.send(saveLoginHistoryDTO);
    }
}
