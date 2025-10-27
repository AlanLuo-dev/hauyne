import {NzTableQueryParams} from "ng-zorro-antd/table";

export class PageQuery {
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
    private sortField: string | null;

    /**
     * 排序方式（1=升序，-1=降序）
     */
    private sortOrder: string | null;

    private filter: Array<{ key: string; value: string[] }> = [];

    constructor(queryParams: NzTableQueryParams) {
        this.pageIndex = queryParams.pageIndex;
        this.pageSize = queryParams.pageSize;

        const currentSort = queryParams.sort.find(item => item.value !== null);
        this.sortField = (currentSort && currentSort.key) || null;
        this.sortOrder = (currentSort && currentSort.value) || null;
        if (queryParams.filter) {
            this.filter = queryParams.filter;
        }
    }
}
