package com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.luoyx.hauyne.security.context.UserContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class LambdaLogicDeleteWrapper<T> extends AbstractLambdaWrapper<T, LambdaLogicDeleteWrapper<T>>
        implements Query<LambdaLogicDeleteWrapper<T>, T, SFunction<T, ?>> {

    /**
     * 查询字段
     */
    private SharedString sqlSelect = new SharedString();

    // 审计字段
    private final Long deletedBy;
    private final LocalDateTime deletedTime;


    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
     */
    public LambdaLogicDeleteWrapper() {
        this(null);
    }

    public LambdaLogicDeleteWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();

        // 设置为当前用户id、当前时间
        this.deletedBy = Optional.ofNullable(UserContextHolder.getUserId()).orElse(0L);
        this.deletedTime = LocalDateTime.now();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(...)
     */
    LambdaLogicDeleteWrapper(T entity, Class<T> entityClass, SharedString sqlSelect, AtomicInteger paramNameSeq,
                             Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments) {
        super.setEntity(entity);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.sqlSelect = sqlSelect;
        setEntityClass(entityClass);

        // 设置为当前用户id、当前时间
        this.deletedBy = Optional.ofNullable(UserContextHolder.getUserId()).orElse(0L);
        this.deletedTime = LocalDateTime.now();
    }

    @SafeVarargs
    @Override
    public final LambdaLogicDeleteWrapper<T> select(SFunction<T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(columnsToString(false, columns));
        }
        return typedThis;
    }

    @Override
    public LambdaLogicDeleteWrapper<T> select(Predicate<TableFieldInfo> predicate) {
        return select(getEntityClass(), predicate);
    }

    /**
     * 过滤查询的字段信息(主键除外!)
     * <p>例1: 只要 java 字段名以 "test" 开头的             -> select(i -&gt; i.getProperty().startsWith("test"))</p>
     * <p>例2: 只要 java 字段属性是 CharSequence 类型的     -> select(TableFieldInfo::isCharSequence)</p>
     * <p>例3: 只要 java 字段没有填充策略的                 -> select(i -&gt; i.getFieldFill() == FieldFill.DEFAULT)</p>
     * <p>例4: 要全部字段                                   -> select(i -&gt; true)</p>
     * <p>例5: 只要主键字段                                 -> select(i -&gt; false)</p>
     *
     * @param predicate 过滤方式
     * @return this
     */
    @Override
    public LambdaLogicDeleteWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        setEntityClass(entityClass);
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(getEntityClass()).chooseSelect(predicate));
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }

    /**
     * 用于生成嵌套 sql
     * <p>故 sqlSelect 不向下传递</p>
     */
    @Override
    protected LambdaLogicDeleteWrapper<T> instance() {
        return new LambdaLogicDeleteWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs, new MergeSegments());
    }

    @Override
    public LambdaLogicDeleteWrapper<T> select(boolean condition, List<SFunction<T, ?>> columns) {
        return null;
    }
}
