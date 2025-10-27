export interface PageResult<T> {

    // 总记录数
    total: number;

    // 结果集
    rows: T[];

    // 页码
    pageNum: number;

    // 每页记录数
    size: number;

    // 总页数
    pages: number;

    // 排序
    sort: string;

    // 是否还有上一页
    isHasPre: boolean;

    // 是否还有下一页
    isHasNext: boolean;
}
