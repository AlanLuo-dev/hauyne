package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.sys.entity.User;
import com.luoyx.hauyne.admin.sys.query.UsernameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.response.UserAutoCompleteVO;
import com.luoyx.hauyne.admin.sys.response.UserEditFormVO;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author LuoYingxiong
 * @since 2020-05-10
 */
public interface UserMapper extends GenericMapper<User> {

    /**
     * 按用户名查询用户
     *
     * @param username
     * @return
     */
    UserDTO findByUserName(@Param("username") String username);

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    UserEditFormVO detail(@Param("id") Long id);

    /**
     * 按关键词 查询自动完成数据
     *
     * @param keyword 关键词
     * @return
     */
    List<UserAutoCompleteVO> findSuggestions(@Param("keyword") String keyword);

    /**
     * 用户名唯一性校验
     *
     * @param query 用户名唯一性校验查询条件
     * @return 返回大于0 则表示用户名已存在，返回0 则表示用户名可用
     */
    default Long countByUserName(UsernameUniqueCheckQuery query) {
        return this.selectCount(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, query.getUsername())
                        .ne(query.getExcludeUserId() != null, User::getId, query.getExcludeUserId())
        );
    }

    /**
     * 按用户id获取用于表单回显的用户数据
     *
     * @param id 用户id
     * @return 用户 表单回显数据
     */
    UserEditFormVO selectForUserEditForm(Long id);

    /**
     * 重置密码
     *
     * @param userId         用户id
     * @param bcryptPassword bcrypt加密后的密码
     */
    default void resetPassword(Long userId, String bcryptPassword) {
        User user = new User();
        user.setId(userId);
        user.setPassword(bcryptPassword);
        this.updateById(user);
    }
}
