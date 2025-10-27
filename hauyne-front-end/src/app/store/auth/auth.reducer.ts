// 状态名称
import {createReducer, on} from "@ngrx/store";
import {authFailure, getMeSuccess, loginStart} from "./auth.action";
import {SystemMenu} from "../../layout/system-menu";

export const authFeatureKey = "auth";

export interface Authority {
    authority: string;
}

// 认证状态类型接口
export interface AuthState {
    authenticated: boolean | null; // 是否已认证
    menus: SystemMenu[]; // 左侧导航菜单
    authorities: Authority[]; // 权限集合
    realName: string; // 真实姓名
    isLoggingIn: boolean;
}


// 初始状态
export const initState: AuthState = {
    authenticated: null,  // 认证状态，默认为 null，表示还未检查
    menus: [], // 左侧导航菜单默认为空数组
    authorities: [], // 权限集合
    realName: '', // 真实姓名
    isLoggingIn: false,  // 认证请求的加载状态，默认为 false
};

// START reducer函数
export const authReducer = createReducer(
    initState,

    on(loginStart, (state) => ({
        ...state,
        isLoggingIn: true
    })),

    // 认证请求成功时
    on(getMeSuccess, (state, action) => ({
        ...state,
        authenticated: action.authStatus.authenticated,
        menus: action.authStatus.principal.menus,
        authorities: action.authStatus.principal.authorities,
        realName: action.authStatus.principal.realName,
        isLoggingIn: false,
        redirect: action.redirect,
    })),

    // 认证请求失败时
    on(authFailure, (state) => ({
        ...state,
        authenticated: false,
        menus: [],
        authorities: [],
        realName: '',
        isLoggingIn: false,
        redirect: false
    }))
);

// END reducer函数
