package com.luoyx.hauyne.mybatisplus.injector.logicdelete.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.luoyx.hauyne.mybatisplus.injector.logicdelete.LogicDeleteProperties;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.stream.Collectors;

public class LogicDelete extends AbstractMethod {

    private final LogicDeleteProperties logicDeleteProperties;

    public LogicDelete(String methodName, LogicDeleteProperties logicDeleteProperties) {
        super(methodName);
        this.logicDeleteProperties = logicDeleteProperties;
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String tableName = tableInfo.getTableName();
        final String deletedTableName = logicDeleteProperties.getDeletedTableNamePrefix() + tableName;
        final String fields = tableInfo.getKeyColumn() + "," + tableInfo.getFieldList().stream()
                .map(TableFieldInfo::getColumn)
                .collect(Collectors.joining(","));

        final String deletedTableFields = fields + ", " + logicDeleteProperties.getDeletedByColumnName() + ", " +
                logicDeleteProperties.getDeletedTimeColumnName();

        String whereEntityWrapper = sqlWhereEntityWrapper(true, tableInfo) + ";";
        String sql = "<script>\n" +
                " INSERT INTO " + deletedTableName + "( " + deletedTableFields + " )" +
                " SELECT " + fields + ", #{" + Constants.WRAPPER + ".deletedBy}, #{" + Constants.WRAPPER + ".deletedTime} FROM " +
                tableName + whereEntityWrapper +
                " DELETE FROM " + tableName + whereEntityWrapper +
                "\n</script>";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }
}
