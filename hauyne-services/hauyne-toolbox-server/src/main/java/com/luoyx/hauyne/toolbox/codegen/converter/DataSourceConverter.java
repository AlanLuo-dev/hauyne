package com.luoyx.hauyne.toolbox.codegen.converter;

import com.luoyx.hauyne.toolbox.codegen.entity.DataSource;
import com.luoyx.hauyne.toolbox.codegen.vo.request.DataSourceAddFormVO;
import com.luoyx.hauyne.toolbox.codegen.vo.request.DataSourceEditFormVO;
import com.luoyx.hauyne.toolbox.codegen.vo.response.DataSourcePageResultVO;
import com.querydsl.core.Tuple;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据源转换器
 *
 * @author 1564469545@qq.com
 * @date 2023/3/31 21:37
 */
@Mapper(componentModel = "spring")
public interface DataSourceConverter {

    DataSourcePageResultVO toDataSourceListVO(DataSource dataSource);

    List<DataSourcePageResultVO> toDataSourceListVOList(List<DataSource> dataSourceList);

    DataSource toDataSource(DataSourceAddFormVO dataSourceAddFormVO);

    DataSource toDataSource(DataSourceEditFormVO dataSourceEditFormVO);

    DataSourcePageResultVO toDataSourceListVO(Tuple tuple);
}
