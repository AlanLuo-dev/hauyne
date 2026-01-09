import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import {NzButtonComponent, NzButtonModule} from "ng-zorro-antd/button";
import {NzTooltipDirective, NzTooltipModule} from "ng-zorro-antd/tooltip";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzFormatEmitEvent, NzTreeComponent, NzTreeNode, NzTreeNodeOptions} from "ng-zorro-antd/tree";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzModalRef, NzModalService} from "ng-zorro-antd/modal";
import {RoleService} from "../role.service";
import {AuthorityService} from "../../authority/authority.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {Role} from "../role-list/role-list.component";
import {forkJoin} from "rxjs";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzSpinModule} from "ng-zorro-antd/spin";

@Component({
    selector: 'app-role-config-authority',
    imports: [
        NzDrawerModule,
        NzButtonModule,
        NzTooltipModule,

        ReactiveFormsModule,
        NzTreeComponent,
        NzIconDirective,
        NzColDirective,
        NzRowDirective,
        NzButtonComponent,
        NzTooltipDirective,
        NzInputGroupComponent,
        NzInputDirective,
        FormsModule,
        NzSpinModule,
    ],
    templateUrl: './role-config-authority.component.html',
    styleUrl: './role-config-authority.component.less'
})
export class RoleConfigAuthorityComponent implements OnInit {

    // 是否显示授权弹窗
    @Input() configAuthorityFormDialogDisplay: boolean = false;
    @Output() configAuthorityFormDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() roleId!: number;


    /**
     * 子传父，触发角色列表刷新
     */
    @Output() triggerRoleListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();


    isOkLoading: boolean = false;
    isCancelDisabled: boolean = false;


    @ViewChild("nzTreeComponent", {static: false}) nzTreeComponent!: NzTreeComponent;

    treeReady = false;

    // 默认选中的节点key 数组
    defaultCheckedKeys: string[] = [];

    nodes: NzTreeNodeOptions[] = [];

    roleName: string = '';

    confirmModal?: NzModalRef; // For testing by now

    isOkDanger: boolean = false;

    searchValue: string = '';

    // 是否加载中 true=是，false=否
    isLoading: boolean = true;

    constructor(private readonly roleService: RoleService,
                private readonly authorityService: AuthorityService,
                private readonly messageService: NzMessageService,
                private readonly modal: NzModalService,
                private readonly cdr: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        this.roleService.getOne<Role>(this.roleId).subscribe(data => {
            this.roleName = data.roleName;
        });

        forkJoin({
            nodes: this.authorityService.loadAuthorityCheckBoxTree(),
            checkedKeys: this.roleService.authorityLeafNodeKeys(this.roleId)
        }).subscribe({
            next: ({nodes, checkedKeys}) => {
                this.nodes = nodes;        // 1. 设置树数据
                this.treeReady = true;     // 2. 触发 @if 渲染 <nz-tree>
                this.cdr.detectChanges();  // 3. 强制触发视图更新
                this.defaultCheckedKeys = checkedKeys;  // 4. 设置勾选节点

                // 5. 再等待一个事件循环，确保树组件实际渲染完成
                setTimeout(() => {
                    this.updateOkDangerStatus();
                });
                this.isLoading = false; // ✅ 成功后关闭 loading
            },
            error: (err) => {
                this.isLoading = false; // ✅ 出错也关闭 loading
            }
        });
    }

    // 调用 tree 实例方法统计节点数量
    updateOkDangerStatus(): void {
        const checked = this.nzTreeComponent?.getCheckedNodeList() ?? [];
        const halfChecked = this.nzTreeComponent?.getHalfCheckedNodeList() ?? [];
        const total = checked.length + halfChecked.length;
        this.isOkDanger = (total === 0);
        console.log('选中的' + checked + ', 半选中的' + halfChecked);
    }

    /**
     * 提交授权配置表单
     */
    submitAuthorityConfig() {
        const checked = this.defaultCheckedKeys;
        const halfCheckedKeys = this.nzTreeComponent.getHalfCheckedNodeList().map(item => item.key);
        console.log('提交授权配置表单, 选中的' + checked + ', 半选中的' + halfCheckedKeys);
        const allCheckedKeys = checked.concat(halfCheckedKeys);
        if (allCheckedKeys.length === 0) {
            this.showConfirm();
        } else {
            this.doSubmit(allCheckedKeys.map(Number));
        }
    }

    doSubmit(allCheckedKeys: number[]) {
        this.isOkLoading = true;
        this.isCancelDisabled = true;
        this.roleService.updateRoleAuthorities(this.roleId, allCheckedKeys).subscribe({
            next: x => {
                this.configAuthorityFormDialogDisplay = false;
                this.messageService.create("success", "操作成功");
                this.configAuthorityFormDialogDisplayChange.emit(false);
                this.triggerRoleListRefreshEmitter.emit();
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            },
            error: err => {
                this.messageService.create('error', err.error.msg);
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        });
    }

    showConfirm(): void {
        this.confirmModal = this.modal.confirm({
            nzTitle: '你未选择任何权限，将清空该角色的所有权限！确定要提交吗？',
            nzOkDanger: true,
            nzOnOk: () => this.doSubmit([])
        });
    }

    handleCancel(): void {
        this.configAuthorityFormDialogDisplayChange.emit(false);
    }

    /**
     * 展开所有节点
     */
    expandAll(): void {
        const expandRecursive = (nodes: NzTreeNode[]): void => {
            nodes.forEach(node => {
                node.isExpanded = true;
                if (node.children && node.children.length > 0) {
                    expandRecursive(node.children);
                }
            });
        };

        const rootNodes = this.nzTreeComponent?.getTreeNodes() ?? [];
        expandRecursive(rootNodes);
    }

    /**
     * 折叠所有节点
     */
    collapseAll(): void {
        const collapseRecursive = (nodes: NzTreeNode[]): void => {
            nodes.forEach(node => {
                node.isExpanded = false;
                if (node.children && node.children.length > 0) {
                    collapseRecursive(node.children);
                }
            });
        };

        const rootNodes = this.nzTreeComponent?.getTreeNodes() ?? [];
        collapseRecursive(rootNodes);
    }

    nzEvent(event: NzFormatEmitEvent): void {
        console.log(event);
    }
}
