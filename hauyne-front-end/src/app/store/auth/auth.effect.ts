import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, exhaustMap, map} from 'rxjs/operators';
import {AuthService} from "../../login/auth.service";
import {authFailure, getMeSuccess, getTokenSuccess} from "./auth.action";
import {of, tap} from "rxjs";
import {Router} from "@angular/router";
import {AuthenticationStatus} from "../../common/authentication-status";

/**
 *  认证相关的Effect
 */
@Injectable()
export class AuthEffects {
    constructor(
        private actions$: Actions,
        private authService: AuthService,
        private router: Router
    ) {
    }

    /**
     * 登录成功后，获取用户信息
     */
    login$ = createEffect(() => {
        return this.actions$.pipe(
            ofType(getTokenSuccess),
            exhaustMap(({redirect}) => {
                return this.authService.me().pipe(
                    map((authStatus: AuthenticationStatus) => {
                        return getMeSuccess({authStatus: authStatus, redirect: redirect});
                    }),
                    catchError((errResp) => {
                        return of(authFailure());
                    })
                );
            })
        );
    });

    /**
     * 登录成功后，跳转到首页
     */
    loginRedirect$ = createEffect(
        () => {
            return this.actions$.pipe(
                ofType(...[getMeSuccess]),
                tap((action) => {
                    if (action.redirect) {
                        this.router.navigate(['/']);
                    }
                })
            );
        },
        {dispatch: false}
    );

}

