package com.luoyx.hauyne.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 罗英雄
 * @date 2021/9/9 16:04
 */
@Slf4j
@Component
@ConfigurationProperties("sms.config")
public class SmsConfig {

    private long lastUpdateTime = System.currentTimeMillis();

    private final Object lockObj = new Object();

    private int sendIndex = 0;

    private List<String> sendAddrList;

    private String sendPath;

    private String protocol;

    public String getPath() {
        return sendPath;
    }

    public void setPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<String> getSendAddrList() {
        return sendAddrList;
    }

    public void setSendAddrList(List<String> sendAddrList) {
        this.sendAddrList = sendAddrList;
    }

    public String getServerAddr() {
        synchronized (this.lockObj) {
            long curTime = System.currentTimeMillis();
            if (curTime - this.lastUpdateTime > 6000000L) {
                this.lastUpdateTime = curTime;
            }
            int index = this.sendIndex % this.sendAddrList.size();
            ++this.sendIndex;
            if (this.sendIndex == this.sendAddrList.size()) {
                this.sendIndex = 0;
            }

            return this.sendAddrList.get(index);
        }
    }

    public  int getIpCount() {
        synchronized (this.lockObj) {
            return this.sendAddrList.size();
        }
    }
}
