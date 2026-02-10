package com.luoyx.hauyne.admin.api.sys.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserProfileUpdatedMsg implements Serializable {

    private static final long serialVersionUID = -3135382679072153569L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String fullName;
}
