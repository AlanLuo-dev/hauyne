package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.sys.converter.DictItemConverter;
import com.luoyx.hauyne.admin.sys.entity.DictItem;
import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.mapper.DictItemMapper;
import com.luoyx.hauyne.admin.sys.mapper.DictTypeMapper;
import com.luoyx.hauyne.admin.sys.query.DictItemCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemQuery;
import com.luoyx.hauyne.admin.sys.request.DictItemCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictItemEditDTO;
import com.luoyx.hauyne.admin.sys.response.DictItemDropdownVO;
import com.luoyx.hauyne.admin.sys.response.DictItemResultVO;
import com.luoyx.hauyne.admin.sys.service.DictItemService;
import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.web.exception.ResourceNotFoundException;
import com.luoyx.hauyne.web.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典选项表 服务实现类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    private final DictItemConverter dictItemConverter;
    private final DictTypeMapper dictTypeMapper;
    private final AsyncTaskExecutor taskExecutor;

    /**
     * 新增字典值
     *
     * @param dictItemCreateDTO 新增字典值表单参数
     * @return 新增后的字典值对象
     */
    @Override
    public DictItem save(DictItemCreateDTO dictItemCreateDTO) {
        checkDictItemAddForm(dictItemCreateDTO);

        DictItem dictItem = dictItemConverter.toEntity(dictItemCreateDTO);
        dictItem.setEnabled(EnableStatusEnum.ENABLE.getBoolValue());
        baseMapper.insert(dictItem);

        return dictItem;
    }

    /**
     * 按字典类型id查询字典选项
     *
     * @param query
     * @return
     */
    @Override
    public List<DictItemResultVO> selectDictItemByTypeId(DictItemQuery query) {
        return baseMapper.selectDictItemByTypeId(query);
    }

    /**
     * 按字典类型编码 查询字典选项，升序排序
     *
     * @param dictTypeCode 字典类型编码
     * @return
     */
    @Override
    public List<DictItemDropdownVO> selectDropdownData(String dictTypeCode) {
        return baseMapper.selectDropdownData(dictTypeCode);
    }


    /**
     * 检查 新增字典表单参数
     *
     * @param addFormVO 新增表单参数
     */
    @Override
    public void checkDictItemAddForm(DictItemCreateDTO addFormVO) {
        Long dictTypeId = addFormVO.getDictTypeId();
        String dictItemCode = addFormVO.getDictItemCode();
        DictItemCodeUniqueCheckQuery dictItemCodeUniqueCheckQuery = new DictItemCodeUniqueCheckQuery(null, dictTypeId, dictItemCode);
        if (!checkDictItemCodeUnique(dictItemCodeUniqueCheckQuery)) {
            throw new ValidateException("字典选项编码\"" + dictItemCode + "\"已被占用，请填写其他值");
        }

        String dictItemName = addFormVO.getDictItemName();
        DictItemNameUniqueCheckQuery dictItemNameUniqueCheckQuery = new DictItemNameUniqueCheckQuery(null, dictTypeId, dictItemName);
        if (!checkDictItemNameUnique(dictItemNameUniqueCheckQuery)) {
            throw new ValidateException("字典选项名称\"" + dictItemName + "\"已被占用，请填写其他值");
        }
    }

    /**
     * 检查 编辑字典表单参数
     *
     * @param editFormVO
     */
    @Override
    public void checkDictItemEditForm(DictItemEditDTO editFormVO) {
        final Long dictTypeId = editFormVO.getDictTypeId();
        if (null == dictTypeMapper.selectById(dictTypeId)) {
            throw new ValidateException("字典类型不存在");
        }

        Long id = editFormVO.getId();
        String dictItemCode = editFormVO.getDictItemCode();

        DictItemCodeUniqueCheckQuery dictItemCodeUniqueCheckQuery = new DictItemCodeUniqueCheckQuery(id, dictTypeId, dictItemCode);
        if (!checkDictItemCodeUnique(dictItemCodeUniqueCheckQuery)) {
            throw new ValidateException("字典选项编码\"" + dictItemCode + "\"已被占用，请填写其他值");
        }

        String dictItemName = editFormVO.getDictItemName();
        DictItemNameUniqueCheckQuery dictItemNameUniqueCheckQuery = new DictItemNameUniqueCheckQuery(id, dictTypeId, dictItemName);
        if (!checkDictItemNameUnique(dictItemNameUniqueCheckQuery)) {
            throw new ValidateException("字典选项名称\"" + dictItemName + "\"已被占用，请填写其他值");
        }
    }

    /**
     * 启用某个字典
     *
     * @param id 主键id
     */
    @Override
    public void enable(Long id) {
        DictItem dictItem = getById(id);
        if (null == dictItem) {
            throw new ResourceNotFoundException("该字典值不存在");
        }
        if (EnableStatusEnum.ENABLE.getBoolValue().equals(dictItem.getEnabled())) {
            throw new ValidateException("该字典值已启用，请勿重复操作");
        }
        baseMapper.updateStatus(id, EnableStatusEnum.ENABLE);
    }

    /**
     * 禁用某个字典
     *
     * @param id 主键id
     */
    @Override
    public void disable(Long id) {
        DictItem dictItem = getById(id);
        if (null == dictItem) {
            throw new ResourceNotFoundException("该字典值不存在");
        }
        if (EnableStatusEnum.DISABLE.getBoolValue().equals(dictItem.getEnabled())) {
            throw new ValidateException("该字典值已禁用，请勿重复操作");
        }
        baseMapper.updateStatus(id, EnableStatusEnum.DISABLE);
    }

    /**
     * 检查输入的【字典选项编码】是否已被占用
     *
     * @param query 条件
     * @return 布尔值
     */
    @Override
    public boolean checkDictItemCodeUnique(DictItemCodeUniqueCheckQuery query) {
        return baseMapper.countDictItemCode(query) == 0;
    }

    /**
     * 检查输入的【字典选项编码】是否已被占用
     *
     * @param query 条件
     * @return true=可用，false=已被占用
     */
    @Override
    public boolean checkDictItemNameUnique(DictItemNameUniqueCheckQuery query) {
        return baseMapper.countDictItemName(query) == 0;
    }

    /**
     * 更新字典选项
     *
     * @param editFormVO
     * @return
     */
    @Override
    public DictItem update(DictItemEditDTO editFormVO) {

        // 参数校验
        this.checkDictItemEditForm(editFormVO);

        DictItem dictItem = dictItemConverter.toEntity(editFormVO);
        this.updateById(dictItem);

        return dictItem;
    }

    /**
     * 重新排序某个字典类型下的所有字典选项
     *
     * @param dictTypeId           字典类型id
     * @param reorderedDictItemIds 重新排序的字典选项id列表
     */
    @Override
    public void reorder(Long dictTypeId,
                        List<Long> reorderedDictItemIds) {
        // 校验字典类型是否存在
        DictType dictType = dictTypeMapper.selectById(dictTypeId);
        if (null == dictType) {
            throw new ResourceNotFoundException("字典类型不存在");
        }

        // dictItemIds不能传入重复的数据
        Set<Long> distinctDictItemIds = new LinkedHashSet<>(reorderedDictItemIds);
        if (reorderedDictItemIds.size() != distinctDictItemIds.size()) {
            throw new ValidateException("字典选项Id重复");
        }

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("dict_type_id", dictTypeId);
        Map<Long, DictItem> dictItemMap = this.listByMap(columnMap)
                .stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item));

        // dictItemIds 必须是 dictTypeId中所有字典选项的id.(也就是必须全量传递， 后台执行全量重新设置排序)
        if (dictItemMap.size() != reorderedDictItemIds.size()) {
            throw new ValidateException("拖拽排序必须全量传递字典值id");
        }
        List<DictItem> dictItems = new ArrayList<>();
        for (int i = 0; i < reorderedDictItemIds.size(); i++) {
            Long tempDictItemId = reorderedDictItemIds.get(i);

            // dictItemIds 必须是存在的数据
            if (!dictItemMap.containsKey(tempDictItemId)) {
                throw new ResourceNotFoundException("字典值不存在");
            }
            DictItem dictItem = new DictItem();
            dictItem.setId(tempDictItemId);
            dictItem.setSort(i + 1);

            dictItems.add(dictItem);
        }

        // 按传入的dictItemIds顺序设置排序，入库
        this.updateBatchById(dictItems);
    }


    public int countByDictTypeId(Long dictTypeId) {
        return baseMapper.countByDictTypeId(dictTypeId);
    }

}
