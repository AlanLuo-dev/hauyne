package com.luoyx.hauyne.admin.api.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 图标类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum IconTypeEnum implements EnumSpec<String, IconTypeEnum> {

    ICONFONT("iconfont", "iconfont图标", "static/icons/iconfont.svg"),
    MATERIAL_SYMBOLS("material-symbols", "Material Symbols图标", "static/icons/material-symbols.svg"),
    NG_ZORRO("ng-zorro", "NG-ZORRO自带图标", "static/icons/ng-zorro.svg");

    @EnumValue
    private final String value;
    private final String label;
    private final String resourcePath; // Classpath 下的文件路径

    /**
     * 获取 SVG 的完整文本内容
     */
    public String getSvgContent() {
        try {
            ClassPathResource resource = new ClassPathResource(this.resourcePath);
            try (InputStream is = resource.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
