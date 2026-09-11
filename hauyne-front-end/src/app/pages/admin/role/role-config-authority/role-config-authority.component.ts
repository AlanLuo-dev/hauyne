import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import {NzButtonComponent, NzButtonModule} from "ng-zorro-antd/button";
import {NzTooltipDirective, NzTooltipModule} from "ng-zorro-antd/tooltip";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzFormatEmitEvent, NzTreeComponent, NzTreeNode, NzTreeNodeOptions} from "ng-zorro-antd/tree";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzModalRef, NzModalService} from "ng-zorro-antd/modal";
import {RoleService} from "../role.service";
import {AuthorityService} from "../../authority/authority.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {Role} from "../role-list/role-list.component";
import {forkJoin} from "rxjs";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzSpinModule} from "ng-zorro-antd/spin";
import {NzSwitchComponent} from "ng-zorro-antd/switch";

@Component({
    selector: 'app-role-config-authority',
    imports: [
        NzDrawerModule,
        NzButtonModule,
        NzTooltipModule,

        ReactiveFormsModule,
        NzTreeComponent,
        NzIconDirective,
        NzButtonComponent,
        NzTooltipDirective,
        NzInputGroupComponent,
        NzInputDirective,
        FormsModule,
        NzSpinModule,
        NzSwitchComponent,

    ],
    templateUrl: './role-config-authority.component.html',
    styleUrl: './role-config-authority.component.less'
})
export class RoleConfigAuthorityComponent implements OnChanges {

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

    role?: Role;

    confirmModal?: NzModalRef; // For testing by now

    isOkDanger: boolean = false;

    searchValue: string = '';

    // 是否加载中 true=是，false=否
    isLoading: boolean = true;

    // 是否已展开全部节点
    isAllExpanded: boolean = false;

    constructor(private readonly roleService: RoleService,
                private readonly authorityService: AuthorityService,
                private readonly messageService: NzMessageService,
                private readonly modal: NzModalService) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['roleId'] && this.roleId != null) {
            this.init();
        }
    }

    private init(): void {
        this.isLoading = true;      // 1. 进入加载状态

        // 5. 一次性加载三个数据
        forkJoin({
            role: this.roleService.getOne<Role>(this.roleId),
            nodes: this.authorityService.loadAuthorityCheckBoxTree(),
            checkedKeys: this.roleService.authorityLeafNodeKeys(this.roleId)
        }).subscribe({
            next: ({role, nodes, checkedKeys}) => {

                // 数据全部准备完成
                this.role = role;
                this.nodes = nodes;
                this.defaultCheckedKeys = checkedKeys;

                // 已经有权限 → 不危险
                this.isOkDanger = checkedKeys.length === 0;

                // 最后一步才允许 Tree 创建
                this.treeReady = true;
                this.isLoading = false;
            },
            error: err => {
                this.isLoading = false;
                this.treeReady = false;

                this.messageService.error('加载角色权限失败');
            }
        });
    }

    get drawerTitle(): string {
        return this.role
            ? `配置【${this.role.roleName}】角色的权限`
            : '配置角色的权限';
    }

    changeCheckStrictly(): void {
        if (!this.role) {
            return;
        }

        this.role.authorityCheckLinkage.value = !this.role.authorityCheckLinkage.value;
    }

    /**
     * 递归获取所有选中的节点
     * @param nodes 树节点数组
     * @private 私有方法
     */
    private getAllCheckedNodes(nodes: NzTreeNode[]): NzTreeNode[] {
        const result: NzTreeNode[] = [];
        const walk = (nodeList: NzTreeNode[]) => {
            for (const node of nodeList) {
                if (node.isChecked) {
                    result.push(node);
                }
                if (node.children?.length) {
                    walk(node.children);
                }
            }
        };
        walk(nodes);

        return result;
    }

    // 调用 tree 实例方法统计节点数量
    updateOkDangerStatus(): void {
        const allNodes = this.nzTreeComponent?.getTreeNodes() ?? [];
        const checkedNodes = this.getAllCheckedNodes(allNodes);


        const halfCheckedNodes = this.nzTreeComponent?.getHalfCheckedNodeList() ?? [];   // 半选中的节点数组
        const total = checkedNodes.length + halfCheckedNodes.length;                                // 总选中的节点数量
        this.isOkDanger = (total === 0);

        const checkedKeys = checkedNodes.map(value => value.key + ' = ' + value.title);
        const halfCheckedKeys = halfCheckedNodes.map(value => value.key + ' = ' + value.title);
        console.log('选中的【' + checkedKeys + '】, 半选中的【' + halfCheckedKeys + '】');


    }

    /**
     * 提交授权配置表单
     */
    submitAuthorityConfig() {
        const allNodes = this.nzTreeComponent?.getTreeNodes() ?? [];

        const checkedNodes = this.getAllCheckedNodes(allNodes);
        const halfCheckedNodeList = this.nzTreeComponent.getHalfCheckedNodeList();

        const checked = checkedNodes.map(item => item.key);
        const halfCheckedKeys = halfCheckedNodeList.map(item => item.key);
        console.log('提交授权配置表单, 选中的' + checked + ', 半选中的' + halfCheckedKeys);

        const checkedObj = checkedNodes.map(value => value.key + ' = ' + value.title);
        const halfCheckedObj = halfCheckedNodeList.map(value => value.key + ' = ' + value.title);
        console.log('选中的【' + checkedObj + '】, 半选中的【' + halfCheckedObj + '】');

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

        this.roleService.updateRoleAuthorities(this.roleId, {
            authorityIds: allCheckedKeys,
            authorityCheckLinkage: this.role!.authorityCheckLinkage.value
        }).subscribe({
            next: x => {
                this.configAuthorityFormDialogDisplay = false;
                this.messageService.create("success", "操作成功");
                this.configAuthorityFormDialogDisplayChange.emit(false);
                this.triggerRoleListRefreshEmitter.emit();
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            },
            error: err => {
                this.messageService.create('error', err.error.errorTips);
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

    toggleExpandAll(): void {
        if (this.isAllExpanded) {
            this.collapseAll();
        } else {
            this.expandAll();
        }

        this.isAllExpanded = !this.isAllExpanded;
    }

    /**
     * 展开所有节点
     */
    private expandAll(): void {
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
    private collapseAll(): void {
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
