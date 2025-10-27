import {createAction, props} from '@ngrx/store';
import {AuthenticationStatus} from "../../common/authentication-status";

export const LOGIN_START = '[auth page] login start';
export const GET_TOKEN_SUCCESS = '[auth page] get token Success';
export const GET_ME_SUCCESS = '[auth page] get me Success';
export const AUTH_FAILURE = '[auth page] Auth Failure';

export const loginStart = createAction(
    LOGIN_START
);

export const getTokenSuccess = createAction(
    GET_TOKEN_SUCCESS,
    props<{ redirect: boolean }>() // 传递 redirect 属性, 用于判断是否需要重定向
)

/**
 * 登录成功
 */
export const getMeSuccess = createAction(
    GET_ME_SUCCESS,
    props<{ authStatus: AuthenticationStatus; redirect: boolean }>()
);

/**
 * 认证失败
 */
export const authFailure = createAction(
    AUTH_FAILURE
);




