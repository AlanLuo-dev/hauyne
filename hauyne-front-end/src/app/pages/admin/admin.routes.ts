import {Routes} from '@angular/router';
import {RoleListComponent} from "./role/role-list/role-list.component";
import {DictTypeListComponent} from "./dictionary/dict-type/dict-type-list/dict-type-list.component";
import {LoginHistoryComponent} from "./login-history/login-history.component";
import {AuthorityListComponent} from "./authority/authority-list/authority-list.component";
import {UserListComponent} from "./user/user-list/user-list.component";

export const ADMIN_ROUTES: Routes = [
    {path: 'role', component: RoleListComponent},
    {path: 'dictionary/dict-type', component: DictTypeListComponent},
    {path: 'login-history', component: LoginHistoryComponent},
    {path: 'authority', component: AuthorityListComponent},
    {path: 'user', component: UserListComponent}
];
