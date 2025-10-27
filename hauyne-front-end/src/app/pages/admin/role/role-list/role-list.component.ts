import {Component, OnInit, ViewChild} from '@angular/core';
import {NzTableComponent, NzTableModule, NzTableQueryParams} from 'ng-zorro-antd/table';
import {RoleService} from "../role.service";
import {NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PageQuery} from "../../../../common/page-query";
import {RoleEditFormComponent} from "../role-edit-form/role-edit-form.component";
import {NzModalService} from "ng-zorro-antd/modal";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {NzPopconfirmDirective} from "ng-zorro-antd/popconfirm";
import {Column} from "../../../../common/column";
import {AuditInfo} from "../../../../common/audit-info";
import {AuthorityDirective} from "../../../../directives/authority.directive";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../store";
import {finalize, Observable} from "rxjs";
import {AuditListComponent} from "../../../audit/audit-list/audit-list.component";
import {RoleConfigAuthorityComponent} from "../role-config-authority/role-config-authority.component";

export interface Role extends AuditInfo {
    id: number;
    roleCode: string;
    roleName: string;
}


interface SearchParam {
    roleCode: string;
    roleName: string;
}

class RoleQuery extends PageQuery {
    /**
     * 角色编码
     */
    roleCode: string;

    /**
     * 角色名称
     */
    roleName: string;

    constructor(queryParams: NzTableQueryParams, roleCode: string, roleName: string) {
        super(queryParams);
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}

@Component({
    selector: 'app-role-list',
    imports: [
        NzTableModule,
        NzFormDirective,
        NzRowDirective, NzColDirective,
        NzInputDirective, NzFormLabelComponent,
        NzButtonComponent, NzIconDirective, FormsModule, ReactiveFormsModule,
        RoleEditFormComponent, NzTooltipDirective, NzPopconfirmDirective, AuthorityDirective, AuditListComponent, RoleConfigAuthorityComponent
    ],
    templateUrl: './role-list.component.html',
    styleUrl: './role-list.component.less',
    providers: [NzModalService]
})
export class RoleListComponent implements OnInit {


    // 列定义
    cols: Column[] = [];

    // 数据结果
    listOfRole: Role[] = [];

    pageSize: number = 10;
    total = 1;

    loading = true;

    checked: boolean = false;
    indeterminate = false;
    setOfCheckedId: Set<number> = new Set<number>();

    roleCode: string = '';
    roleName: string = '';

    @ViewChild('basicTable', {static: false}) table!: NzTableComponent<any>;

    // 是否显示新增弹窗
    formDialogDisplay: boolean = false;
    formTitle: string = '';

    roleId?: number;

    roleIdToDelete!: number;

    // 是否显示 配置权限 弹窗
    configAuthorityFormDialogDisplay: boolean = false;

    // 是否显示 审计日志 弹窗
    auditDialogDisplay: boolean = false;

    typeRemarkMap: Map<string, string> = new Map<string, string>([
        ['hyn_sys_role', '角色日志'],
        ['hyn_sys_role_authority', '角色授权日志']
    ]);

    cancelButtonDisabled: boolean = false;

    constructor(private readonly roleService: RoleService,
                private readonly modal: NzModalService,
                private readonly messageService: NzMessageService) {

        this.cols = [
            {field: 'id', header: 'Id', isDataKey: true},
            {field: 'roleCode', header: '角色编码'},
            {field: 'roleName', header: '角色名称'},
            {field: 'createdByFullName', header: '创建人'},
            {field: 'createdTime', header: '创建时间', sortable: true},
            {field: 'lastModifiedByFullName', header: '修改人'},
            {field: 'lastUpdatedTime', header: '修改时间'},
            {field: 'operation', header: '操作'}
        ];
    }

    ngOnInit(): void {
    }

    search(): void {
        this.onQueryParamsChange(this.roleService.createLazyLoadMetaData(this.pageSize));
    }

    resetForm(): void {
        this.roleCode = '';
        this.roleName = '';
        this.search();
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        const roleQuery: RoleQuery = new RoleQuery(queryParams, this.roleCode, this.roleName);
        this.loading = true;
        this.roleService.loadPageData<Role, RoleQuery>(roleQuery)
            .subscribe({
                next: (value) => {
                    this.listOfRole = value.rows;
                    this.total = value.total;
                },
                error: (err) => {
                    this.messageService.create('error', err.error.errorTips);
                    this.loading = false;
                },
                complete: () => this.loading = false
            });
    }

    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.listOfRole.forEach(({id}) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.listOfRole.every(({id}) => this.setOfCheckedId.has(id));
        this.indeterminate = this.listOfRole.some(({id}) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    /**
     * 显示权限 新增、编辑表单弹窗
     * @param id 可选
     */
    showFormDialog(id?: number) {
        this.formDialogDisplay = true;
        this.formTitle = (id ? '编辑' : '创建') + '角色';
        this.roleId = id;
    }

    /**
     * 删除确认弹窗
     */
    showDeleteConfirm(): void {
        const modalRef = this.modal.confirm({
            nzTitle: '你确定要删除选中的角色吗？',
            nzContent: '<b style="color: red;">已关联用户的角色需要解除关联才能删除</b>',
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

                    this.roleService.deleteByIds(this.setOfCheckedId).subscribe({
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


    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable(observer => {
            this.roleService.deleteById(this.roleIdToDelete)
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
     * 显示权限配置 弹窗
     * @param id
     */
    showAssignAuthorityDialog(id: number) {
        this.configAuthorityFormDialogDisplay = true;
        this.roleId = id;
    }

    /**
     * 显示审计日志 弹窗
     * @param id
     */
    showAuditlog(id: number) {
        this.auditDialogDisplay = true;
        this.roleId = id;
    }
}
