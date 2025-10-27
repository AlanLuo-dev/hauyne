import {Injectable} from '@angular/core';
import {CanActivate, CanActivateChild, Router} from '@angular/router';
import {filter, Observable, of, switchMap, take} from 'rxjs';
import {selectIsAuthenticated} from "../../store/auth/auth.selector";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../store";
import {getTokenSuccess} from "../../store/auth/auth.action";
import {catchError} from "rxjs/operators";

/**
 * 路由守卫
 */
@Injectable({
    providedIn: 'root',  // 这行代码确保 AuthGuard 在应用程序级别被提供
})
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(private store: Store<AppState>,
                private router: Router) {
    }

    canActivate(): Observable<boolean> {
        return this.checkAuth();
    }

    canActivateChild(): Observable<boolean> {
        return this.checkAuth();
    }

    private checkAuth(): Observable<boolean> {
        return this.store.pipe(
            select(selectIsAuthenticated),
            switchMap((authenticated) => {
                if (authenticated === true) {
                    console.log("已认证，允许导航");
                    return of(true);
                } else if (authenticated === null) {
                    console.log("向服务器发起/me请求 检查认证状态");
                    // 未检查认证状态，发起 /me 请求
                    this.store.dispatch(getTokenSuccess({ redirect: false }));
                    return this.store.pipe(
                        select(selectIsAuthenticated),
                        filter((auth) => auth !== null), // 等待状态变更为 true 或 false
                        take(1), // 只取一次状态更新
                        switchMap((auth) => {
                            console.log("/me已完成 " + auth);
                            if (auth) {
                                return of(true); // 认证成功，允许导航
                            } else {
                                this.router.navigate(['/login']); // 认证失败，跳转登录页
                                return of(false);
                            }
                        })
                    );
                } else {
                    // 认证失败，直接跳转登录页
                    this.router.navigate(['/login']);
                    return of(false);
                }
            }),
            catchError(() => {
                // 捕获任何意外错误，跳转登录页
                this.router.navigate(['/login']);
                return of(false);
            })
        );
    }
}
