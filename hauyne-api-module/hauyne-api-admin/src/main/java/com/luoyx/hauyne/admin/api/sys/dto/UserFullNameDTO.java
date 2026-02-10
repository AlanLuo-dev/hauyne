package com.luoyx.hauyne.admin.api.sys.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户真实姓名 缓存数据DTO类
 *
 * @author 1564469545@qq.com
 */
@Getter
@Setter
public class UserFullNameDTO implements Serializable {

    private static final long serialVersionUID = 715881901530536935L;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String fullName;
}
