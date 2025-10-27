package com.luoyx.hauyne.admin.sys.service;


import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.api.sys.query.LoginLookupQuery;
import com.luoyx.hauyne.admin.sys.entity.User;
import com.luoyx.hauyne.admin.sys.query.UserPageQuery;
import com.luoyx.hauyne.admin.sys.query.UsernameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.request.ModifyPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.ResetPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.UserCreateDTO;
import com.luoyx.hauyne.admin.sys.request.UserUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.CreatedUserVO;
import com.luoyx.hauyne.admin.sys.response.UserAutoCompleteVO;
import com.luoyx.hauyne.admin.sys.response.UserEditFormVO;
import com.luoyx.hauyne.admin.sys.response.UserPageResultVO;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;

/**
 * @author 罗英雄
 */
public interface UserService extends BaseService<User> {

    /**
     * 登录查询
     *
     * @param query 登录查询条件
     * @return 用户信息
     */
    UserDTO loginLookup(LoginLookupQuery query);

    /**
     * 修改密码
     *
     * @param modifyPasswordDTO 表单参数
     */
    void modifyPassword(ModifyPasswordDTO modifyPasswordDTO) throws Exception;

    /**
     * 按关键词 查询自动完成数据
     *
     * @param keyword 关键词
     * @return
     */
    List<UserAutoCompleteVO> findSuggestions(String keyword);

    PageResult<UserPageResultVO> findPage(UserPageQuery query);

    /**
     * 新增用户
     *
     * @param addDTO 表单参数
     * @return 新增的用户
     */
    CreatedUserVO create(UserCreateDTO addDTO);

    /**
     * 用户名唯一性校验
     *
     * @param query 用户名唯一性校验查询条件
     * @return true 表示用户名可用，false 表示用户名已存在
     */
    boolean checkUserNameUnique(UsernameUniqueCheckQuery query);

    /**
     * 批量删除用户
     *
     * @param userIds 用户id集合
     */
    void deleteByIds(List<Long> userIds);

    /**
     * 用户 表单回显
     *
     * @param id 用户id
     * @return 用户详情
     */
    UserEditFormVO findUserForEdit(Long id);

    /**
     * 修改用户信息
     *
     * @param userUpdateDTO 用户信息
     */
    void update(UserUpdateDTO userUpdateDTO);

    /**
     * 重置密码
     *
     * @param resetPasswordDTO 重置密码DTO
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
