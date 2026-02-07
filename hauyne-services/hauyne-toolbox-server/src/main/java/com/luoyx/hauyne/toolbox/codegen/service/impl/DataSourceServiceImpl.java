package com.luoyx.hauyne.toolbox.codegen.service.impl;

import com.luoyx.hauyne.jpa.dto.PageResult;
import com.luoyx.hauyne.jpa.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.toolbox.codegen.entity.DataSource;
import com.luoyx.hauyne.toolbox.codegen.query.DataSourceQuery;
import com.luoyx.hauyne.toolbox.codegen.repository.DataSourceRepository;
import com.luoyx.hauyne.toolbox.codegen.service.DataSourceService;
import com.luoyx.hauyne.toolbox.codegen.vo.response.DataSourcePageResultVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;



/**
 * <p>
 * 数据源配置表 服务实现类
 * </p>
 *
 * @author 1564469545@qq.com
 * @since 2023-03-31
 */
@Service
public class DataSourceServiceImpl extends BaseServiceImpl<DataSource, Long, DataSourceRepository> implements DataSourceService {

    /**
     * 表示不可用
     */
    public static final String NOT_AVAILABLE = "N/A";

    public DataSourceServiceImpl(DataSourceRepository repository) {
        super(repository);
    }


    @Override
    public PageResult<DataSourcePageResultVO> pageList(DataSourceQuery query) {
        Page<DataSourcePageResultVO> page = repository.findPage(query);
        List<DataSourcePageResultVO> list = page.getContent();
        // 创建人、最后修改人id集合
        Set<Long> userIds = list.stream()
                .map(item -> Arrays.asList(item.getCreatedBy(), item.getLastUpdatedBy()))
                .flatMap(List::stream)
                .collect(Collectors.toSet());

//        Map<Long, String> cachedUserFullNamesMap = userCache.getUserFullName(userIds);
        Map<Long, String> cachedUserFullNamesMap = null;
        list = list.stream()
                .map(item -> {
                    item.setCreatedByFullName(cachedUserFullNamesMap.getOrDefault(item.getCreatedBy(), NOT_AVAILABLE));
                    item.setLastModifiedByFullName(cachedUserFullNamesMap.getOrDefault(item.getLastUpdatedBy(), NOT_AVAILABLE));

                    return item;
                })
                .collect(Collectors.toList());

        PageResult<DataSourcePageResultVO> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotalElements());
        pageResult.setRows(list);
        pageResult.setPageNum(page.getNumber());
        pageResult.setSize(page.getSize());
        pageResult.setPages(page.getTotalPages());
        pageResult.setSort(page.getSort().toString());
        pageResult.setHasPre(page.hasPrevious());
        pageResult.setHasNext(page.hasNext());

        return pageResult;
    }

}
