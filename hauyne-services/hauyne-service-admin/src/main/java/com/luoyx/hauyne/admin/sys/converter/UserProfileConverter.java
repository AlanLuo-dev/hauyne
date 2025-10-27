package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.request.UserCreateDTO;
import com.luoyx.hauyne.admin.sys.request.UserUpdateDTO;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileConverter {


    UserProfile toUserProfile(UserCreateDTO.UserProfileDTO userProfileDTO);

    UserProfile toUserProfile(UserUpdateDTO.UserProfileDTO userUpdateDTO);

    UserSnapshotMessage toUserSnapshotMessage(UserProfile userProfile);

    @Named("toMessage")
    @Mapping(target = "realName", expression = "java(userProfile.getRealName() + \"（已删除）\")")
    @Mapping(target = "nickname", expression = "java(userProfile.getNickname() + \"（已删除）\")")
    @Mapping(target = "lastUpdatedTime", expression = "java(now)")
    UserSnapshotMessage toMessage(UserProfile userProfile, @Context LocalDateTime now);

    @IterableMapping(qualifiedByName = "toMessage")
    List<UserSnapshotMessage> toMessageList(List<UserProfile> profiles, @Context LocalDateTime now);
}
