package com.luoyx.hauyne.web.autoconfigure;

import java.io.Serializable;

/**
 * 枚举统一接口：所有需要嵌套返回的枚举必须实现
 */
public interface BaseEnum<T extends Serializable> {
    T getValue(); // 枚举编码（String/Integer 均可）

    String getLabel(); // 枚举描述（前端展示用）
}
