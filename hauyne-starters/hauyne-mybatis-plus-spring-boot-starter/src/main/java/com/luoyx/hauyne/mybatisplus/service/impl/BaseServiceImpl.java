package com.luoyx.hauyne.mybatisplus.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LambdaLogicDeleteWrapper;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LogicDeleteByBatchIdsCondition;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition.LogicDeleteByIdCondition;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 父Service类，继承了Mybatis-Plus框架的ServiceImpl类，可自行扩展
 * （具体业务层ServiceImpl 请继承当前类）
 *
 * @author LuoYingxiong
 */
public class BaseServiceImpl<N extends GenericMapper<T>, T> extends ServiceImpl<N, T> implements BaseService<T> {

    /**
     * 泛型分页方法(会执行count查询)
     *
     * @param query 过滤条件和分页参数
     * @param <U>   返回数据的封装类
     * @param <Q>   过滤条件 & 分页条件
     * @return 结果集
     */
    @Override
    public <U, Q extends PageQuery> PageResult<U> findPage(Q query) {
        return findPage(query, true);
    }

    /**
     * 泛型分页方法
     *
     * @param query 过滤条件和分页参数
     * @param count 是否进行count查询(true=是，false=否)
     * @param <U>   返回数据的封装类
     * @param <Q>   过滤条件 & 分页条件
     * @return 结果集
     */
    @Override
    public <U, Q extends PageQuery> PageResult<U> findPage(Q query, boolean count) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize(), count);
        return setPageResult(baseMapper.findPage(query));
    }

    public <U> PageResult<U> setPageResult(List<U> list) {
        PageResult<U> pageResult = new PageResult<>();
        if (list instanceof Page) {
            Page<U> page = (Page<U>) list;
            pageResult.setTotal(page.getTotal());
            pageResult.setRows(list);
            pageResult.setPageNum(page.getPageNum());
            pageResult.setSize(page.size());
            pageResult.setPages(page.getPages());
            pageResult.setSort(page.getOrderBy());
            pageResult.setHasPre(page.getPageNum() - 1 >= 1);
            pageResult.setHasNext(page.getPageNum() + 1 <= page.getPages());
        }

        return pageResult;
    }

    @Override
    public int logicDeleteById(Serializable id) {
        return baseMapper.logicDeleteById(new LogicDeleteByIdCondition(id));
    }

    @Override
    public int logicDeleteByBatchIds(Collection<? extends Serializable> ids) {
        return baseMapper.logicDeleteByBatchIds(new LogicDeleteByBatchIdsCondition(ids));
    }

    /**
     * 按Lambda条件删除
     *
     * @param wrapper Lambda条件
     * @return
     */
    @Override
    public int logicDelete(LambdaLogicDeleteWrapper<T> wrapper) {
        return baseMapper.logicDelete(wrapper);
    }
}