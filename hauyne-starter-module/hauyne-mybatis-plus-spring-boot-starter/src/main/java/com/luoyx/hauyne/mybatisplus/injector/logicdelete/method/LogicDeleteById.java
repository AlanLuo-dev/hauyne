package com.luoyx.hauyne.mybatisplus.injector.logicdelete.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.LogicDeleteProperties;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.stream.Collectors;

/**
 * 自定义通用方法：按Id逻辑删除
 *
 * @author luo_yingxiong@163.com
 * @since 0.2.0
 */
public class LogicDeleteById extends AbstractMethod {

    private final LogicDeleteProperties logicDeleteProperties;

    public LogicDeleteById(String methodName, LogicDeleteProperties logicDeleteProperties) {
        super(methodName);
        this.logicDeleteProperties = logicDeleteProperties;
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String tableName = tableInfo.getTableName();
        final String deletedTableName = logicDeleteProperties.getDeletedTableNamePrefix() + tableName;
        final String keyColumn = tableInfo.getKeyColumn();

        final String fields = keyColumn + "," + tableInfo.getFieldList().stream()
                .map(TableFieldInfo::getColumn)
                .collect(Collectors.joining(","));

        final String deletedTableFields = fields + ", "
                + logicDeleteProperties.getDeletedByColumnName() + ", "
                + logicDeleteProperties.getDeletedTimeColumnName();
        final String whereIdEquals = " WHERE " + keyColumn + "= #{id};";

        String sql = "<script>\n" +
                " INSERT INTO " + deletedTableName + "( " + deletedTableFields + " )" +
                " SELECT " + fields + ", #{deletedBy}, #{deletedTime} FROM " + tableName + whereIdEquals +
                " DELETE FROM " + tableName + whereIdEquals +
                "\n</script>";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }
}
