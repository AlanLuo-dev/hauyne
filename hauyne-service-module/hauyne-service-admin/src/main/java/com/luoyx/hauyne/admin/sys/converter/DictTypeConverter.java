package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.request.DictTypeCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictTypeEditDTO;
import com.luoyx.hauyne.admin.sys.response.DictTypeDetailVO;
import org.mapstruct.Mapper;

/**
 * 数据字典类型 领域模型对象 mapstruct 转换接口
 *
 * @author 罗英雄
 */
@Mapper(componentModel = "spring")
public interface DictTypeConverter {

    DictType toEntity(DictTypeCreateDTO dictTypeCreateDTO);

    DictType toEntity(DictTypeEditDTO dictTypeEditFormVO);

    DictTypeDetailVO toDictTypeDetailVO(DictType dictType);


    //    @InheritInverseConfiguration()
    DictTypeEditDTO toDictTypeEditDTO(DictType dictType);
}
