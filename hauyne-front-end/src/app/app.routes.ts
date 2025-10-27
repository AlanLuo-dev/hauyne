import {Routes} from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./shared/auth/auth-guard";

export const routes: Routes = [

    {
        path: '',
        component: LayoutComponent,
        children: [
            {path: 'admin', loadChildren: () => import('./pages/admin/admin.routes').then(m => m.ADMIN_ROUTES)}
        ],
        // 路由守卫
        canActivate: [AuthGuard], // 检查认证
        canActivateChild: [AuthGuard] // 子路由（admin/xxx）也检查认证
    },
    {
        path: 'login',
        component: LoginComponent,
    }
];
