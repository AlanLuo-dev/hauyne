package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.sys.service.DeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "DeptController", description = "部门管理")
@RestController
@RequestMapping("/sys/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

//    /**
//     * 分页查询角色
//     *
//     * @param query
//     * @return
//     */
//    @Operation(summary = "分页查询角色")
//    @GetMapping
//    public PageResult<DeptPageDataVO> pagelist(@Validated RoleQuery query) {
//        return deptService.pageList(query);
//    }
}
