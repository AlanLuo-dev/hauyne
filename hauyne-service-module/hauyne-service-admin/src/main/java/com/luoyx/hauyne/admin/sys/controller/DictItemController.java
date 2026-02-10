package com.luoyx.hauyne.admin.sys.controller;


import com.luoyx.hauyne.admin.sys.converter.DictItemConverter;
import com.luoyx.hauyne.admin.sys.entity.DictItem;
import com.luoyx.hauyne.admin.sys.query.DictItemCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.DictItemQuery;
import com.luoyx.hauyne.admin.sys.request.DictItemCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictItemEditDTO;
import com.luoyx.hauyne.admin.sys.response.DictItemCountByDictTypeVO;
import com.luoyx.hauyne.admin.sys.response.DictItemDetailVO;
import com.luoyx.hauyne.admin.sys.response.DictItemResultVO;
import com.luoyx.hauyne.admin.sys.service.DictItemService;
import com.luoyx.hauyne.api.Availability;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * <p>
 * 数据字典选项表 前端控制器
 * </p>
 *
 * @author 1079032853@qq.com
 * @since 2021-04-15
 */
@Validated
@Tag(name = "字典选项管理")
@RestController
@RequestMapping(DictItemController.DICT_ITEM_URL)
@RequiredArgsConstructor
public class DictItemController {

    public static final String DICT_ITEM_URL = "/sys/dict-items";

    private final DictItemService dictItemService;
    private final DictItemConverter dictItemConverter;

    /**
     * 查询字典选项列表
     *
     * @param query
     * @return
     */
    @PreAuthorize("hasAuthority('sys-dict-item:list')")
    @Operation(summary = "查询某个字典类型下的所有字典选项", description = "维护功能，typeId必传")
    @GetMapping
    public List<DictItemResultVO> list(@ParameterObject @Validated DictItemQuery query) {
        return dictItemService.selectDictItemByTypeId(query);
    }

    /**
     * 查询字典选项详情
     *
     * @param id 字典选项id
     * @return
     */
    //    @PreAuthorize("hasAuthority('sys-dict-item:view')")
    @Operation(summary = "查询字典选项的详情")
    @GetMapping(value = "/{id}")
    public DictItemDetailVO view(@Parameter(description = "字典选项id") @PathVariable(value = "id") Long id) {
        return dictItemConverter.toDictItemDetailVO(dictItemService.getById(id));
    }

    /**
     * 校验【字典值编码】是否已被占用（表单输入框onblur事件触发）
     *
     * @param query
     * @return
     */
    @Operation(summary = "校验【字典值编码】是否已被占用（表单输入框onblur事件触发）")
    @GetMapping(value = "/check-dict-item-code-unique")
    public Availability checkDictItemCodeUnique(@ParameterObject DictItemCodeUniqueCheckQuery query) {
        return new Availability(dictItemService.checkDictItemCodeUnique(query));
    }

    /**
     * 校验输入的【字典值名称】是否已被占用
     *
     * @param query
     * @return
     */
    @Operation(summary = "校验输入的【字典值名称】是否已被占用")
    @GetMapping(value = "/check-dict-item-name-unique")
    public Availability checkDictItemNameUnique(@ParameterObject DictItemNameUniqueCheckQuery query) {
        return new Availability(dictItemService.checkDictItemNameUnique(query));
    }

    /**
     * 新增字典
     *
     * @param dictItemCreateDTO 表单参数
     * @return 新增后的字典对象
     */
    @Operation(summary = "创建数据字典选项")
    @PreAuthorize("hasAuthority('sys-dict-item:add')")
    @PostMapping
    public ResponseEntity<DictItem> save(@Validated @RequestBody DictItemCreateDTO dictItemCreateDTO) {
        DictItem dictItem = dictItemService.save(dictItemCreateDTO);
        return ResponseEntity.created(URI.create(DICT_ITEM_URL + "/" + dictItem.getId())).body(dictItem);
    }

    /**
     * 编辑字典
     *
     * @param editFormVO 表单参数
     * @return
     */
    @PreAuthorize("hasAuthority('sys-dict-item:update')")
    @Operation(summary = "更新数据字典选项")
    @PutMapping
    public DictItem update(@Validated @RequestBody DictItemEditDTO editFormVO) {
        return dictItemService.update(editFormVO);
    }

    /**
     * 批量删除字典选项
     *
     * @param ids 字典选项id列表
     */
    @Operation(summary = "批量删除字典选项")
    @PreAuthorize("hasAuthority('sys-dict-item:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{ids}")
    public void batchDelete(@Parameter(description = "字典值id列表【多个id以逗号分隔】") @PathVariable(value = "ids") List<Long> ids) {
        dictItemService.removeByIds(ids);
    }

    /**
     * 启用某个字典
     *
     * @param id
     */
    @Operation(summary = "启用某个字典")
    @PreAuthorize("hasAuthority('sys-dict-item:toggle-status')")
    @PatchMapping(value = "/{id}/enabled")
    public void enable(@Parameter(description = "字典选项id") @NotNull @PathVariable(value = "id") Long id) {
        dictItemService.enable(id);
    }

    /**
     * 禁用某个字典
     *
     * @param id
     */
    @Operation(summary = "禁用某个字典")
    @PreAuthorize("hasAuthority('sys-dict-item:toggle-status')")
    @PatchMapping(value = "/{id}/disabled")
    public void disable(@Parameter(description = "字典选项id") @NotNull @PathVariable(value = "id") Long id) {
        dictItemService.disable(id);
    }

    /**
     * 重新设置字典值的排序
     *
     * @param dictTypeId
     * @param reorderedDictItemIds
     */
    @Operation(summary = "重新设置字典值的排序")
    @PreAuthorize("hasAuthority('sys-dict-item:reorder')")
    @PatchMapping(value = "/{dict-type-id}/reorder")
    public void reorder(@Validated @Parameter(description = "字典类型id") @PathVariable(value = "dict-type-id") Long dictTypeId,
                        @Parameter(description = "重新排序的字典选项id列表")
                        @NotNull(message = "字典值id列表不能为空")
                        @NotEmpty(message = "字典值id列表不能为空数组")
                        @RequestBody List<@NotNull(message = "字典值id列表不能包含空元素") Long> reorderedDictItemIds
    ) {
        dictItemService.reorder(dictTypeId, reorderedDictItemIds);
    }

    /**
     * 查询字典类型下的字典选项数量
     *
     * @param dictTypeId
     * @return
     */
    @Operation(summary = "查询字典类型下的字典选项数量")
    @GetMapping(value = "/{dict-type-id}/dict-item-count")
    public DictItemCountByDictTypeVO findDictItemCountByDictTypeId(@Validated @Parameter(description = "字典类型id")
                                                                   @PathVariable(value = "dict-type-id") Long dictTypeId) {
        return new DictItemCountByDictTypeVO(dictItemService.countByDictTypeId(dictTypeId));
    }
}


