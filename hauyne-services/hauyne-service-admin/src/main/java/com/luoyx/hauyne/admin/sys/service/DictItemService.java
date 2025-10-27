package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.sys.entity.DictItem;
import com.luoyx.hauyne.admin.sys.query.DictItemCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemQuery;
import com.luoyx.hauyne.admin.sys.request.DictItemCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictItemEditDTO;
import com.luoyx.hauyne.admin.sys.response.DictItemDropdownVO;
import com.luoyx.hauyne.admin.sys.response.DictItemResultVO;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;

/**
 * <p>
 * 数据字典选项表 服务类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
public interface DictItemService extends BaseService<DictItem> {

    /**
     * 新增字典值
     *
     * @param dictItemCreateDTO 新增字典值表单参数
     * @return 新增后的字典值对象
     */
    DictItem save(DictItemCreateDTO dictItemCreateDTO);

    /**
     * 按字典类型id查询字典选项
     *
     * @param query
     * @return
     */
    List<DictItemResultVO> selectDictItemByTypeId(DictItemQuery query);

    /**
     * 按字典类型编码 查询字典选项，升序排序
     *
     * @param dictTypeCode 字典类型编码
     * @return
     */
    List<DictItemDropdownVO> selectDropdownData(String dictTypeCode);

    /**
     * 检查 新增字典表单参数
     *
     * @param dictItemCreateDTO
     */
    void checkDictItemAddForm(DictItemCreateDTO dictItemCreateDTO);

    /**
     * 检查 编辑字典表单参数
     *
     * @param dictItemEditFormVO
     */
    void checkDictItemEditForm(DictItemEditDTO dictItemEditFormVO);

    /**
     * 启用某个字典
     *
     * @param id 主键id
     */
    void enable(Long id);

    /**
     * 禁用某个字典
     *
     * @param id 主键id
     */
    void disable(Long id);

    /**
     * 检查输入的【字典选项编码】是否已被占用
     *
     * @param query 条件
     * @return 布尔值
     */
    boolean checkDictItemCodeUnique(DictItemCodeUniqueCheckQuery query);

    /**
     * 检查输入的【字典选项编码】是否已被占用
     *
     * @param query 条件
     * @return true=可用，false=已被占用
     */
    boolean checkDictItemNameUnique(DictItemNameUniqueCheckQuery query);

    /**
     * 更新字典选项
     *
     * @param editFormVO
     * @return
     */
    DictItem update(DictItemEditDTO editFormVO);

    /**
     * 重新排序某个字典类型下的所有字典选项
     *
     * @param dictTypeId           字典类型id
     * @param reorderedDictItemIds 重新排序的字典选项id列表
     */
    void reorder(Long dictTypeId,
                 List<Long> reorderedDictItemIds);

    int countByDictTypeId(Long dictTypeId);

}
