export interface AuthorityAddData{

    /**
     * 父节点id
     */
    parentId: number;

    /**
     * 权限类型
     */
    authorityType: string;

    /**
     * 权限编码
     */
    authorityCode: string;

    /**
     * 权限名称
     */
    authorityName: string;

    /**
     * 图标
     */
    icon: string;

    /**
     * 请求路径
     */
    path: string;

    /**
     * 排序（无符号）
     */
    sort: number;

    /**
     * 备注
     */
    remark: string;
}
