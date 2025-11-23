package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.request.UserCreateDTO;
import com.luoyx.hauyne.admin.sys.request.UserUpdateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileConverter {


    UserProfile toUserProfile(UserCreateDTO.UserProfileDTO userProfileDTO);

    UserProfile toUserProfile(UserUpdateDTO.UserProfileDTO userUpdateDTO);


}
