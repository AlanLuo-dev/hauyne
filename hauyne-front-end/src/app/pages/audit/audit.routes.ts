import {Routes} from '@angular/router';
import {AuditListComponent} from "./audit-list/audit-list.component";

/**
 * 审计日志路由
 */
export const AUDIT_ROUTES: Routes = [
    {path: 'audit', component: AuditListComponent},
];
