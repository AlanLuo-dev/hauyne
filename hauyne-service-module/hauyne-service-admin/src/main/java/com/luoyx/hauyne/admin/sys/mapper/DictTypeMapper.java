package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.query.DictTypeQuery;
import com.luoyx.hauyne.admin.sys.response.DeletedDictTypePageResultVO;
import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;

import java.util.List;


/**
 * <p>
 * 数据字典类型表 Mapper 接口
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
public interface DictTypeMapper extends GenericMapper<DictType> {

    /**
     * 查询字典类型编码是否已存在
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeCode      字典类型编码
     * @return 返回大于0 则表示字典类型编码已存在，返回0 则表示字典类型编码可用
     */
    default Long countDictTypeCode(Long excludeDictTypeId, String dictTypeCode) {
        return selectCount(
                Wrappers.<DictType>lambdaQuery()
                        .eq(DictType::getDictTypeCode, dictTypeCode)
                        .ne(excludeDictTypeId != null, DictType::getId, excludeDictTypeId)
        );
    }

    /**
     * 查询字典类型名称是否已存在
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeName      字典类型名称
     * @return 返回大于0 则表示字典类型名称已存在，返回0 则表示字典类型名称可用
     */
    default Long countDictTypeName(Long excludeDictTypeId, String dictTypeName) {
        return selectCount(
                Wrappers.<DictType>lambdaQuery()
                        .eq(DictType::getDictTypeName, dictTypeName)
                        .ne(excludeDictTypeId != null, DictType::getId, excludeDictTypeId)
        );
    }

    void updateDescription(DictType dictType);

    /**
     * 修改状态
     *
     * @param id               主键id
     * @param enableStatusEnum 状态
     */
    default void updateStatus(Long id, EnableStatusEnum enableStatusEnum) {
        DictType dictType = new DictType();
        dictType.setId(id);
        dictType.setEnabled(enableStatusEnum.getBoolValue());
        updateById(dictType);
    }

    /**
     * 查询已删除的字典类型列表
     *
     * @param query 查询条件
     * @return 已删除的字典类型列表
     */
    List<DeletedDictTypePageResultVO> findDeletedPage(DictTypeQuery query);
}
