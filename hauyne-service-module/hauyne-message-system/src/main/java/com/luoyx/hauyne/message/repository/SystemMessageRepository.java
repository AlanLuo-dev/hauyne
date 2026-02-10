package com.luoyx.hauyne.message.repository;

import com.luoyx.hauyne.message.repository.document.SystemMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 罗英雄
 * @date 2021/9/9 17:54
 */
public interface SystemMessageRepository extends ElasticsearchRepository<SystemMessage, String>{

}
