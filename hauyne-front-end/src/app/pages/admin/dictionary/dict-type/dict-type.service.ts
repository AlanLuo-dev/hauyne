import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable, of} from "rxjs";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError} from "rxjs/operators";
import {AdminBaseService} from "../../admin-base.service";
import {Injectable} from "@angular/core";
import {DictTypeQuery} from "./dict-type-query";
import {PageResult} from "../../../../common/page-result";
import {DeletedDictType} from "./deleted-dict-type-list/deleted-dict-type-list.component";


@Injectable({
    providedIn: 'root'
})
export class DictTypeService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, "sys/dict-types")
    }

    /**
     * 校验字典类型编码是否已存在【新增、修改共用】
     * @param id 主键id （可选参数）
     */
    dictTypeCodeAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const dictTypeCode = control.value;
            return this.http.get<any>( `${this.apiUrl}/check-dict-type-code-unique?dictTypeCode=${dictTypeCode}`
                + (id ? `&excludeDictTypeId=${id}` : '')
            )
                .pipe(
                    map(data =>
                        data.availability ? null : {msg: `字典类型编码"${dictTypeCode}"已存在`}
                    ),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 检查字典类型名称唯一性（新增、修改共用）
     * @param id 主键id （可选参数）
     */
    dictTypeNameAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const dictTypeName = control.value;
            return this.http.get<any>( `${this.apiUrl}/check-dict-type-name-unique?dictTypeName=${dictTypeName}`
                + (id ? `&excludeDictTypeId=${id}` : '')
            )
                .pipe(
                    map(data =>
                        data.availability ? null : {msg: `字典类型名称"${dictTypeName}"已存在`}
                    ),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 加载数据字典 作为下拉框的数据来源
     * @param dictTypeCode
     */
    loadDropdownData(dictTypeCode: string): Observable<any> {
        return this.http.get(`${this.apiUrl}/${dictTypeCode}/dropdown`);
    }

    /**
     * 查询已删除的数据字典类型
     * @param query 查询条件
     */
    findDeletedDictTypes(query: DictTypeQuery): Observable<PageResult<DeletedDictType>> {

        // 过滤掉值为 null、空字符串或 undefined 的键值对
        let filterAndPageParams = Object.entries(query)
            .filter(([key, value]) => value)
            .reduce((newObj, [key, value]) => {
                newObj[key] = value;
                return newObj;
            }, {} as { [key: string]: any });
        let httpParams = new HttpParams().appendAll(filterAndPageParams);

        return this.http.get<PageResult<DeletedDictType>>(`${this.apiUrl}/deleted`, {params: httpParams});
    }
}
