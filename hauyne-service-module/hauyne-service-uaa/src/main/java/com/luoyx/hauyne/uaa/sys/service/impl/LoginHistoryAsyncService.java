package com.luoyx.hauyne.uaa.sys.service.impl;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.security.pojo.CurrentSysUser;
import com.luoyx.hauyne.uaa.amqp.LoginHistoryProducer;
import com.luoyx.hauyne.uaa.enums.LoginHistoryResultEnum;
import com.luoyx.hauyne.uaa.enums.LoginHistoryTypeEnum;
import com.luoyx.hauyne.uaa.util.IpAddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.luoyx.hauyne.uaa.config.AsyncPoolConfig.TASK_EXECUTOR_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginHistoryAsyncService {

    private final LoginHistoryProducer loginHistoryProducer;
    private final UserAgentAnalyzer userAgentAnalyzer;

    @Async(TASK_EXECUTOR_NAME)
    public void saveLoginHistory(LoginHistoryTypeEnum type,
                                 LoginHistoryResultEnum result,
                                 String failReason,
                                 CurrentSysUser user,
                                 String ipAddress,
                                 String rawUserAgent) {
        try {
            SaveLoginHistoryDTO dto = new SaveLoginHistoryDTO();

            dto.setType(type.getValue());
            dto.setResult(result.getValue());
            dto.setFailReason(failReason);

            dto.setUserId(user.getId());
            dto.setIpAddress(ipAddress);
            dto.setLocation(IpAddressUtil.getCityInfo(ipAddress));

            dto.setLoginTime(LocalDateTime.now());
            dto.setUserAgent(rawUserAgent);

            if (StringUtils.isBlank(rawUserAgent)) {
                dto.setBrowser("Unknown");
                dto.setOsName("Unknown");
            } else {
                UserAgent agent = userAgentAnalyzer.parse(rawUserAgent);

                dto.setBrowser(agent.getValue(UserAgent.AGENT_NAME));
                dto.setBrowserVersion(agent.getValue(UserAgent.AGENT_VERSION));

                String osName = agent.getValue(UserAgent.OPERATING_SYSTEM_NAME);
                if (osName.startsWith("Windows")) {
                    osName = "Windows";
                }

                dto.setOsName(osName);
            }

            loginHistoryProducer.send(dto);

        } catch (Exception e) {
            // 登录历史记录失败，不能影响登录
            log.error("异步记录登录历史失败, userId={}",
                    user.getId(), e);
        }
    }
}
