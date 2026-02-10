package com.luoyx.hauyne.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.LogicDeleteProperties;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.method.LogicDelete;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.method.LogicDeleteByBatchIds;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.method.LogicDeleteById;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 自定义SQL注入器
 *
 * @author luo_yingxiong@163.com
 */
@RequiredArgsConstructor
public class MySqlInjector extends DefaultSqlInjector {

    private final LogicDeleteProperties logicDeleteProperties;

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        // 增加自定义通用方法: 逻辑删除
        methodList.add(new LogicDeleteById("logicDeleteById", logicDeleteProperties));
        methodList.add(new LogicDeleteByBatchIds("logicDeleteByBatchIds", logicDeleteProperties));
        methodList.add(new LogicDelete("logicDelete", logicDeleteProperties));

        return methodList;
    }
}
