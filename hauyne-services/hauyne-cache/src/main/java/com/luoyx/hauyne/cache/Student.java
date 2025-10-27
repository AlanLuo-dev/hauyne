package com.luoyx.hauyne.cache;

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
