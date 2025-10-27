import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable, of} from "rxjs";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError} from "rxjs/operators";
import {AdminBaseService} from "../../admin-base.service";
import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class DictItemService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, "sys/dict-items")
    }

    list(query: any): Observable<any[]> {
        let httpParams = new HttpParams();
        Object.keys(query).forEach(function (key) {
            if (null !== query[key] && undefined !== query[key] && '' !== query[key]) {
                httpParams = httpParams.append(key, query[key]);
            }
        });

        return this.http.get<any[]>(this.apiUrl, {params: httpParams});
    }

    /**
     * 改变排序
     * @param dictTypeId 字典类型id
     * @param dictItemIds 字典值id数组
     */
    reorder(dictTypeId: number, dictItemIds: number[]): Observable<any> {
        return this.http.patch<any>( `${this.apiUrl}/${dictTypeId}/reorder`, dictItemIds);
    }

    /**
     * 校验字典选项编码是否已存在【新增、修改共用】
     * @param dictTypeId 字典类型id（必填参数）
     * @param dictItemId 字典选项id（可选参数）
     */
    dictItemCodeAsyncValidator(dictTypeId: number, dictItemId?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const dictItemCode = control.value;
            return this.http.get<any>(`${this.apiUrl}/check-dict-item-code-unique?dictTypeId=${dictTypeId}&dictItemCode=${dictItemCode}`
                + (dictItemId ? `&excludeDictItemId=${dictItemId}` : '')
            )
                .pipe(
                    map(data =>
                        data.availability ? null : {msg: `字典选项编码"${dictItemCode}"已存在`}
                    ),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 校验字典选项名称是否已存在【新增、修改共用】
     * @param dictTypeId 字典类型id（必填参数）
     * @param dictItemId 字典选项id（可选参数）
     */
    dictItemNameAsyncValidator(dictTypeId: number, dictItemId?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const dictItemName = control.value;
            return this.http.get<any>( `${this.apiUrl}/check-dict-item-name-unique?dictTypeId=${dictTypeId}&dictItemName=${dictItemName}`
                + (dictItemId ? `&excludeDictItemId=${dictItemId}` : '')
            )
                .pipe(
                    map(data =>
                        data.availability ? null : {msg: `字典值名称"${dictItemName}"已存在`}
                    ),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 查询字典类型下的字典选项数量
     * @param dictTypeId 字典类型id
     */
    findDictItemCountByDictTypeId(dictTypeId: number): Observable<any> {
        return this.http.get(`${this.apiUrl}/${dictTypeId}/dict-item-count`);
    }

}
