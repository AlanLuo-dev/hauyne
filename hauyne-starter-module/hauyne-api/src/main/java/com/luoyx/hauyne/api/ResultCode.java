package com.luoyx.hauyne.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 类<code>ResultCode</code>
 * 用于：TODO
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:49
 */
@Getter
@AllArgsConstructor
public class ResultCode implements Serializable {

    private String msId;

    private String code;

    private String msg;

    public String getFullCode() {
        return msId + code;
    }

    public String parseMessage(Object... args) {
        if (args != null && args.length > 0) {
            return String.format(msg, args);
        }
        return msg;
    }
}
