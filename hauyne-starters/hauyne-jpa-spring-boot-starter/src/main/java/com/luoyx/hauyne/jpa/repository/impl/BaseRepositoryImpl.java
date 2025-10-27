package com.luoyx.hauyne.jpa.repository.impl;

import com.luoyx.hauyne.jpa.repository.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager em;

    protected final JPAQueryFactory jpaQueryFactory;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    // QuerydslPredicateExecutor<T>
}
