package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.entity.UserSnapshot;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserSnapshotConverter {

    UserSnapshot toUserSnapshot(UserProfile userProfile);

    UserSnapshotMessage.UserSnapshot toMsgUserSnapshot(UserSnapshot userSnapshot);

    List<UserSnapshotMessage.UserSnapshot> toMsgUserSnapshotList(List<UserSnapshot> userSnapshotList);
}
