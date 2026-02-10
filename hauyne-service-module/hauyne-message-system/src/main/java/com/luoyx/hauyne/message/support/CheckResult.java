package com.luoyx.hauyne.message.support;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:46
 */
@Getter
@Setter
@Builder
public class CheckResult {

    private boolean success;
    private String message;
    private String data;
}
