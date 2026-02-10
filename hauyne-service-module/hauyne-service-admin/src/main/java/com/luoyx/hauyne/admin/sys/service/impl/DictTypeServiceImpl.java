package com.luoyx.hauyne.admin.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.base.CaseFormat;
import com.luoyx.hauyne.admin.sys.converter.DictTypeConverter;
import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.mapper.DictItemMapper;
import com.luoyx.hauyne.admin.sys.mapper.DictTypeMapper;
import com.luoyx.hauyne.admin.sys.query.DictTypeQuery;
import com.luoyx.hauyne.admin.sys.request.DictTypeCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictTypeEditDTO;
import com.luoyx.hauyne.admin.sys.response.DeletedDictTypePageResultVO;
import com.luoyx.hauyne.admin.sys.response.DictTypeDetailVO;
import com.luoyx.hauyne.admin.sys.service.DictTypeService;
import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LogicDeleteByBatchIdsCondition;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.web.exception.ResourceNotFoundException;
import com.luoyx.hauyne.web.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典类型表 服务实现类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    private final DictItemMapper dictItemMapper;
    private final DictTypeConverter dictTypeConverter;

    /**
     * 新增字典类型时，检查字典类型表单数据的唯一性（字典类型编码、字典类型名称）
     *
     * @param dictTypeCreateDTO
     */
    @Override
    public void checkUniqueDictType(DictTypeCreateDTO dictTypeCreateDTO) {

        // 检查字典类型编码是否重复
        String dictTypeCode = dictTypeCreateDTO.getDictTypeCode();
        if (isDictTypeCodeDuplicate(null, dictTypeCode)) {
            throw new ValidateException("字典类型编码【" + dictTypeCode + "】已存在，请勿重复添加 ");
        }
        String dictTypeName = dictTypeCreateDTO.getDictTypeName();
        if (isDictTypeNameDuplicate(null, dictTypeName)) {
            throw new ValidateException("字典类型名称【" + dictTypeName + "】已存在，请勿重复添加 ");
        }
    }

    /**
     * 修改字典类型时，检查字典类型表单数据的唯一性（字典类型编码、字典类型名称）
     *
     * @param dictTypeEditDTO 修改字典类型 表单参数
     */
    @Override
    public void checkUniqueDictType(DictTypeEditDTO dictTypeEditDTO) {
        Long id = dictTypeEditDTO.getId();

        // 检查字典类型编码是否重复
        String dictTypeCode = dictTypeEditDTO.getDictTypeCode();
        if (isDictTypeCodeDuplicate(id, dictTypeCode)) {
            throw new ValidateException("字典类型编码【" + dictTypeCode + "】已存在，请勿重复添加 ");
        }
        String dictTypeName = dictTypeEditDTO.getDictTypeName();
        if (isDictTypeNameDuplicate(id, dictTypeName)) {
            throw new ValidateException("字典类型名称【" + dictTypeName + "】已存在，请勿重复添加 ");
        }
    }

    /**
     * 检查输入的【字典类型编码】是否重复
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeCode      字典类型编码
     * @return true=重复  false=可用
     */
    @Override
    public boolean isDictTypeCodeDuplicate(Long excludeDictTypeId, String dictTypeCode) {
        return baseMapper.countDictTypeCode(excludeDictTypeId, dictTypeCode) > 0;
    }

    /**
     * 检查输入的【字典类型名称】是否重复
     *
     * @param excludeDictTypeId 要排除的字典类型Id（编辑场景）
     * @param dictTypeName      字典类型名称
     * @return true=重复  false=可用
     */
    @Override
    public boolean isDictTypeNameDuplicate(Long excludeDictTypeId, String dictTypeName) {
        return baseMapper.countDictTypeName(excludeDictTypeId, dictTypeName) > 0;
    }

    @Override
    public DictTypeDetailVO view(Long id) {
        return dictTypeConverter.toDictTypeDetailVO(baseMapper.selectById(id));
    }

    @Override
    public void enable(Long id) {
        DictType dictType = getById(id);
        if (null == dictType) {
            throw new ResourceNotFoundException("该字典类型不存在");
        }
        if (EnableStatusEnum.ENABLE.getBoolValue().equals(dictType.getEnabled())) {
            throw new ValidateException("该字典类型已启用，请勿重复操作");
        }
        baseMapper.updateStatus(id, EnableStatusEnum.ENABLE);
    }

    @Override
    public void disable(Long id) {
        DictType dictType = getById(id);
        if (null == dictType) {
            throw new ResourceNotFoundException("该字典类型不存在");
        }
        if (EnableStatusEnum.DISABLE.getBoolValue().equals(dictType.getEnabled())) {
            throw new ValidateException("该字典类型已禁用，请勿重复操作");
        }
        baseMapper.updateStatus(id, EnableStatusEnum.DISABLE);
    }

    /**
     * 删除单个字典类型
     *
     * @param ids 字典类型id列表
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        List<DictType> dictTypeList = baseMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(dictTypeList)) {
            throw new ResourceNotFoundException("字典类型不存在");
        }
        Map<Long, DictType> dictTypeMap = dictTypeList.stream().collect(Collectors.toMap(DictType::getId, item -> item));
        for (Long id : ids) {
            if (!dictTypeMap.containsKey(id)) {
                log.info("字典类型Id {} 不存在", id);
                throw new ResourceNotFoundException("字典类型不存在");
            }
        }
        List<String> dictTypeNames = dictItemMapper.countByDictTypeIds(ids);
        if (CollectionUtils.isNotEmpty(dictTypeNames)) {
            throw new ValidateException("字典类型“" + dictTypeNames.get(0) + "”有字典值，需要删除字典值才能删除字典类型");
        }
        baseMapper.logicDeleteByBatchIds(new LogicDeleteByBatchIdsCondition(ids));
    }

    /**
     * 新增字典类型
     *
     * @param dictTypeCreateDTO 表单参数
     */
    @Override
    public DictType create(DictTypeCreateDTO dictTypeCreateDTO) {
        checkUniqueDictType(dictTypeCreateDTO);

        DictType dictType = dictTypeConverter.toEntity(dictTypeCreateDTO);

        // 默认启用
        dictType.setEnabled(true);
        baseMapper.insert(dictType);

        return dictType;
    }

    /**
     * 修改字典类型
     *
     * @param dictTypeEditDTO 表单参数
     */
    @Override
    public void update(DictTypeEditDTO dictTypeEditDTO) {
        checkUniqueDictType(dictTypeEditDTO);
        DictType dictType = dictTypeConverter.toEntity(dictTypeEditDTO);
        baseMapper.updateById(dictType);
    }

    @Override
    public PageResult<DeletedDictTypePageResultVO> findDeletedPage(DictTypeQuery query) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        final String sortField = query.getSortField();
        final String sortOrder = query.getSortOrder();

        // 排序字段、排序方式都不为空时才能排序
        if (StringUtils.hasText(sortField) && StringUtils.hasText(sortOrder)) {

            // 驼峰命名 转为 蛇形命名
            String lowerUnderscoreSortField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortField);
            String sortOrderSqlKeyword = PageQuery.SortOrderEnum.SORT_ORDER_MAP.get(sortOrder);
            PageHelper.orderBy(lowerUnderscoreSortField + " " + sortOrderSqlKeyword);
        }

        return setPageResult(baseMapper.findDeletedPage(query));
    }
}
