package com.luoyx.hauyne.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回可用性结果
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Availability {

    @Schema(description = "是否可用【true=是, false=否】")
    private boolean availability;
}
