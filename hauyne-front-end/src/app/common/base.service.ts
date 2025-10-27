import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {PageQuery} from "./page-query";
import {PageResult} from "./page-result";
import {NzTableQueryParams} from "ng-zorro-antd/table";

export class BaseService<ID> {

    /**
     * 后端 API URL
     *
     * 风格为：/api/具体微服务端点/模块/具体业务名的复数，如/api/admin/sys/users
     *
     * @private
     */
    protected readonly apiUrl: string;

    /* ----------------------------------------------------------------------------------------------------- */

    /**
     * 构造函数
     * @param http HttpClient对象
     * @param endpoint 具体业务的API
     */
    constructor(protected http: HttpClient,
                private endpoint: string) {

        // 根据具体情况设置 API URL
        this.apiUrl = `/api/${endpoint}`;
    }

    /**
     * 创建一个空的分页条件（For NG-ZORRO Table表格组件）
     * @param pageSize
     */
    createLazyLoadMetaData(pageSize: number): NzTableQueryParams {
        return {
            pageIndex: 1,
            pageSize: pageSize,
            sort: [],
            filter: []
        };
    }

    /**
     * 查询所有
     */
    findAll<U>(): Observable<U[]> {
        return this.http.get<U[]>(this.apiUrl);
    }

    /**
     * 分页查询
     * @param {U} resultType - 结果类型参数
     * @param {Q} query - 查询参数，继承自 PageQuery 类型
     * @param query 过滤、分页条件
     * @returns {Observable<PageResult<{U}[]>>} - 返回包含分页查询结果的 Observable
     */
    loadPageData<U, Q extends PageQuery>(query: Q): Observable<PageResult<U>> {

        // 过滤掉值为 null、空字符串或 undefined 的键值对
        let filterAndPageParams = Object.entries(query)
            .filter(([key, value]) => value )
            .reduce((newObj, [key, value]) => {
                newObj[key] = value;
                return newObj;
            }, {} as { [key: string]: any });
        let httpParams = new HttpParams().appendAll(filterAndPageParams);

        return this.http.get<PageResult<U>>(this.apiUrl, {params: httpParams});
    }

    /**
     * Ng-Zorro 分页方法
     * @param queryParams
     */
    // loadPage<U>(queryParams: NzTableQueryParams): Observable<PageResult<U>> {
    //     let httpParams = new HttpParams()
    //         .append('pageIndex', `${queryParams.pageIndex}`)
    //         .append('pageSize', `${queryParams.pageSize}`);
    //
    //     const currentSort: { key: string, value: NzTableSortOrder } | undefined = queryParams
    //         .sort
    //         .find(
    //             (item: { key: string, value: NzTableSortOrder }) => item.value !== null
    //         );
    //     const sortField: string | null = (currentSort && currentSort.key) || null;
    //     if (sortField) {
    //         httpParams = httpParams.append('sortField', sortField);
    //     }
    //     const sortOrder: string | null = (currentSort && currentSort.value) || null;
    //     if (sortOrder) {
    //         httpParams = httpParams.append('sortOrder', sortOrder);
    //     }
    //     queryParams.filter.forEach((item:{ key: string; value: any[] | any; })  => {
    //         const key = item.key;
    //         const value = item.value;
    //         if (key && value) {
    //             if (Array.isArray(value)) {
    //                 value.forEach((element: NzSafeAny) => {
    //                     httpParams = httpParams.append(key, element);
    //                 });
    //             } else {
    //                 httpParams = httpParams.append(key, value);
    //             }
    //         }
    //     });
    //
    //     return this.http.get<PageResult<U>>(this.apiUrl, {params: httpParams});
    // }

    /**
     * 查询单个
     * @param id ID
     *
     */
    getOne<U>(id: ID): Observable<U> {
        return this.http.get<U>(`${this.apiUrl}/${id}`);
    }

    /**
     * 新增
     * @param item
     */
    save<U>(item: U): Observable<U> {
        return this.http.post<U>(this.apiUrl, item);
    }

    /**
     * 更新
     * @param item
     */
    update<U>(item: U): Observable<void> {
        return this.http.put<void>(this.apiUrl, item);
    }

    /**
     * 删除项
     * @param id ID
     */
    deleteById(id: ID): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    /**
     * 批量删除
     * @param ids
     */
    deleteByIds(ids: ID | ID [] | Set<ID>): Observable<void> {
        if (ids instanceof Set) {
            ids = Array.from(ids);
        }
        return this.http.delete<void>(`${this.apiUrl}/${ids}`);
    }

    /**
     * 启用
     * @param id
     */
    enabled(id: ID): Observable<void> {
        return this.http.patch<void>(`${this.apiUrl}/${id}/enabled`, null);
    }

    /**
     * 禁用
     * @param id
     */
    disabled(id: ID): Observable<void> {
        return this.http.patch<void>(`${this.apiUrl}/${id}/disabled`, null);
    }
}
