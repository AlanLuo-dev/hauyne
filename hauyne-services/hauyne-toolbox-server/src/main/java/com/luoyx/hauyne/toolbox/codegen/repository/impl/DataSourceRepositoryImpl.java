package com.luoyx.hauyne.toolbox.codegen.repository.impl;

import com.luoyx.hauyne.jpa.repository.impl.BaseRepositoryImpl;
import com.luoyx.hauyne.toolbox.codegen.entity.DataSource;
import com.luoyx.hauyne.toolbox.codegen.entity.QDataSource;
import com.luoyx.hauyne.toolbox.codegen.query.DataSourceQuery;
import com.luoyx.hauyne.toolbox.codegen.query.OffsetPageable;
import com.luoyx.hauyne.toolbox.codegen.repository.DataSourceRepository;
import com.luoyx.hauyne.toolbox.codegen.vo.response.DataSourcePageResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * @author 1564469545@qq.com
 * @date 2023/4/22 15:42
 */
@Repository
public class DataSourceRepositoryImpl extends BaseRepositoryImpl<DataSource, Long> implements DataSourceRepository {

    public DataSourceRepositoryImpl(EntityManager entityManager) {
        super(DataSource.class, entityManager);
    }

    @Override
    public Page<DataSourcePageResultVO> findPage(DataSourceQuery query) {
        QDataSource dataSource = QDataSource.dataSource;

        BooleanBuilder buildPredicate = buildPredicate(query);

        // 查询总记录数
        long count = jpaQueryFactory.selectFrom(dataSource).where(buildPredicate).fetchCount();

        Pageable pageable = new OffsetPageable(query.getFirst(), query.getRows(), toSort(query.getSortOrder(), query.getSortField()));
        // 构建查询
        JPAQuery<DataSourcePageResultVO> jpaQuery = jpaQueryFactory
                .select(Projections.bean(DataSourcePageResultVO.class,
                        dataSource.id,
                        dataSource.dataSourceName,
                        dataSource.driverClassName,
                        dataSource.url,
                        dataSource.username,
                        dataSource.password,
                        dataSource.createdBy,
                        dataSource.createdTime,
                        dataSource.lastUpdatedBy,
                        dataSource.lastUpdatedTime))
                .from(dataSource)
                .where(buildPredicate(query))
                .offset(query.getFirst())
                .limit(query.getRows());


        // 分页查询
        List<DataSourcePageResultVO> dataSourceList = jpaQuery.fetch();

        // 构建Page对象
        return new PageImpl<>(dataSourceList, pageable, count);
    }

    /**
     * 将 PrimeNgPageQuery 中的排序字段和排序方式转换为 Sort 对象
     *
     * @return Sort 对象
     */
    private Sort toSort(Integer sortOrder, String sortField) {
        Sort.Direction direction = (sortOrder != null && sortOrder == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (sortField != null) {
            return Sort.by(direction, sortField);
        } else {
            return Sort.unsorted();
        }
    }

    private BooleanBuilder buildPredicate(DataSourceQuery query) {
        QDataSource ds = QDataSource.dataSource;

        BooleanBuilder builder = new BooleanBuilder();
        if (null != query.getCreatedBy()) {
            builder.and(ds.createdBy.eq(query.getCreatedBy()));
        }
        if (StringUtils.isNotBlank(query.getDataSourceName())) {
            builder.and(ds.dataSourceName.like("%" + query.getDataSourceName() + "%"));
        }

        return builder;
    }

}
