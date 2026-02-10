//package com.luoyx.hauyne.uaa.authentication.logout;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
//import com.luoyx.hauyne.api.APIError;
//import com.luoyx.hauyne.uaa.enums.LoginHistoryResultEnum;
//import com.luoyx.hauyne.uaa.enums.LoginHistoryTypeEnum;
//import com.luoyx.hauyne.uaa.feignclient.LoginHistoryFeignClient;
//import com.luoyx.hauyne.uaa.util.IpAddressUtil;
//import com.luoyx.hauyne.security.pojo.CurrentSysUser;
//import com.luoyx.hauyne.security.util.OAuth2CookieHelper;
//import eu.bitwalker.useragentutils.Browser;
//import eu.bitwalker.useragentutils.UserAgent;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.core.task.AsyncTaskExecutor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.concurrent.CompletableFuture;
//
///**
// * @author zlt
// * @date 2018/10/17
// */
//@Slf4j
//public class CustomLogoutHandler implements LogoutHandler {
//
//    @Autowired
//    @Qualifier("consumerTokenServices")
//    private ConsumerTokenServices consumerTokenServices;
//
//    @Autowired
//    private LoginHistoryFeignClient loginHistoryFeignClient;
//
//    @Autowired
//    private AsyncTaskExecutor taskExecutor;
//
//
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        DefaultTokenServices defaultTokenServices = (DefaultTokenServices) consumerTokenServices;
//        String accessToken;
//        Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(request);
//        if (accessTokenCookie != null && StringUtils.isNotBlank(accessToken = accessTokenCookie.getValue())) {
//            try {
//                OAuth2Authentication oAuth2Authentication = defaultTokenServices.loadAuthentication(accessToken);
//                CurrentSysUser currentSysUser = (CurrentSysUser) oAuth2Authentication.getPrincipal();
//                Long userId = currentSysUser.getId();
//                if (consumerTokenServices.revokeToken(accessToken)) {
//                    clearCookie(request, response);
//                    clearRefreshCookie(request, response);
//                    publishLoginHistoryEvent(
//                            LoginHistoryTypeEnum.LOGOUT,
//                            LoginHistoryResultEnum.LOGOUT_SUCCESS,
//                            "注销成功",
//                            userId);
//                } else {
//                    publishLoginHistoryEvent(
//                            LoginHistoryTypeEnum.LOGOUT,
//                            LoginHistoryResultEnum.LOGOUT_FAIL,
//                            "注销失败",
//                            userId);
//                }
//            } catch (Exception e) {
//                log.info("{}", e.getMessage(), e);
//                resp401(response);
//            }
//        } else {
//            resp401(response);
//        }
//    }
//
//    private void resp401(HttpServletResponse response) {
//        try {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            PrintWriter writer = response.getWriter();
//            APIError result = APIError.noPermissionToUseApi(null);
//            String jsonStr = new ObjectMapper().writeValueAsString(result);
//            writer.write(jsonStr);
//            writer.flush();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 发布 记录日志事件
//     *
//     * @param loginHistoryTypeEnum
//     * @param loginHistoryResultEnum
//     * @param failReason
//     */
//    public void publishLoginHistoryEvent(LoginHistoryTypeEnum loginHistoryTypeEnum,
//                                         LoginHistoryResultEnum loginHistoryResultEnum,
//                                         String failReason,
//                                         Long userId) {
//        saveLoginHistory(loginHistoryTypeEnum, loginHistoryResultEnum, failReason, userId);
//        try {
//            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//            response.setStatus(HttpStatus.OK.value());
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            PrintWriter writer = response.getWriter();
////            writer.write(jsonStr);
//            writer.flush();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 发布 记录日志事件
//     *
//     * @param loginHistoryTypeEnum   类型（1=登录；0=注销）
//     * @param loginHistoryResultEnum 登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）
//     * @param failReason             失败原因
//     */
//    public void saveLoginHistory(LoginHistoryTypeEnum loginHistoryTypeEnum,
//                                 LoginHistoryResultEnum loginHistoryResultEnum,
//                                 String failReason,
//                                 Long userId) {
//        CompletableFuture.runAsync(() -> {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//            SaveLoginHistoryDTO saveLoginHistoryDTO = new SaveLoginHistoryDTO();
//            saveLoginHistoryDTO.setType(loginHistoryTypeEnum.getValue());
//            saveLoginHistoryDTO.setResult(loginHistoryResultEnum.getValue());
//            saveLoginHistoryDTO.setFailReason(failReason);
//
//            //输入的用户名不存在则记为0
//            saveLoginHistoryDTO.setUserId(userId);
//
//            String ip = IpAddressUtil.getHttpServletRequestIpAddress(request);
//            saveLoginHistoryDTO.setIpAddress(ip);
//            saveLoginHistoryDTO.setLocation(IpAddressUtil.getCityInfo(ip));
//
//            String userAgentStr = request.getHeader("User-Agent");
//            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
//            Browser browser = userAgent.getBrowser();
//            saveLoginHistoryDTO.setBrowser(browser.getGroup().getName());
//            saveLoginHistoryDTO.setBrowserVersion(browser.getVersion(userAgentStr).getVersion());
//            saveLoginHistoryDTO.setOsName(userAgent.getOperatingSystem().getName());
//
//            loginHistoryFeignClient.save(saveLoginHistoryDTO);
//        }, taskExecutor);
//    }
//
//    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//        try {
//            for (int i = 0; i < cookies.length; i++) {
//                Cookie cookie = new Cookie(cookies[i].getName(), null);
//                cookie.setMaxAge(0);
//                cookie.setPath("/");//根据你创建cookie的路径进行填写
//                response.addCookie(cookie);
//            }
//        } catch (Exception ex) {
//            log.error("清空Cookies发生异常！", ex.getMessage());
//        }
//
//    }
//
//    public static void clearRefreshCookie(HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//        try {
//            Cookie cookie = new Cookie(OAuth2CookieHelper.REFRESH_TOKEN_COOKIE, null);
//            cookie.setMaxAge(0);
//            cookie.setPath("/api/uaa/oauth/token");//根据你创建cookie的路径进行填写
//            response.addCookie(cookie);
//        } catch (Exception ex) {
//            log.error("清空Cookies发生异常！", ex.getMessage());
//        }
//
//    }
//}
