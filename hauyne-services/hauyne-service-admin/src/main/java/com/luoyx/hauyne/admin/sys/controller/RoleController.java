package com.luoyx.hauyne.admin.sys.controller;


import com.luoyx.hauyne.admin.sys.converter.RoleConverter;
import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.enums.TestCodeEnum;
import com.luoyx.hauyne.admin.sys.feignclient.AuditFeignClient;
import com.luoyx.hauyne.admin.sys.feignclient.UAAFeignClient;
import com.luoyx.hauyne.admin.sys.feignclient.UserFeignClient;
import com.luoyx.hauyne.admin.sys.query.RoleCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.RoleNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.RoleQuery;
import com.luoyx.hauyne.admin.sys.request.RoleCreateDTO;
import com.luoyx.hauyne.admin.sys.request.RoleUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.RoleDropdownVO;
import com.luoyx.hauyne.admin.sys.response.RolePageResultVO;
import com.luoyx.hauyne.admin.sys.response.RoleVO;
import com.luoyx.hauyne.admin.sys.service.RoleService;
import com.luoyx.hauyne.api.Availability;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.validation.group.GroupOrdered;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Tag(name = "角色管理")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(RoleController.ROLE_URI)
public class RoleController {

    public static final String ROLE_URI = "/sys/roles";

    private final RoleService roleService;
    private final RoleConverter roleConverter;
    private final UserFeignClient userFeignClient;
    private final UAAFeignClient uaaFeignClient;
    private final AuditFeignClient traceLogFeignClient;

    @Operation(summary = "测试调用Audit服务")
    @GetMapping(value = "/testCallAudit")
    public Map<String, String> testCallAudit(){
        return traceLogFeignClient.testCallAudit();
    }

    @Operation(summary = "测试枚举接收参数")
    @GetMapping(value = "/testEnum")
    public TestCodeEnum test(@RequestParam TestCodeEnum testCodeEnum){
        return testCodeEnum;
    }

    /**
     * 分页查询角色
     *
     * @param query 过滤条件 & 分页条件
     * @return 分页结果集
     */
    @Operation(summary = "分页查询角色")
    @PreAuthorize("hasAuthority('sys-role:find-page')")
    @GetMapping
    public PageResult<RolePageResultVO> findPage(@Validated RoleQuery query) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

//        System.out.println("auth.name = " + auth.getName());
//        Map<String, String> stringStringMap = uaaFeignClient.testCallUAA();
//        System.out.println("UAA 返回的数据：" + JsonUtil.toString(stringStringMap));
        return roleService.findPage(query);
    }

//    @HystrixCommand(fallbackMethod = "testFallback")
    @GetMapping("/test")
    public String test() {
        return userFeignClient.testRPC();
    }


    /**
     * 新增角色
     *
     * @param roleCreateDTO 新增角色表单数据
     * @return 新增的角色
     */
    @Operation(summary = "新增角色")
    @PreAuthorize("hasAuthority('sys-role:add')")
    @PostMapping
    public ResponseEntity<Role> create(@Validated(GroupOrdered.class) @RequestBody RoleCreateDTO roleCreateDTO) {
        Role role = roleService.create(roleCreateDTO);
        return ResponseEntity.created(URI.create(ROLE_URI + "/" + role.getId())).body(role);
    }

    /**
     * 查看角色详情
     *
     * @param id 角色id
     * @return 角色详情
     */
    @Operation(summary = "查看角色详情")
    @GetMapping(value = "/{id}")
    public RoleVO view(@Parameter(name = "角色id", required = true) @PathVariable(value = "id") Long id) {
        return roleConverter.toRoleVO(roleService.getById(id));
    }

    /**
     * 修改角色
     *
     * @param roleUpdateDTO 修改角色表单数据
     */
    @Operation(summary = "修改角色")
    @PreAuthorize("hasAuthority('sys-role:update')")
    @PutMapping
    public void update(@Validated(GroupOrdered.class) @RequestBody RoleUpdateDTO roleUpdateDTO) {
        roleService.update(roleUpdateDTO);
    }

    /**
     * 检查角色编码唯一性
     *
     * @param query 要排除的角色id & 角色编码
     * @return 是否可用
     */
    @Operation(summary = "检查角色编码唯一性")
    @GetMapping(value = "/check-role-code-unique")
    public Availability checkRoleCodeUnique(RoleCodeUniqueCheckQuery query) {
        return new Availability(roleService.checkRoleCodeUnique(query));
    }


    /**
     * 检查角色名称唯一性
     *
     * @param query 要排除的角色id & 角色名称
     * @return 是否可用
     */
    @Operation(summary = "检查角色名称唯一性")
    @GetMapping(value = "/check-role-name-unique")
    public Availability checkRoleNameUnique(RoleNameUniqueCheckQuery query) {
        return new Availability(roleService.checkRoleNameUnique(query));
    }

    /**
     * 删除角色
     *
     * @param ids 主键id
     */
    @Operation(summary = "根据ID删除角色", description = "多个角色id以逗号分隔")
    @PreAuthorize("hasAuthority('sys-role:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable("ids") List<Long> ids) {
        roleService.deleteByIds(ids);
    }

    /**
     * 加载角色已有的权限（忽略部分选中的权限资源父节点），用于配置角色权限的表单回显
     *
     * @param roleId 角色id
     * @return
     */
    @Operation(summary = "加载角色已有的权限（忽略部分选中的权限资源父节点），用于配置角色权限的表单回显")
    @GetMapping(value = "/{roleId}/authority-leaf-node-keys")
    public List<String> selectLeafNodeAuthorityIdsByRoleId(@PathVariable(value = "roleId") Long roleId) {
        return roleService.selectLeafNodeAuthorityIdsByRoleId(roleId);
    }

    /**
     * 更新角色的权限
     *
     * @param roleId       角色id
     * @param authorityIds 权限资源id数组
     */
    @Parameters({
            @Parameter(name = "roleId", description = "角色id", required = true, in = ParameterIn.PATH),
            @Parameter(name = "authorityIds", description = "权限资源id数组", required = true, in = ParameterIn.DEFAULT)
    })
    @Operation(summary = "更新角色的权限")
    @PreAuthorize("hasAuthority('sys-role:assign-authorities')")
    @PutMapping(value = "/{roleId}/authorities")
    public void updateRoleAuthorities(@NotNull(message = "角色id不能为空") @PathVariable(value = "roleId") Long roleId,
                                      @RequestBody @NotNull(message = "权限资源id不能为空") List<Long> authorityIds) {
        roleService.updateRoleAuthorities(roleId, authorityIds);
    }

    @Operation(summary = "查询所有")
    @GetMapping(value = "/selectAll")
    public List<Role> selectAll() {
        return roleService.selectAll();
    }

    @Operation(summary = "查询角色下拉框数据列表")
    @GetMapping(value = "/dropdown")
    public List<RoleDropdownVO> selectDropdown() {
        return roleService.selectDropdown();
    }
}

