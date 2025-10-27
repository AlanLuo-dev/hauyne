package com.luoyx.hauyne.admin.api.sys.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AuthorityDTO implements Serializable {

    /**
     * 权限编码
     */
    private String authorityCode;
}
