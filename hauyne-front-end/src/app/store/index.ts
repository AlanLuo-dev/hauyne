import {isDevMode} from '@angular/core';
import {ActionReducerMap, MetaReducer} from '@ngrx/store';
import * as fromCounter from './counter/counter.reducer';
import * as authReducer from './auth/auth.reducer';

// store中存储的状态类型接口
export interface AppState {

    [fromCounter.counterFeatureKey]: fromCounter.State;
    [authReducer.authFeatureKey]: authReducer.AuthState
}

// 状态名字和reducer的对应关系
export const reducers: ActionReducerMap<AppState> = {
    [fromCounter.counterFeatureKey]: fromCounter.reducer,
    [authReducer.authFeatureKey]: authReducer.authReducer
};


export const metaReducers: MetaReducer<AppState>[] = isDevMode() ? [] : [];
