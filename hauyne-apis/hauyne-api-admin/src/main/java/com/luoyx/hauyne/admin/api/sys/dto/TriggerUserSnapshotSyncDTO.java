package com.luoyx.hauyne.admin.api.sys.dto;

import lombok.Data;

@Data
public class TriggerUserSnapshotSyncDTO {

    /**
     * 触发用户快照同步的服务名
     */
    private String serviceName;
}
