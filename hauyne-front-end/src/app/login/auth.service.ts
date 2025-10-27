import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, map, Observable} from 'rxjs';
import {AbstractControl, ValidatorFn} from "@angular/forms";
import {CurrentUserModel} from "./current-user.model";
import {HttpResult} from "../common/http-result";
import {AuthenticationStatus} from "../common/authentication-status";
import {ImgCode} from "./img-code";

/**
 * 认证 Service
 */
@Injectable({
    providedIn: 'root',
})
export class AuthService {


    userProfile: BehaviorSubject<CurrentUserModel> = new BehaviorSubject<CurrentUserModel>({
        username: '',
        menus: []
    });

    constructor(private http: HttpClient) {
    }

    imgCode(): Observable<ImgCode> {
        return this.http.get<ImgCode>('/api/uaa/captchas');
    }

    /**
     * 登录
     * @param param 账号、密码、验证码、授权模式
     * @param base64ClientInfo 客户端信息
     */
    login(param: HttpParams, base64ClientInfo: string) {
        return this.http.post<HttpResult<any>>('/api/uaa/oauth2/token', param, {
            headers: new HttpHeaders().set('Authorization', base64ClientInfo)
        });
    }

    /**
     * 获取当前已登录的用户
     */
    me(): Observable<AuthenticationStatus> {
        console.log("发生me请求");
        return this.http.get<AuthenticationStatus>('/api/uaa/me')
            .pipe(
                map((response) => response)
            );
    }

    /**
     * 刷新令牌
     */
    refreshCookie(): Observable<HttpResponse<any>> {
        const param = new HttpParams().set('grant_type', 'refresh_token') // 刷新令牌模式
        const base64ClientInfo = 'Basic ' + btoa('service-admin:123456');
        return this.http.post<HttpResponse<any>>('/api/uaa/oauth2/token', param, {
            headers: new HttpHeaders().set('Authorization', base64ClientInfo)
        });
    }

    /**
     * 注销
     */
    logout(): Observable<any> {
        const base64ClientInfo = 'Basic ' + btoa('service-admin:123456');
        return this.http.post<HttpResponse<any>>('/api/uaa/oauth2/revoke', null, {
            headers: new HttpHeaders().set('Authorization', base64ClientInfo)
        });
    }

    /**
     *清空 userProfile订阅对象
     */
    clearUserProfile() {
        this.userProfile.next({
            username: '',
            menus: []
        });
    }

    /**
     * 检查两次输入的密码是否一致
     * @param newPasswordControl
     * @param confirmPasswordControl
     */
    confirmPasswordCheck(newPasswordControl: AbstractControl, confirmPasswordControl: AbstractControl): ValidatorFn {
        return () => {
            if (newPasswordControl.value !== confirmPasswordControl.value && !confirmPasswordControl.errors) {
                console.log("两次输入的密码不一致");
                confirmPasswordControl.setErrors({match_error: '两次输入的密码不一致'});
            }

            return null;
        }
    }


    /**
     * 获取修改密码要用的RSA公钥
     */
    getPublickKey(business: string): Observable<any> {
        return this.http.post<any>(`/api/admin/public-key/${business}`, null);
    }

    /**
     * 修改密码
     * @param passwordFormData
     */
    modifyPassword(passwordFormData: any): Observable<any> {
        return this.http.patch<HttpResponse<any>>('/api/admin/sys/users/modify-password', passwordFormData);
    }
}
