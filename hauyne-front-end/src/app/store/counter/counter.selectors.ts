import {createFeatureSelector, createSelector} from '@ngrx/store';
import {counterFeatureKey, State} from './counter.reducer';
import {AppState} from '../index';

export const selectCounter = createFeatureSelector<State>(counterFeatureKey)
export const selectCount = createSelector(selectCounter, state => state.count)
