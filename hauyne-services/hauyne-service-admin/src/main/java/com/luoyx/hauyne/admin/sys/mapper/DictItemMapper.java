package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.sys.entity.DictItem;
import com.luoyx.hauyne.admin.sys.query.DictItemCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemQuery;
import com.luoyx.hauyne.admin.sys.response.DictItemDropdownVO;
import com.luoyx.hauyne.admin.sys.response.DictItemResultVO;
import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据字典选项表 Mapper 接口
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
public interface DictItemMapper extends GenericMapper<DictItem> {

    /**
     * 按字典类型id 查询字典选项
     *
     * @param query
     * @return
     */
    List<DictItemResultVO> selectDictItemByTypeId(DictItemQuery query);

    /**
     * 新增、编辑字典值时，执行该查询以确保同一字典类型下单字典值不重复
     * @param query 条件
     * @return
     */
    default Long countDictItemCode(DictItemCodeUniqueCheckQuery query){
        return selectCount(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictItemCode, query.getDictItemCode())
                        .eq(DictItem::getDictTypeId, query.getDictTypeId())
                        .ne(query.getExcludeDictItemId() != null, DictItem::getId, query.getExcludeDictItemId())
        );
    }

    /**
     * 新增、编辑时，校验同一类型下的字典名称是否已存在
     *
     * @param query 条件
     * @return 记录数
     */
    default Long countDictItemName(DictItemNameUniqueCheckQuery query) {
        return selectCount(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictItemName, query.getDictItemName())
                        .eq(DictItem::getDictTypeId, query.getDictTypeId())
                        .ne(query.getExcludeDictItemId() != null, DictItem::getId, query.getExcludeDictItemId())
        );
    }

    /**
     * 统计某个字典类型的字典数量
     *
     * @param dictTypeId
     * @return
     */
    int countByDictTypeId(@Param("dictTypeId") Long dictTypeId);

    /**
     * 统计一批字典类型的字典数量
     *
     * @param dictTypeIds
     * @return
     */
    List<String> countByDictTypeIds(@Param("dictTypeIds") List<Long> dictTypeIds);

    /**
     * 按字典类型编码 查询字典选项，升序排序
     *
     * @param dictTypeCode 字典类型编码
     * @return
     */
    List<DictItemDropdownVO> selectDropdownData(@Param("dictTypeCode") String dictTypeCode);

    /**
     * 启用
     *
     * @param id 主键id
     */
    default void updateStatus(Long id, EnableStatusEnum enableStatusEnum) {
        DictItem dictItem = new DictItem();
        dictItem.setId(id);
        dictItem.setEnabled(enableStatusEnum.getBoolValue());
        updateById(dictItem);
    }
}
