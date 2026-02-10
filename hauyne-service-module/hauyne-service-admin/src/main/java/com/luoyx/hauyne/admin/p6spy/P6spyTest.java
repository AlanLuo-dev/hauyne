//package com.luoyx.hauyne.admin.p6spy;
//
//import com.luoyx.hauyne.framework.utils.JsonUtil;
//import org.apache.commons.lang3.StringUtils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class P6spyTest implements MessageFormattingStrategy {
//
//    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//
//    /**
//     * 自定义sql日志打印
//     *
//     * @param connectionId 连接标识
//     * @param now          当前时间
//     * @param elapsed      执行耗时ms（需要注意的是这里的耗时指的是从发送sql到服务器截止到收到服务器响应结果的总耗时，而不是sql本身在服务器的执行时间）
//     * @param category     statement 操作的类型，比如查询，更新，commit，rollback等
//     * @param prepared     预编译sql语句 不打印具体的参数
//     * @param sql          真实的sql语句 参数占位符会被真正的参数值替换
//     * @param url          数据库url连接
//     * @return {@link String}
//     */
//    @Override
//    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
//        Map<String, Object> message = new LinkedHashMap<>(4);
//        if (StringUtils.isNoneBlank(prepared, sql) ) {
//            message.put("开始时间:", format.format(new Date(Long.parseLong(now))));
//            message.put("执行耗时:", String.format("%sms", elapsed));
//            String newPrepared = prepared.replace("   ", "").replace("\n", " ");
//            message.put("预编译SQL语句:", newPrepared);
//            String newSql = sql.replace("   ", "").replace("\n", " ");
//            message.put("真实执行SQL语句:", newSql);
//            return JsonUtil.toStringPretty(message);
//        }
//        return null;
//    }
//}