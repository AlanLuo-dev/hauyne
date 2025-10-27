package com.luoyx.hauyne.toolbox.codegen.repository;

import com.luoyx.hauyne.jpa.repository.BaseRepository;
import com.luoyx.hauyne.toolbox.codegen.entity.DataSource;
import com.luoyx.hauyne.toolbox.codegen.query.DataSourceQuery;
import com.luoyx.hauyne.toolbox.codegen.vo.response.DataSourcePageResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface DataSourceRepository extends BaseRepository<DataSource, Long> {

    Page<DataSourcePageResultVO> findPage(DataSourceQuery query);
}
