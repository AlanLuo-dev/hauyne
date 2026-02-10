package com.luoyx.hauyne.toolbox.codegen.query;

/**
 * @author 1564469545@qq.com
 * @date 2023/4/10 21:52
 */
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetPageable implements Pageable {
    

    private int offset; // 偏移量
    private int pageSize; // 页面大小
    private Sort sort; // 排序

    public OffsetPageable(int offset, int pageSize, Sort sort) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        // 根据偏移量和页面大小计算当前页码
        return offset / pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        // 根据当前偏移量和页面大小计算下一页的偏移量
        return new OffsetPageable((int) (offset + pageSize), pageSize, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        // 根据当前偏移量和页面大小计算上一页的偏移量，并确保不小于0
        return new OffsetPageable(Math.max(0, (int) (offset - pageSize)), pageSize, sort);
    }

    @Override
    public Pageable first() {
        // 创建一个偏移量为0的新的 OffsetPageable 对象，表示第一页
        return new OffsetPageable(0, pageSize, sort);
    }

    /**
     * @param pageNumber 
     * @return
     */
    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        // 判断是否有上一页，根据当前偏移量是否大于0
        return offset > 0;
    }
}
