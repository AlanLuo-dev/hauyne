

import { createFeatureSelector, createSelector } from '@ngrx/store';
import {AuthState} from "./auth.reducer";


export const selectAuthState = createFeatureSelector<AuthState>('auth');

// 从NgRx状态管理中读取「是否正在登录」状态
export const selectIsLoggingIn = createSelector(
    selectAuthState,
    (state: AuthState) => state.isLoggingIn
);

export const selectIsAuthenticated = createSelector(
    selectAuthState,
    (state: AuthState) => state.authenticated
);

// 从状态管理中读取导航菜单数据
export const selectMenus = createSelector(
    selectAuthState,
    (state: AuthState) => state.menus
);

// 从状态管理中读取权限数据
export const selectAuthorities = createSelector(
    selectAuthState,
    (state: AuthState) => {
        return state.authorities.map(authority => authority.authority);
    }
);

export const selectRealName = createSelector(
    selectAuthState,
    (state: AuthState) => state.realName
)
