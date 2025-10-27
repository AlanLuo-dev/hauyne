export interface AuthorityQuery {

    /**
     * 权限类型【? 表示这个属性有可能不存在】
     */
    authorityType?: string;

    /**
     * 权限名称【? 表示这个属性有可能不存在】
     */
    authorityName?: string;

    /**
     * 排序字段
     */
    sortField: string | null;

    /**
     * 排序方式（1=升序，-1=降序）
     */
    sortOrder: string | null;
}
