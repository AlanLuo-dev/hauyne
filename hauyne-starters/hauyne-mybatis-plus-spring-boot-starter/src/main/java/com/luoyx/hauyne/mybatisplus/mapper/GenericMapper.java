package com.luoyx.hauyne.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LambdaLogicDeleteWrapper;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LogicDeleteByBatchIdsCondition;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LogicDeleteByIdCondition;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 父Mapper接口，继承 Mybatis-Plus框架提供的BaseMapper,可自行扩展
 * （具体业务Mapper层继承当前接口）
 *
 * @author LuoYingxiong
 */
public interface GenericMapper<T> extends BaseMapper<T> {

    /**
     * 泛型分页方法 For PageHelper 该方法只需做一个普通查询，分页操作由PageHelper拦截Sql处理
     *
     * @param query
     * @param <U>
     * @param <Q>
     * @return
     */
    <U, Q extends PageQuery> List<U> findPage(Q query);

    /**
     * 按ID单个逻辑删除
     * 1、先按id查询出原始数据
     * 2、将原始数据插入到归档表中
     * 3、将原始数据从原始表删除
     *
     * @param idCondition 含删除条件（单个ID）和审计字段（删除者ID、删除时间）
     * @return 删除的条数
     */
    @Transactional(rollbackFor = Exception.class)
    int logicDeleteById(LogicDeleteByIdCondition idCondition);

    /**
     * 按Id批量逻辑删除
     *
     * @param batchIdsCondition 含删除条件（多个ID）和审计字段（删除者ID、删除时间）
     * @return 删除的条数
     */
    @Transactional(rollbackFor = Exception.class)
    int logicDeleteByBatchIds(LogicDeleteByBatchIdsCondition batchIdsCondition);

    /**
     * 逻辑删除
     *
     * @param wrapper LambdaLogicDeleteWrapper
     * @return 删除的条数
     */
    @Transactional(rollbackFor = Exception.class)
    int logicDelete(@Param(Constants.WRAPPER) LambdaLogicDeleteWrapper<T> wrapper);
}
