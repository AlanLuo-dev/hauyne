import {
    AfterViewInit,
    ChangeDetectionStrategy,
    Component,
    ElementRef,
    OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import { PageQuery } from "../../../../common/page-query";
import { NzTableModule, NzTableQueryParams } from "ng-zorro-antd/table";
import { NzModalService } from "ng-zorro-antd/modal";
import { NzMessageService } from "ng-zorro-antd/message";
import { UserService } from "../user.service";
import { FormsModule } from "@angular/forms";
import { NzButtonModule } from "ng-zorro-antd/button";
import { NzIconModule } from "ng-zorro-antd/icon";
import { NzInputModule } from "ng-zorro-antd/input";
import { RoleService } from "../../role/role.service";
import { NzSelectModule } from "ng-zorro-antd/select";
import { UserEditFormComponent } from "../user-edit-form/user-edit-form.component";
import { RoleDropdown } from "../../role/role-dropdown";
import { NzPopconfirmModule } from "ng-zorro-antd/popconfirm";
import { NzTooltipModule } from "ng-zorro-antd/tooltip";
import { finalize, Observable } from "rxjs";
import { ResetPasswordComponent } from "../reset-password/reset-password.component";
import { EnumOption } from "../../../../common/enum-option";

export interface User {
    id: number;
    username: string;
    roleName: string;
    phone: string;
    email: string;
    accountNonExpired: EnumOption<boolean>;
    accountNonLocked: EnumOption<boolean>;
    credentialsNonExpired: EnumOption<boolean>;
    enabled: EnumOption<boolean>;
    nickname: string;
    realName: string;
    gender: EnumOption<number> | null;
    avatar: string;
    position: string;
    remark: string;
    self: EnumOption<boolean>;
    builtin: EnumOption<boolean>;
    createdTime: string;
    lastUpdatedTime: string;
}

export class UserQuery extends PageQuery {
    username: string;
    roleCode: string;
    nickname: string;
    realName: string;
    gender: number | null = null;
    phone: string;
    enabled: boolean | null = null;

    constructor(
        queryParams: NzTableQueryParams,
        username: string,
        roleCode: string,
        nickname: string,
        realName: string,
        gender: number | null,
        phone: string,
        enabled: boolean | null
    ) {
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
        NzIconModule,
        NzInputModule,
        NzSelectModule,
        UserEditFormComponent,
        NzPopconfirmModule,
        NzTooltipModule,
        ResetPasswordComponent,
        NzTableModule
    ],
    templateUrl: './user-list.component.html',
    styleUrl: './user-list.component.less',
    changeDetection: ChangeDetectionStrategy.Eager,
    providers: [NzModalService]
})
export class UserListComponent implements OnInit, AfterViewInit, OnDestroy {
    // 数据结果
    listOfUser: User[] = [];

    pageIndex: number = 1;
    pageSize: number = 10;
    total: number = 0;
    loading: boolean = true;

    /* 过滤条件 */
    username: string = '';
    roleCode: string = '';
    nickname: string = '';
    realName: string = '';
    gender: number | null = null;
    phone: string = '';
    enabled: boolean | null = null;

    roles: RoleDropdown[] = [];

    /* 复选框处理相关 */
    checked: boolean = false;
    indeterminate: boolean = false;
    setOfCheckedId: Set<number> = new Set<number>();

    userFormDialogDisplay: boolean = false;
    formTitle: string = '';
    userId?: number;

    userIdToDelete!: number;
    cancelButtonDisabled: boolean = false;

    /* 重置密码弹窗开关 */
    resetPasswordDialogDisplay: boolean = false;
    usernameToResetPassword!: string;

    /* 动态表格高度控制 */
    @ViewChild('tableContainer') tableContainer!: ElementRef<HTMLElement>;
    tableScrollY: string = '400px';
    private resizeObserver?: ResizeObserver;

    constructor(
        private userService: UserService,
        private roleService: RoleService,
        private modal: NzModalService,
        private messageService: NzMessageService
    ) {}

    ngOnInit(): void {
        this.roleService.selectDropdown().subscribe((value) => {
            this.roles = value;
        });
    }

    ngAfterViewInit(): void {
        setTimeout(() => this.calculateTableScrollY(), 0);

        if (typeof ResizeObserver !== 'undefined') {
            this.resizeObserver = new ResizeObserver(() => {
                window.requestAnimationFrame(() => this.calculateTableScrollY());
            });
            this.resizeObserver.observe(this.tableContainer.nativeElement);
        }
    }

    ngOnDestroy(): void {
        this.resizeObserver?.disconnect();
    }

    /**
     * 动态计算表格内容区域可滚动的真实高度
     */
    private calculateTableScrollY(): void {
        if (!this.tableContainer) return;

        const containerHeight = this.tableContainer.nativeElement.clientHeight;
        if (containerHeight === 0) return;

        // 扣除表头(~39px)、全局工具栏(~45px)与底部分页(~48px)的总保留高度
        const calculatedHeight = containerHeight - 120;
        this.tableScrollY = `${Math.max(calculatedHeight, 100)}px`;
    }

    search(): void {
        if (this.pageIndex === 1) {
            this.loadData();
        } else {
            this.pageIndex = 1;
        }
    }

    resetForm(): void {
        const isAlreadyFirstPage = this.pageIndex === 1;

        this.username = '';
        this.roleCode = '';
        this.nickname = '';
        this.realName = '';
        this.gender = null;
        this.phone = '';
        this.enabled = null;

        if (isAlreadyFirstPage) {
            this.loadData();
        } else {
            this.pageIndex = 1;
        }
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this.pageIndex = queryParams.pageIndex;
        this.loadData(queryParams);
    }

    private loadData(queryParams?: NzTableQueryParams): void {
        const params = queryParams || this.userService.createLazyLoadMetaData(this.pageSize);
        const userQuery = new UserQuery(
            params,
            this.username,
            this.roleCode,
            this.nickname,
            this.realName,
            this.gender,
            this.phone,
            this.enabled
        );

        this.loading = true;
        this.userService.loadPageData<User, UserQuery>(userQuery).subscribe({
            next: (value) => {
                this.listOfUser = value.rows;
                this.total = value.total;
                this.loading = false;
            },
            error: (err) => {
                console.error('Error:', err);
                this.loading = false;
            },
            complete: () => (this.loading = false)
        });
    }

    /* 复选框逻辑 */
    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.listOfUser.forEach(({ id }) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.listOfUser.every(({ id }) => this.setOfCheckedId.has(id));
        this.indeterminate = this.listOfUser.some(({ id }) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    /* 弹窗逻辑 */
    showUserFormDialog(userId?: number): void {
        this.userFormDialogDisplay = true;
        this.formTitle = (userId ? '编辑' : '创建') + '用户';
        this.userId = userId;
    }

    resetPasswordDialog(userId: number, username: string): void {
        this.resetPasswordDialogDisplay = true;
        this.userId = userId;
        this.usernameToResetPassword = username;
    }

    /* 删除确认与执行 */
    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable((observer) => {
            this.userService
                .deleteById(this.userIdToDelete)
                .pipe(finalize(() => observer.complete()))
                .subscribe({
                    next: () => {
                        observer.next(true);
                        this.setOfCheckedId.clear();
                        this.refreshCheckedStatus();
                        this.messageService.create('success', '操作成功');
                        this.search();
                        this.cancelButtonDisabled = false;
                    },
                    error: (err) => {
                        observer.next(false);
                        this.messageService.create('error', err.error?.errorTips || '删除失败');
                        this.cancelButtonDisabled = false;
                    }
                });
        });
    };

    showDeleteConfirm(): void {
        const modalRef = this.modal.confirm({
            nzTitle: '你确定要删除选中的用户吗？',
            nzOkText: '确定',
            nzOkType: 'primary',
            nzOkDanger: true,
            nzCancelText: '取消',
            nzOnOk: () =>
                new Promise<void>((resolve, reject) => {
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
                            resolve();
                        },
                        error: (err) => {
                            this.messageService.create('error', err.error?.errorTips || '删除失败');
                            modalRef.updateConfig({
                                nzCancelDisabled: false,
                                nzClosable: true
                            });
                            reject();
                        }
                    });
                })
        });
    }
}
