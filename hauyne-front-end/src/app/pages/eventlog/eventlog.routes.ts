import {Routes} from '@angular/router';
import {EventlogListComponent} from "./eventlog-list/eventlog-list.component";

/**
 * 审计日志路由
 */
export const EVENTLOG_ROUTES: Routes = [
    {path: 'eventlog', component: EventlogListComponent},
];
