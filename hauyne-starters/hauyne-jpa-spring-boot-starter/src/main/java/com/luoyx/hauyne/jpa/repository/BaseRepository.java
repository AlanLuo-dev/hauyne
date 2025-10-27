package com.luoyx.hauyne.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * JPA Repository基础接口
 *
 * @author Alan.Luo
 * @since 2023/4/27 21:52
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepositoryImplementation<T, ID> {
}
