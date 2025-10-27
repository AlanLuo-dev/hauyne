package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.sys.entity.DictType;
import com.luoyx.hauyne.admin.sys.query.DictTypeQuery;
import com.luoyx.hauyne.admin.sys.request.DictTypeCreateDTO;
import com.luoyx.hauyne.admin.sys.request.DictTypeEditDTO;
import com.luoyx.hauyne.admin.sys.response.DeletedDictTypePageResultVO;
import com.luoyx.hauyne.admin.sys.response.DictItemDropdownVO;
import com.luoyx.hauyne.admin.sys.response.DictTypeDetailVO;
import com.luoyx.hauyne.admin.sys.response.DictTypePageResultVO;
import com.luoyx.hauyne.admin.sys.service.DictItemService;
import com.luoyx.hauyne.admin.sys.service.DictTypeService;
import com.luoyx.hauyne.api.Availability;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * <p>
 * 数据字典类型表 前端控制器
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Tag(name = "字典类型管理")
@Slf4j
@RestController
@RequestMapping(DictTypeController.DICT_TYPE_URI)
@RequiredArgsConstructor
public class DictTypeController {

    public static final String DICT_TYPE_URI = "/sys/dict-types";

    private final DictTypeService dictTypeService;
    private final DictItemService dictItemService;

    @Operation(summary = "分页查询字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:find-page')")
    @GetMapping
    public PageResult<DictTypePageResultVO> findPage(@Validated DictTypeQuery query) {
        return dictTypeService.findPage(query);
    }

    /**
     * 查询单个字典类型
     *
     * @param id 主键id
     * @return
     */
    @Operation(summary = "查询单个字典类型")
    @GetMapping(value = "/{id}")
    public DictTypeDetailVO view(@PathVariable(value = "id") Long id) {
        return dictTypeService.view(id);
    }

    /**
     * 检查【字典类型编码】是否重复
     *
     * @param excludeDictTypeId 要排除的字典类型id
     * @param dictTypeCode      字典类型编码
     * @return 字典类型编码是否重复
     */
    @Operation(summary = "检查输入的【字典类型编码】是否重复")
    @GetMapping(value = "/check-dict-type-code-unique")
    public Availability checkDictTypeCodeUnique(@Schema(description = "要排除的字典类型id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
                                                @RequestParam(value = "excludeDictTypeId", required = false)
                                                Long excludeDictTypeId,
                                                @Schema(description = "字典类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
                                                @NotBlank(message = "字典类型编码不能为空")
                                                @RequestParam(value = "dictTypeCode")
                                                String dictTypeCode) {
        return new Availability(!dictTypeService.isDictTypeCodeDuplicate(excludeDictTypeId, dictTypeCode));
    }

    /**
     * 检查输入的【字典类型名称】是否已被占用
     *
     * @param excludeDictTypeId 要排除的字典类型id
     * @param dictTypeName      字典类型名称
     * @return 字典类型名称是否重复
     */
    @Operation(summary = "检查输入的【字典类型名称】是否已被占用")
    @GetMapping(value = "/check-dict-type-name-unique")
    public Availability checkDictTypeNameUnique(@Schema(description = "要排除的字典类型id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
                                                @RequestParam(value = "excludeDictTypeId", required = false)
                                                Long excludeDictTypeId,
                                                @Schema(description = "字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
                                                @NotBlank(message = "字典类型名称不能为空")
                                                @RequestParam(value = "dictTypeName")
                                                String dictTypeName) {
        return new Availability(!dictTypeService.isDictTypeNameDuplicate(excludeDictTypeId, dictTypeName));
    }

    /**
     * 创建字典类型
     *
     * @param dictTypeCreateDTO 表单参数
     * @return 字典类型
     */
    @Operation(summary = "创建字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:add')")
    @PostMapping
    public ResponseEntity<DictType> create(@Validated @RequestBody DictTypeCreateDTO dictTypeCreateDTO) {
        DictType dictType = dictTypeService.create(dictTypeCreateDTO);

        return ResponseEntity.created(URI.create(DICT_TYPE_URI + "/" + dictType.getId())).body(dictType);
    }

    @Operation(summary = "修改字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:update')")
    @PutMapping
    public void update(@Validated @RequestBody DictTypeEditDTO dictTypeEditFormVO) {
        dictTypeService.update(dictTypeEditFormVO);
    }

    @Operation(summary = "批量删除字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{ids}")
    public void delete(@Parameter(name = "字典类型id列表【多个字典类型id以逗号分隔】") @PathVariable(value = "ids") List<Long> ids) {
        dictTypeService.deleteByIds(ids);
    }

    /**
     * 启用字典类型
     *
     * @param id
     */
    @Operation(summary = "启用字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:toggle-status')")
    @PatchMapping(value = "/{id}/enabled")
    public void enable(@PathVariable(value = "id") Long id) {
        dictTypeService.enable(id);
    }

    /**
     * 禁用字典类型
     *
     * @param id
     */
    @Operation(summary = "禁用字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:toggle-status')")
    @PatchMapping(value = "/{id}/disabled")
    public void disable(@PathVariable(value = "id") Long id) {
        dictTypeService.disable(id);
    }

    /**
     * 按字典类型编码查询字典选项，用作下拉框数据
     *
     * @param dictTypeCode 字典类型编码
     * @return
     */
    @Operation(summary = "按字典类型编码查询字典选项，用作下拉框数据", description = "只查询已启用的字典选项")
    @GetMapping(value = "/{dictTypeCode}/dropdown")
    public List<DictItemDropdownVO> loadDropdownData(@PathVariable(value = "dictTypeCode") String dictTypeCode) {
        return dictItemService.selectDropdownData(dictTypeCode);
    }

    /**
     * 查询已删除的字典类型
     *
     * @param query 查询条件
     * @return 已删除的字典类型
     */
    @Operation(summary = "查询已删除的字典类型")
    @PreAuthorize("hasAuthority('sys-dict-type:recycle-bin')")
    @GetMapping(value = "/deleted")
    public PageResult<DeletedDictTypePageResultVO> findDeletedPage(DictTypeQuery query) {
        return dictTypeService.findDeletedPage(query);
    }
}

