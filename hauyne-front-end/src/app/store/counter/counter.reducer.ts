import {createReducer, on} from '@ngrx/store';
import {decrement, increment} from './counter.actions';

// 状态名称
export const counterFeatureKey = 'counter';

// 状态类型接口
export interface State {
    count: number;
}

// 初始状态
export const initialState: State = {
    count: 0
};

// 创建reducer函数
export const reducer = createReducer(
    initialState,

    // 加1
    on(increment, state => ({
        ...state,
        count: state.count + 1
    })),

    // 减1
    on(decrement, state => ({
        ...state,
        count: state.count - 1
    }))
);

