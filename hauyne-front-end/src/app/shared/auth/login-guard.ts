import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {filter, Observable, of, switchMap, take} from 'rxjs';
import {selectIsAuthenticated} from "../../store/auth/auth.selector";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../store";
import {getTokenSuccess} from "../../store/auth/auth.action";
import {catchError} from "rxjs/operators";

/**
 * 登录页守卫
 *
 * 用于保护登录页，防止已登录用户直接访问登录页。
 * 如果用户未登录，允许访问登录页。
 * 如果用户已登录，重定向到首页。
 */
@Injectable({
    providedIn: 'root',
})
export class LoginGuard implements CanActivate {

    constructor(private store: Store<AppState>,
                private router: Router) {
    }

    canActivate(): Observable<boolean> {
        return this.store.pipe(
            select(selectIsAuthenticated),
            switchMap((authenticated) => {

                if (authenticated === true) {
                    // ✅ 已登录，禁止进入 login
                    this.router.navigate(['/']);
                    return of(false);

                } else if (authenticated === null) {
                    // 🔄 未检查状态 -> 触发 /me
                    this.store.dispatch(getTokenSuccess({redirect: false}));

                    return this.store.pipe(
                        select(selectIsAuthenticated),
                        filter(auth => auth !== null),
                        take(1),
                        switchMap(auth => {
                            if (auth) {
                                this.router.navigate(['/']);
                                return of(false);
                            }
                            return of(true);
                        })
                    );

                } else {
                    // ❌ 未登录，可以进入 login
                    return of(true);
                }
            }),
            catchError(() => {
                return of(true); // 出错也允许进 login
            })
        );
    }
}
