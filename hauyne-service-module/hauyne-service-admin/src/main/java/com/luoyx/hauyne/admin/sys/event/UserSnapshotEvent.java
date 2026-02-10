package com.luoyx.hauyne.admin.sys.event;

import com.luoyx.hauyne.admin.sys.entity.UserSnapshot;
import com.luoyx.hauyne.usersnapshot.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserSnapshotEvent {

    /**
     * 最新的用户快照
     */
    private List<UserSnapshot> userSnapshotList;

    /**
     * 事件类型（CREATE/UPDATE/DELETE）
     */
    private EventType eventType;
}
