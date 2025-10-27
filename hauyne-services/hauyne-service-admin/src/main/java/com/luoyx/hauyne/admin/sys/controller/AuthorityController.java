package com.luoyx.hauyne.admin.sys.controller;


import com.luoyx.hauyne.admin.sys.converter.AuthorityConverter;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.query.AuthorityCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityQuery;
import com.luoyx.hauyne.admin.sys.request.AuthorityCreateDTO;
import com.luoyx.hauyne.admin.sys.request.AuthorityUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.AuthorityCheckBoxTreeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityDetailVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeNodeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeSelectVO;
import com.luoyx.hauyne.admin.sys.service.AuthorityService;
import com.luoyx.hauyne.api.Availability;
import com.luoyx.hauyne.web.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
 * 权限表 前端控制器
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-18
 */
@Tag(name = "权限管理")
@RestController
@RequestMapping(AuthorityController.AUTHORITY_URI)
@RequiredArgsConstructor
public class AuthorityController {

    public static final String AUTHORITY_URI = "/sys/authorities";

    private final AuthorityService authorityService;
    private final AuthorityConverter authorityConverter;

    @Operation(summary = "权限树形数据列表")
    @PreAuthorize("hasAuthority('sys-authority:tree-list')")
    @GetMapping
    public List<AuthorityTreeNodeVO> list(@Validated AuthorityQuery query) {
        return authorityService.findAuthorities(query);
    }

    /**
     * 父级菜单树【新增、编辑权限表单页用】
     *
     * @return
     */
    @Operation(summary = "父级菜单树【新增、编辑权限表单页用】")
    @GetMapping(value = "/tree-select")
    public List<AuthorityTreeSelectVO> loadParentAuthorityTreeData() {
        return authorityService.selectParentAuthorityFormData();
    }

    /**
     * 加载权限资源, 以复选框形式的树结构展示
     *
     * @return 权限资源树
     */
    @Operation(summary = "加载权限资源, 以复选框形式的树结构展示")
    @GetMapping(value = "/checkbox-tree")
    public List<AuthorityCheckBoxTreeVO> loadAuthorityCheckBoxTree() {
        return authorityService.selectCheckBoxTree();
    }

    /**
     * 创建权限
     *
     * @param authorityCreateDTO 权限表单参数
     * @return 权限数据
     */
    @Operation(summary = "创建权限")
    @PreAuthorize("hasAuthority('sys-authority:add')")
    @PostMapping
    public ResponseEntity<Authority> add(@Validated @RequestBody AuthorityCreateDTO authorityCreateDTO) {
        Authority authority = authorityService.create(authorityCreateDTO);
        return ResponseEntity.created(URI.create(AUTHORITY_URI + "/" + authority.getId())).body(authority);
    }

    /**
     * 校验权限编码是否唯一
     *
     * @param query 权限编码唯一性校验 查询条件
     * @return
     */
    @Operation(summary = "校验权限编码是否唯一")
    @GetMapping(value = "/check-authority-code-unique")
    public Availability checkAuthorityCodeUnique(@Validated AuthorityCodeUniqueCheckQuery query) {
        return new Availability(authorityService.checkAuthorityCodeUnique(query));
    }

    /**
     * 校验权限名称是否唯一
     *
     * @param query 权限名称唯一性校验 查询条件
     * @return
     */
    @Operation(summary = "校验权限名称是否唯一")
    @GetMapping(value = "/check-authority-name-unique")
    public Availability checkAuthorityNameUnique(@Validated AuthorityNameUniqueCheckQuery query) {
        return new Availability(authorityService.checkAuthorityNameAvailability(query));
    }

    //    @PreAuthorize("hasAuthority('sys-permission:view')")
    @Operation(summary = "获取单行权限纪录")
    @GetMapping(value = "/{id}")
    public AuthorityDetailVO view(@NotNull @PathVariable(value = "id") Long id) {
        AuthorityDetailVO authorityDetail = authorityService.view(id);
        if (null == authorityDetail) {
            throw new ResourceNotFoundException("权限数据不存在");
        }

        return authorityDetail;
    }

    /**
     * 修改权限
     *
     * @param authorityUpdateDTO 权限表单参数
     */
    @Operation(summary = "修改权限")
    @PreAuthorize("hasAuthority('sys-authority:update')")
    @PutMapping
    public void update(@Validated @RequestBody AuthorityUpdateDTO authorityUpdateDTO) {
        authorityService.update(authorityUpdateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除权限资源数据")
    @PreAuthorize("hasAuthority('sys-authority:delete')")
    @DeleteMapping(value = "/{id}")
    public void deleteById(@NotNull @PathVariable(value = "id") Long id) {
        authorityService.deleteById(id);
    }

}

