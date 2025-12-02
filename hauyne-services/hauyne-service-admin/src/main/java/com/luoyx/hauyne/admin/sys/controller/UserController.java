package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.amqp.producer.UserSnapshotProducer;
import com.luoyx.hauyne.admin.api.sys.UserAPI;
import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.api.sys.query.LoginLookupQuery;
import com.luoyx.hauyne.admin.sys.mapper.UserProfileMapper;
import com.luoyx.hauyne.admin.sys.query.UserPageQuery;
import com.luoyx.hauyne.admin.sys.request.ModifyPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.ResetPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.UserCreateDTO;
import com.luoyx.hauyne.admin.sys.request.UserUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.CreatedUserVO;
import com.luoyx.hauyne.admin.sys.response.UserAutoCompleteVO;
import com.luoyx.hauyne.admin.sys.response.UserEditFormVO;
import com.luoyx.hauyne.admin.sys.response.UserPageResultVO;
import com.luoyx.hauyne.admin.sys.service.UserService;
import com.luoyx.hauyne.api.Availability;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import static com.luoyx.hauyne.admin.api.sys.UserAPI.SYS_USERS;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping(value = SYS_USERS)
@RequiredArgsConstructor
@Validated
public class UserController implements UserAPI {

    private final UserService userService;
    private final UserSnapshotProducer userSnapshotProducer;
    private final UserProfileMapper userProfileMapper;

    /**
     * 按用户名查找
     *
     * @param loginLookupQuery 登录查询条件
     * @return 用户DTO对象
     */
    @Operation(summary = "按用户名查询用户信息")
    @GetMapping(value = "/login-lookup")
    @Override
    public UserDTO loginLookup(LoginLookupQuery loginLookupQuery) {
        return userService.loginLookup(loginLookupQuery);
    }

    @Operation(summary = "分页查询用户信息")
    @GetMapping
    public PageResult<UserPageResultVO> findPage(@Validated UserPageQuery userPageQuery) {
        return userService.findPage(userPageQuery);
    }

    /**
     * 新增用户
     *
     * @param userCreateDTO 表单参数
     * @return 新增的用户
     */
    @Operation(summary = "新增用户")
    @PostMapping
    public ResponseEntity<CreatedUserVO> create(@Validated @RequestBody UserCreateDTO userCreateDTO) {
        CreatedUserVO createdUserVO = userService.create(userCreateDTO);
        return ResponseEntity.created(URI.create(SYS_USERS + "/" + createdUserVO.getId())).body(createdUserVO);
    }

    /**
     * 按用户id获取用于表单回显的用户数据
     *
     * @param id 用户id
     * @return 用户 表单回显数据
     */
    @Operation(summary = "按用户id获取用于表单回显的用户数据")
    @GetMapping(value = "/{id}")
    public UserEditFormVO findUserForEdit(@Parameter(name = "用户id", required = true)
                                          @PathVariable(value = "id") Long id) {
        return userService.findUserForEdit(id);
    }

    /**
     * 修改用户
     *
     * @param userUpdateDTO 用户信息
     */
    @Operation(summary = "修改用户")
    @PutMapping
    public void update(@Validated @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(userUpdateDTO);
    }

    @Operation(summary = "检查输入的【用户名】是否已被占用")
    @GetMapping(value = "/check-username-unique")
    public Availability checkUsernameUnique(@Schema(description = "要排除的用户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
                                            @RequestParam(value = "excludeUserId", required = false)
                                            Long excludeUserId,
                                            @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
                                            @NotBlank(message = "用户名不能为空")
                                            @RequestParam(value = "username")
                                            String username) {
        return new Availability(userService.isUserNameUnique(excludeUserId, username));
    }

    /**
     * 重置密码
     *
     * @param resetPasswordDTO 重置密码DTO
     */
    @Operation(summary = "重置密码")
    @PutMapping(value = "/reset-password")
    public void resetPassword(@Validated @RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.resetPassword(resetPasswordDTO);
    }

    /**
     * 批量 删除用户
     *
     * @param ids 用户id 集合
     */
    @Operation(summary = "删除用户")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@Parameter(name = "用户id", required = true) @PathVariable(value = "id") List<Long> ids) {
        userService.deleteByIds(ids);
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordDTO 表单参数
     * @throws Exception
     */
    @Operation(summary = "修改密码")
    @PatchMapping(value = "/modify-password")
    public void modifyPassword(@Validated @RequestBody ModifyPasswordDTO modifyPasswordDTO) throws Exception {
        userService.modifyPassword(modifyPasswordDTO);
    }

    /**
     * 用户自动完成接口（按用户名、真实姓名、昵称、手机号、电子邮箱地址模糊查询）
     *
     * @param keyword 关键词
     * @return
     */
    @Operation(summary = "用户自动完成接口（按用户名、真实姓名、昵称、手机号、电子邮箱地址模糊查询）")
    @Parameter(name = "keyword", description = "关键词（用户名、真实姓名、昵称、手机号、电子邮箱地址）", required = true, in = ParameterIn.QUERY)
    @GetMapping(value = "/suggestions")
    public List<UserAutoCompleteVO> suggestions(@RequestParam(value = "keyword") String keyword) {
        return userService.findSuggestions(keyword);
    }

}
