package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.query.DictTypeQuery;
import com.luoyx.hauyne.admin.sys.request.DictTypeCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictTypeEditDTO;
import com.luoyx.hauyne.admin.sys.response.DeletedDictTypePageResultVO;
import com.luoyx.hauyne.admin.sys.response.DictTypeDetailVO;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;

/**
 * <p>
 * 数据字典类型表 服务类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
public interface DictTypeService extends BaseService<DictType> {

    /**
     * 新增字典类型时，检查字典类型表单数据的唯一性（字典类型编码、字典类型名称）
     *
     * @param dictTypeCreateDTO
     */
    void checkUniqueDictType(DictTypeCreateDTO dictTypeCreateDTO);

    /**
     * 检查字典类型表单数据的唯一性（字典类型编码、字典类型名称）
     *
     * @param dictTypeEditFormVO
     * @return
     */
    void checkUniqueDictType(DictTypeEditDTO dictTypeEditFormVO);

    /**
     * 检查输入的【字典类型编码】是否重复
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeCode      字典类型编码
     * @return true=重复  false=可用
     */
    boolean isDictTypeCodeDuplicate(Long excludeDictTypeId, String dictTypeCode);

    /**
     * 检查输入的【字典类型名称】是否重复
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeName      字典类型名称
     * @return true=重复  false=可用
     */
    boolean isDictTypeNameDuplicate(Long excludeDictTypeId, String dictTypeName);

    /**
     * 查询单个字典类型
     *
     * @param id id
     * @return
     */
    DictTypeDetailVO view(Long id);

    /**
     * 启用某个字典类型
     *
     * @param id
     */
    void enable(Long id);

    /**
     * 禁用某个字典类型
     *
     * @param id
     */
    void disable(Long id);

    /**
     * 删除单个字典类型
     *
     * @param id
     */
    void deleteByIds(List<Long> ids);

    /**
     * 新增字典类型
     *
     * @param dictTypeCreateDTO 表单参数
     */
    DictType create(DictTypeCreateDTO dictTypeCreateDTO);

    /**
     * 修改字典类型
     *
     * @param dictTypeEditFormVO 表单参数
     */
    void update(DictTypeEditDTO dictTypeEditFormVO);

    /**
     * 分页查询已删除的字典类型列表
     *
     * @param query 查询条件
     * @return 已删除的字典类型列表
     */
    PageResult<DeletedDictTypePageResultVO> findDeletedPage(DictTypeQuery query);
}
