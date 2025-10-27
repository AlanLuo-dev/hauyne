import {HttpClient, HttpParams} from "@angular/common/http";
import {BaseService} from "../../common/base.service";
import {Injectable} from "@angular/core";
import {AuditChangeInfo, AuditQuery} from "./audit-list/audit-list.component";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuditService extends BaseService<number> {

    /**
     * 构造函数
     * @param http HttpClient对象
     * @param endpoint 具体业务的API
     */
    constructor(http: HttpClient) {
        super(http, `audit`);
    }


    groupChangesByCommit(query: AuditQuery): Observable<AuditChangeInfo[]> {
        // 过滤掉值为 null、空字符串或 undefined 的键值对
        let filterAndPageParams = Object.entries(query)
            .filter(([key, value]) => value)
            .reduce((newObj, [key, value]) => {
                newObj[key] = value;
                return newObj;
            }, {} as { [key: string]: any });
        let httpParams = new HttpParams().appendAll(filterAndPageParams);

        return this.http.get<AuditChangeInfo[]>(`${this.apiUrl}/grouped-changes`, {params: httpParams});
    }
}
