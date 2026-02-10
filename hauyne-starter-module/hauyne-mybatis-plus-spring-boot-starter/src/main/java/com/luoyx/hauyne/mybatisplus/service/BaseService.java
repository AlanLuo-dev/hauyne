package com.luoyx.hauyne.mybatisplus.service;


import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LambdaLogicDeleteWrapper;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;

/**
 * 父Service接口，继承 Mybatis-Plus框架提供的泛型Service接口，可自行扩展
 * （具体业务Service接口请继承当前接口）
 *
 * @author LuoYingxiong
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 泛型分页方法(会执行count查询)
     *
     * @param query 过滤条件和分页参数
     * @param <U>   返回数据的封装类
     * @param <Q>   过滤条件 & 分页条件
     * @return
     */
    <U, Q extends PageQuery> PageResult<U> findPage(Q query);


    /**
     * 泛型分页方法
     *
     * @param query 过滤条件和分页参数
     * @param count 是否进行count查询(true=是， false=否)
     * @param <U>   返回数据的封装类
     * @param <Q>   过滤条件 & 分页条件
     * @return
     */
    <U, Q extends PageQuery> PageResult<U> findPage(Q query, boolean count);

    /**
     * 逻辑删除
     *
     * @param id 主键id
     * @return
     */
    int logicDeleteById(Serializable id);

    /**
     * 按Id批量逻辑删除
     *
     * @param ids 主键id集合
     * @return
     */
    int logicDeleteByBatchIds(Collection<? extends Serializable> ids);

    /**
     * 按Lambda条件删除
     *
     * @param wrapper Lambda条件
     * @return
     */
    int logicDelete(@Param(Constants.WRAPPER) LambdaLogicDeleteWrapper<T> wrapper);
}