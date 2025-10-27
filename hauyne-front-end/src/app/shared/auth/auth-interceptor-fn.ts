import {
    HttpEvent,
    HttpHandlerFn,
    HttpInterceptorFn,
    HttpRequest, HttpResponse
} from '@angular/common/http';
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {finalize, Observable, share, tap, throwError} from "rxjs";
import {catchError, switchMap} from "rxjs/operators";
import {ProgressBarService} from "./progress-bar.service";
import {AuthService} from "../../login/auth.service";
import {NzMessageService} from "ng-zorro-antd/message";

let refresher: Observable<HttpResponse<any>> | null = null;

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {

    /* START 依赖注入 */
    const authService = inject(AuthService);
    const route = inject(Router);
    const progressBarService = inject(ProgressBarService);
    const messageService = inject(NzMessageService);
    /* END 依赖注入 */

    const handleAccessToken401Error = (request: HttpRequest<any>, error: any) => {
        if (request.url !== '/api/uaa/oauth/token') {
            if (refresher) {
                return waitRefresh(request);
            }
            refreshToken();
            return waitRefresh(request);
        } else {
            return next(request);
        }
    };

    const refreshToken = () => {
        refresher = authService.refreshCookie().pipe(
            share(),
            tap(() => {}),
            catchError((error) => {
                // localStorage.removeItem('user-profile');
                authService.clearUserProfile();
                route.navigate(['login']);
                return throwError(() => error);
            }),
            finalize(() => refresher = null)
        );
    };

    const waitRefresh = (request: HttpRequest<any>) => {
        return refresher!.pipe(
            switchMap(() => next(request))
        );
    };

    return next(req).pipe(
        tap(() => progressBarService.show()),
        finalize(() => progressBarService.hide()),
        catchError((error) => {
            if (error.status === 401) {
                return handleAccessToken401Error(req, error);
            }
            if (error.status === 504) {
                messageService.create('error', '网关超时 ' + error.statusText);
                // localStorage.removeItem('user-profile');
                authService.clearUserProfile();
                route.navigate(['login']);
                return throwError(() => error);
            } else if (error.status === 400) {
                return throwError(() => error);
            } else {
                if (error.error) {
                    if (error.error.errorTips) {
                        messageService.create('error', error.error.errorTips);
                    } else {
                        messageService.create('error', error.error.msg);
                    }
                }
                if (error.status === 503) {
                    messageService.create('error', '服务不可用'+ error.error.error);
                }
                return throwError(() => error);
            }
        })
    );
};
