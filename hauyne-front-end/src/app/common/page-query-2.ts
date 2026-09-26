import {NzTableQueryParams} from "ng-zorro-antd/table";

export class PageQuery2 {
    /**
     * 页码，默认为1
     */
    private pageIndex: number = 1;

    /**
     * 每页显示行数，默认20
     */
    private pageSize: number = 20;

    /**
     * 排序字段
     */
    private sortField: string | null = null;

    /**
     * 排序方式（1=升序，-1=降序）
     */
    private sortOrder: string | null = null;

    private filter: Array<{ key: string; value: string[] }> = [];

    /**
     * 将表格分页/排序参数更新到当前 Query 实例
     */
    updateQueryParams(queryParams: NzTableQueryParams): this {
        this.pageIndex = queryParams.pageIndex;
        this.pageSize = queryParams.pageSize;

        const currentSort = queryParams.sort.find(item => item.value !== null);
        this.sortField = currentSort?.key || null;
        this.sortOrder = currentSort?.value || null;
        if (queryParams.filter) {
            this.filter = queryParams.filter;
        }
        return this;
    }

    /**
     * 转换为 HttpParams 兼容的扁平 Plain Object
     */
    toHttpParams(): { [key: string]: any } {

        // 只过滤掉值为 null 或 undefined 的键值对
        return Object.entries(this)
            .filter(([key, value]) => value !== null && value !== undefined)
            .reduce((newObj, [key, value]) => {
                newObj[key] = value;
                return newObj;
            }, {} as { [key: string]: any });
    }
}
