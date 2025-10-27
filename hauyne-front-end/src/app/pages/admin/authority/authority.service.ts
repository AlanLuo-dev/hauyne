import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthorityQuery} from "./AuthorityQuery";
import {map, Observable, of} from "rxjs";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError} from "rxjs/operators";
import {AdminBaseService} from "../admin-base.service";
import {Injectable} from "@angular/core";
import {Authority} from "./authority-list/authority-list.component";

@Injectable({
    providedIn: 'root'
})
export class AuthorityService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, 'sys/authorities');
    }

    /**
     * 加载权限信息列表【树形结构】
     * @param query
     */
    list(query: AuthorityQuery): Observable<Authority[]> {
        let httpParams = new HttpParams();
        Object.keys(query).forEach(function (key) {
            // @ts-ignore
            if (null !== query[key] && undefined !== query[key] && '' !== query[key]) {
                // @ts-ignore
                httpParams = httpParams.append(key, query[key]);
            }
        });

        return this.http.get<Authority[]>(this.apiUrl, {params: httpParams});
    }

    /**
     * 权限编码异步校验【新增、编辑共用】
     * @param id 为可选参数。新增不传，编辑要传
     */
    authorityCodeAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const authorityCode = control.value;
            return this.http
                .get<any>(`${this.apiUrl}/check-authority-code-unique?authorityCode=${authorityCode}${id ? `&excludeAuthorityId=${id}` : ''}`)
                .pipe(
                    map(data =>
                        data.availability === false
                            ? {msg: `权限编码 ${authorityCode} 已存在，请勿重复添加！`}
                            : null
                    ),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 权限名称异步校验【新增、编辑共用】
     * @param id 为可选参数。新增不传，编辑要传
     */
    authorityNameAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const authorityName = control.value;
            return this.http
                .get<any>(`${this.apiUrl}/check-authority-name-unique?authorityName=${authorityName}${id ? `&excludeAuthorityId=${id}` : ''}`)
                .pipe(
                map(data => (data.availability === false ? {msg: `权限名称 ${authorityName} 已存在，请勿重复添加！`} : null)),
                catchError(() => of(null))
            );
        }
    }

    loadAuthorityTree(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/tree-select`);
    }

    /**
     * 加载权限资源, 以复选框形式的树结构展示
     */
    loadAuthorityCheckBoxTree(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/checkbox-tree`);
    }
}
