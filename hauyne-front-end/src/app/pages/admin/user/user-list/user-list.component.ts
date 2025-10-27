import {Component} from '@angular/core';
import {PageQuery} from "../../../../common/page-query";
import {NzTableModule, NzTableQueryParams} from "ng-zorro-antd/table";
import {Column} from "../../../../common/column";
import {NzModalService} from "ng-zorro-antd/modal";
import {NzMessageService} from "ng-zorro-antd/message";
import {UserService} from "../user.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzInputModule} from "ng-zorro-antd/input";
import {RoleService} from "../../role/role.service";
import {NzSelectModule} from "ng-zorro-antd/select";
import {UserEditFormComponent} from "../user-edit-form/user-edit-form.component";
import {RoleDropdown} from "../../role/role-dropdown";
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzTooltipModule} from "ng-zorro-antd/tooltip";
import {finalize, Observable} from "rxjs";
import {ResetPasswordComponent} from "../reset-password/reset-password.component";
import {NzFormLabelComponent} from "ng-zorro-antd/form";

/**
 * 查询结果
 */
export interface User {
    /** 主键id */
    id: number;

    /** 用户名 */
    username: string;

    /** 角色名 */
    roleName: string;

    /** 手机号 */
    phone: string;

    /** 电子邮箱 */
    email: string;

    /** 帐户是否过期 文本值 */
    accountNonExpired: string;

    /** 帐户是否锁定 文本值 */
    accountNonLocked: string;


    /** 密码是否过期 文本值 */
    credentialsNonExpired: string;

    /** 是否已启用（原始值，true=已启用；false=已禁用） */
    enabledValue: boolean;

    /** 是否已启用 文本值 */
    enabled: string;

    /** 昵称 */
    nickname: string;

    /** 用户真实姓名 */
    realName: string;

    /** 性别 */
    gender: string;

    /** 头像 */
    avatar: string;

    /** 职位 */
    position: string;

    /** 备注 */
    remark: string;

    createdTime: string;
    lastUpdatedTime: string;
}

/**
 * 查询条件
 */
export class UserQuery extends PageQuery {
    /** 用户名 */
    username: string;

    /** 角色编码 */
    roleCode: string;

    /** 昵称 */
    nickname: string;

    /** 真实姓名 */
    realName: string;

    /** 性别(1=男 0=女) */
    gender: number | null = null;

    /** 手机号 */
    phone: string;

    /** 是否可用（true=是；false=否） */
    enabled: boolean | null = null;


    constructor(queryParams: NzTableQueryParams, username: string, roleCode: string, nickname: string,
                realName: string, gender: number | null, phone: string, enabled: boolean | null) {
        super(queryParams);
        this.username = username;
        this.roleCode = roleCode;
        this.nickname = nickname;
        this.realName = realName;
        this.gender = gender;
        this.phone = phone;
        this.enabled = enabled;
    }
}

@Component({
    selector: 'app-user-list',
    imports: [
        FormsModule,
        NzButtonModule,
        NzGridModule,
        NzIconModule,
        NzInputModule,
        ReactiveFormsModule,
        NzSelectModule,
        UserEditFormComponent,
        NzPopconfirmModule,
        NzTooltipModule,
        ResetPasswordComponent,
        NzTableModule,
        NzFormLabelComponent
    ],
    templateUrl: './user-list.component.html',
    styleUrl: './user-list.component.less'
})
export class UserListComponent {

    // 列定义
    cols: Column[] = [];

    // 数据结果
    listOfUser: User[] = [];

    pageSize: number = 10;
    total = 1;

    loading = true;


    /** start 过滤条件 ******************/
    /** 用户名 */
    username: string = '';

    /** 角色编码 */
    roleCode: string = '';

    /** 昵称 */
    nickname: string = '';

    /** 真实姓名 */
    realName: string = '';

    /** 性别(1=男 0=女) */
    gender: number | null = null;

    /** 手机号 */
    phone: string = '';

    /** 是否可用（true=是；false=否） */
    enabled: boolean | null = null;

    /** end 过滤条件 ******************/


    roles: RoleDropdown[] = [];

    /* START 复选框处理相关字段 */
    checked: boolean = false;
    indeterminate = false;
    setOfCheckedId: Set<number> = new Set<number>();
    /* END 复选框处理相关字段 */

    userFormDialogDisplay: boolean = false;
    formTitle: string = '';
    userId?: number;

    userIdToDelete!: number;
    cancelButtonDisabled: boolean = false;

    /**
     * 重置密码弹窗开关
     */
    resetPasswordDialogDisplay: boolean = false;
    usernameToResetPassword!: string;

    constructor(private userService: UserService,
                private roleService: RoleService,
                private modal: NzModalService,
                private messageService: NzMessageService) {

        // 初始化列定义
        this.cols = [
            {field: 'id', header: 'Id', isDataKey: true},
            {field: 'avatar', header: '头像', width: '50px'},
            {field: 'username', header: '用户名', width: '100px'},
            {field: 'realName', header: '真实姓名', width: '100px'},
            {field: 'nickname', header: '昵称', width: '100px'},
            {field: 'roleName', header: '角色', width: '100px'},
            {field: 'phone', header: '手机', width: '100px'},
            {field: 'email', header: 'E-mail', width: '100px'},
            {field: 'accountNonExpired', header: '账号过期状态', width: '100px'},
            {field: 'accountNonLocked', header: '账号锁定状态', width: '100px'},
            {field: 'credentialsNonExpired', header: '密码过期状态', width: '100px'},
            {field: 'enabled', header: '启用状态', width: '100px'},
            {field: 'gender', header: '性别', width: '100px'},
            {field: 'position', header: '职位', width: '100px'},
            {field: 'remark', header: '备注', width: '200px'},
            {field: 'createdTime', header: '创建时间', sortable: true, width: '180px'},
            {field: 'lastUpdatedTime', header: '修改时间', sortable: true, width: '180px'},
            {field: 'operation', header: '操作', width: '100px'}
        ];

        this.roleService.selectDropdown().subscribe(
            (value) => {
                this.roles = value;
            }
        )
    }

    search(): void {
        this.onQueryParamsChange(this.userService.createLazyLoadMetaData(this.pageSize));
    }

    resetForm(): void {
        this.username = '';
        this.roleCode = '';
        this.nickname = '';
        this.realName = '';
        this.gender = null;
        this.phone = '';
        this.enabled = null;
        this.search();
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        const userQuery: UserQuery = new UserQuery(queryParams, this.username, this.roleCode, this.nickname,
            this.realName, this.gender, this.phone, this.enabled
        );
        this.loading = true;
        this.userService.loadPageData<User, UserQuery>(userQuery).subscribe({
            next: (value) => {
                this.listOfUser = value.rows;
                this.total = value.total;
            },
            error: (err) => {
                console.error('Error:', err);
                this.loading = false;
            },
            complete: () => this.loading = false
        });
    }

    /* START 复选框处理逻辑 */
    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.listOfUser.forEach(({id}) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.listOfUser.every(({id}) => this.setOfCheckedId.has(id));
        this.indeterminate = this.listOfUser.some(({id}) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    /* END 复选框处理逻辑 */

    /**
     * 显示用户表单弹窗
     * @param userId 要编辑的用户Id
     */
    showUserFormDialog(userId?: number) {
        this.userFormDialogDisplay = true;
        this.formTitle = (userId ? '编辑' : '创建') + '用户';
        this.userId = userId;
    }

    resetPasswordDialog(userId: number, username: string): void {
        this.resetPasswordDialogDisplay = true;
        this.userId = userId;
        this.usernameToResetPassword = username;
    }

    /**
     * 删除单个用户
     */
    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable(observer => {
            this.userService.deleteById(this.userIdToDelete)
                .pipe(
                    finalize(() => {
                        observer.complete(); // 确保在流结束时完成订阅
                    })
                )
                .subscribe(
                    {
                        next: x => {
                            observer.next(true);
                            this.setOfCheckedId.clear();
                            this.refreshCheckedStatus();
                            this.messageService.create('success', '操作成功');
                            this.search();
                            this.cancelButtonDisabled = false;
                        },
                        error: err => {
                            observer.next(false);
                            this.messageService.create('error', err.error?.errorTips);
                            this.cancelButtonDisabled = false;
                        }
                    }
                )
        });
    }

    /**
     * 批量删除用户 确认弹窗
     */
    showDeleteConfirm(): void {
        const modalRef = this.modal.confirm({
            nzTitle: '你确定要删除选中的用户吗？',
            nzOkText: '确定',
            nzOkType: 'primary',
            nzOkDanger: true,
            nzCancelText: '取消',
            nzOnOk: () =>
                new Promise<void>((resolve, reject) => {
                    // 禁用取消按钮
                    modalRef.updateConfig({
                        nzCancelDisabled: true,
                        nzClosable: false
                    });

                    this.userService.deleteByIds(this.setOfCheckedId).subscribe({
                        next: () => {
                            this.setOfCheckedId.clear();
                            this.refreshCheckedStatus();
                            this.messageService.create('success', '操作成功');
                            this.search();
                            resolve(); // 关闭弹窗
                        },
                        error: (err) => {
                            this.messageService.create('error', err.error?.errorTips || '删除失败');
                            modalRef.updateConfig({
                                nzCancelDisabled: false,
                                nzClosable: true
                            }); // 恢复取消按钮
                            reject(); // 阻止关闭弹窗
                        }
                    });
                })
        });
    }

}
