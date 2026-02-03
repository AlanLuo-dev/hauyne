package com.luoyx.hauyne.web.enums.convert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举转换上下文：用于存储枚举转换过程中出现的错误信息
 */
public final class EnumConvertContext {

    /**
     * 私有构造方法，防止外部实例化
     */
    private EnumConvertContext() {
    }

    private static final ThreadLocal<Map<String, EnumConvertErrorGroup>> HOLDER =
            ThreadLocal.withInitial(LinkedHashMap::new);

    /**
     * 添加枚举转换错误：将指定枚举名称和无效值添加到上下文
     *
     * @param enumName     枚举名称
     * @param invalidValue 无效值
     */
    public static void add(String enumName, String invalidValue) {
        Map<String, EnumConvertErrorGroup> map = HOLDER.get();
        map.computeIfAbsent(enumName, EnumConvertErrorGroup::new)
                .addInvalidValue(invalidValue);
    }

    /**
     * 获取并清空上下文：返回当前线程存储的所有枚举转换错误组，并清空上下文
     *
     * @return 枚举转换错误组列表
     */
    public static List<EnumConvertErrorGroup> getAndClear() {
        Map<String, EnumConvertErrorGroup> map = HOLDER.get();
        List<EnumConvertErrorGroup> snapshot = new ArrayList<>(map.values());
        HOLDER.remove(); // 清上下文
        return snapshot; // 返回快照
    }
}
