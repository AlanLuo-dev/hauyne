import {Injectable} from '@angular/core';
import {AdminBaseService} from "../admin-base.service";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map, Observable, of} from "rxjs";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class UserService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, "sys/users");
    }

    /**
     * 重置密码
     * @param passwordFormData
     */
    resetPassword(passwordFormData: any): Observable<any> {
        return this.http.put<HttpResponse<any>>('/api/admin/sys/users/reset-password', passwordFormData);
    }

    /**
     * 校验字典类型编码是否已存在【新增、修改共用】
     * @param id 主键id （可选参数）
     */
    usernameAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const username = control.value;
            return this.http.get<any>( `${this.apiUrl}/check-username-unique?username=${username}`
                + (id ? `&excludeUserId=${id}` : '')
            )
                .pipe(
                    map(data =>
                        data.availability ? null : {msg: `用户名 ${username} 已存在`}
                    ),
                    catchError(() => of(null))
                );
        }
    }
}
