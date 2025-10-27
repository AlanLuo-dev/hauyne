package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.admin.sys.entity.LoginHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginHistoryConverter {

    LoginHistory toEntity(SaveLoginHistoryDTO saveLoginHistoryDTO);
}
