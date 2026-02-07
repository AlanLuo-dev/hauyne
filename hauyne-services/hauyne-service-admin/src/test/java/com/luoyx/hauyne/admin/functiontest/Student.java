package com.luoyx.hauyne.admin.functiontest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Student {

    private long createdBy;
    private String createdByFullName;
    private long lastUpdatedBy;
    private String lastModifiedByFullName;

}
