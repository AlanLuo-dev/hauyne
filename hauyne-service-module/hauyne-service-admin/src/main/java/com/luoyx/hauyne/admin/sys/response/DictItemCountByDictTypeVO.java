package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 某个字典类型下的字典选项的数量 VO类
 *
 * @author 1564469545@qq.com
 * @date 2022/12/10 22:30
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class DictItemCountByDictTypeVO {

    /**
     * 某个字典类型下的字典选项的数量
     */
    @Schema(description = "某个字典类型下的字典选项的数量")
    private Integer dictItemCount;
}
