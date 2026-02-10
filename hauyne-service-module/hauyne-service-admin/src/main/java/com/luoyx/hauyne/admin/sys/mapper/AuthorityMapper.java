package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.query.AuthorityCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityQuery;
import com.luoyx.hauyne.admin.sys.response.AuthorityCheckBoxTreeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityDetailVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeSelectVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityVO;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-18
 */
public interface AuthorityMapper extends GenericMapper<Authority> {

    /**
     * 按用户id查询权限
     *
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> findAuthoritiesByUserId(@Param("userId") Long userId);


    /**
     * 按用户id查询权限
     *
     * @param userId 用户id
     * @return 菜单集合
     */
    List<UserDTO.SideMenuDTO> selectSideMenuByUserId(@Param("userId") Long userId);

    /**
     * 查询所有权限数据
     *
     * @param query
     * @return
     */
    List<AuthorityVO> findList(AuthorityQuery query);

    List<AuthorityTreeSelectVO> selectParentAuthorityFormData();

    List<AuthorityCheckBoxTreeVO> selectForCheckBoxTree();

    /**
     * 查询权限编码是否已存在
     *
     * @param query 权限编码唯一性校验 查询条件
     * @return 返回大于0 则表示权限编码已存在，返回0 则表示权限编码可用
     */
    default Long countAuthorityCode(AuthorityCodeUniqueCheckQuery query) {
        return selectCount(
                Wrappers.<Authority>lambdaQuery()
                        .eq(Authority::getAuthorityCode, query.getAuthorityCode())
                        .ne(query.getExcludeAuthorityId() != null, Authority::getId, query.getExcludeAuthorityId())
        );
    }

    /**
     * 校验权限名称是否已存在
     *
     * @param query 权限名称唯一性校验 查询条件
     * @return 返回大于0 则表示权限名称已存在，返回0 则表示权限名称可用
     */
    default Long countAuthorityName(AuthorityNameUniqueCheckQuery query) {
        return selectCount(
                Wrappers.<Authority>lambdaQuery()
                        .eq(Authority::getAuthorityName, query.getAuthorityName())
                        .ne(query.getExcludeAuthorityId() != null, Authority::getId, query.getExcludeAuthorityId())
        );
    }

    /**
     * 统计子权限资源数量
     *
     * @param parentId 父级权限资源id
     * @return 子权限资源数量
     */
    long selectChildAuthorityByParentId(@Param("parentId") Long parentId);

    AuthorityDetailVO view(Long id);

    /**
     * 按父级权限资源id查询最大排序
     *
     * @param parentId 父级权限资源id
     * @return 最大排序
     */
    Integer selectMaxSortByParentId(@Param("parentId") Long parentId);

    /**
     * 查询角色已有的权限名称
     *
     * @param roleId 角色id
     * @return 角色已有的权限名称
     */
    List<String> selectAuthorityNamesByRoleId(@Param("roleId") Long roleId);

    default List<String> selectAuthorityNamesByIds(List<Long> authorityIds) {
        return selectList(
                new LambdaQueryWrapper<Authority>()
                        .select(Authority::getAuthorityName)
                        .in(Authority::getId, authorityIds)
        )
                .stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.toList());

    }

    /**
     * 查询某个权限（authorityId）被哪些角色（role_name）所引用。
     *
     * @param authorityId 权限id
     * @return 角色名称集合
     */
    List<String> findRoleNamesByAuthorityId(@Param("authorityId") Long authorityId);
}
