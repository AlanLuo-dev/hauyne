import {ApplicationConfig, importProvidersFrom, isDevMode} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideNzI18n, zh_CN} from 'ng-zorro-antd/i18n';
import {registerLocaleData} from '@angular/common';
import zh from '@angular/common/locales/zh';
import {FormsModule} from '@angular/forms';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {authInterceptor} from "./shared/auth/auth-interceptor-fn";

registerLocaleData(zh);

import { IconDefinition } from '@ant-design/icons-angular';

// 引入全部的图标，不推荐 ❌
import * as AllIcons from '@ant-design/icons-angular/icons';
import {provideNzIcons} from "ng-zorro-antd/icon";
import {provideEffects} from '@ngrx/effects';
import {provideRouterStore} from '@ngrx/router-store';
import {provideStoreDevtools} from '@ngrx/store-devtools';
import {provideStore} from "@ngrx/store";
import {AuthEffects} from "./store/auth/auth.effect";
import {authReducer} from "./store/auth/auth.reducer";
import {reducer} from "./store/counter/counter.reducer";

const antDesignIcons = AllIcons as {
    [key: string]: IconDefinition;
};
const icons: IconDefinition[] = Object.keys(antDesignIcons).map(key => antDesignIcons[key])

export const appConfig: ApplicationConfig = {
    providers: [
        // 路由配置 总入口
        provideRouter(routes),
        provideAnimationsAsync(),
        provideNzI18n(zh_CN),
        importProvidersFrom(FormsModule),
        provideAnimationsAsync(),
        // 拦截器配置 总入口
        provideHttpClient(withInterceptors([authInterceptor])),
        provideNzIcons(icons),
        // 状态管理
        provideEffects(AuthEffects),
        provideRouterStore(),
        provideStore({
            counter: reducer,
            auth: authReducer
        }),
        provideStoreDevtools({maxAge: 25, logOnly: !isDevMode()})
    ]
};
