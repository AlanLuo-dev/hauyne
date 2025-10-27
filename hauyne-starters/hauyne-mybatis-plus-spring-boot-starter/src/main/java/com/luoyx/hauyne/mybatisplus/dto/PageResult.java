package com.luoyx.hauyne.mybatisplus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页结果类
 *
 * @param <T>
 * @author 罗英雄
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> {

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private Long total;

    /**
     * 结果集
     */
    @Schema(description = "结果集")
    private List<T> rows;

    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNum;

    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数")
    private Integer size;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Integer pages;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private String sort;

    /**
     * 是否还有上一页
     */
    @Schema(description = "是否还有上一页【true=是 false=否】")
    private boolean hasPre;

    /**
     * 是否还有下一页
     *
     * @date 2022.06.15 如果只定义方法，且把@ApiModelProperty 写在方法上，会导致swagger文档不显示返回参数（hasPre也一样）
     */
    @Schema(description = "是否还有下一页【true=是 false=否】")
    private boolean hasNext;

    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理， 而出现一些问题。
     *
     * @param list page结果
     */
//    public PageResult(List<T> list) {
//        if (list instanceof Page) {
//            Page<T> page = (Page<T>) list;
//            this.pageNum = page.getPageNum();
//            this.size = page.size();
//            this.total = page.getTotal();
//            this.pages = page.getPages();
//            this.rows = page;
//            this.sort = page.getOrderBy();
//
//            this.hasPre =  (this.pageNum - 1 >= 1);
//            this.hasNext = (this.pageNum + 1 <= this.pages);
//        }
//    }

}
