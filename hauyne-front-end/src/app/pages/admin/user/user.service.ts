import {Injectable} from '@angular/core';
import {AdminBaseService} from "../admin-base.service";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";

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
}
