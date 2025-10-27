import {Component, OnInit} from '@angular/core';
import {NzTableModule, NzTableQueryParams, NzTdAddOnComponent} from "ng-zorro-antd/table";

import {FormBuilder, FormsModule} from "@angular/forms";
import {AuthorityService} from "../authority.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzModalService} from "ng-zorro-antd/modal";
import {AuditInfo} from "../../../../common/audit-info";
import {AuthorityQuery} from "../AuthorityQuery";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzPopconfirmDirective} from "ng-zorro-antd/popconfirm";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {DictTypeService} from "../../dictionary/dict-type/dict-type.service";
import {AuthorityEditFormComponent} from "../authority-edit-form/authority-edit-form.component";
import {finalize, Observable} from "rxjs";

export interface Authority extends AuditInfo {
    // key: string;
    // name: string;
    // age?: number;
    // level?: number;
    expand?: boolean;
    // address?: string;
    // children?: TreeNodeInterface[];
    parent?: Authority;


    /**
     * id
     */
    id: number;
    leaf: boolean;
    level: number;

    /**
     * 权限类型
     */
    authorityType: string;

    /**
     * 权限编码
     */
    authorityCode: string;

    /**
     * 权限名称
     */
    authorityName: string;

    /**
     * 图标
     */
    icon: string;

    /**
     * 请求路径
     */
    path: string;

    /**
     * 排序（无符号）
     */
    sort: number;

    /**
     * 备注
     */
    remark: string;

    /**
     * 子菜单集合
     */
    children: Authority[];
}

@Component({
    selector: 'app-authority',
    imports: [
        NzTableModule,
        NzTdAddOnComponent,
        NzButtonComponent,
        NzIconDirective,
        NzPopconfirmDirective,
        NzTooltipDirective,
        FormsModule,
        NzColDirective,
        NzFormDirective,
        NzFormLabelComponent,
        NzInputDirective,
        NzRadioComponent,
        NzRadioGroupComponent,
        NzRowDirective,
        AuthorityEditFormComponent
    ],
    templateUrl: './authority-list.component.html',
    styleUrl: './authority-list.component.less',
    providers: [NzModalService]
})
export class AuthorityListComponent implements OnInit {

    // 列定义
    cols: any[] = [];

    // 列表数据
    treeNodeListOfAuthority: Authority[] = [];

    // 是否加载中？
    _loading: boolean = true;


    // mapOfExpandedData: { [key: number]: Authority[] } = {};

    mapOfExpandedData: Map<number, Authority[]> = new Map<number, Authority[]>();

    // @ViewChild("treeDt") dt: Table;


    formTitle: string = '';
    header: string = '';
    formDialogDisplay: boolean = false;
    authorityId?: number;

    authorityName: string = '';
    authorityType: string = '';

    authorityTypeOption: any[] = [];

    // 用于记录展开状态的集合
    expandedNodes: Set<number> = new Set<number>();

    // 将要删除的id
    idToDelete: number = 0;

    cancelButtonDisabled: boolean = false;

    /**
     * 构造函数
     * @param authorityService
     * @param dictTypeService
     * @param messageService
     */
    constructor(private readonly authorityService: AuthorityService,
                private readonly dictTypeService: DictTypeService,
                private readonly messageService: NzMessageService) {

        this.dictTypeService.loadDropdownData('authority_type').subscribe(res => {
            this.authorityTypeOption = res;
        })
    }

    ngOnInit(): void {

        this.cols = [
            {field: 'authorityName', header: '权限名称', width: '14%'},
            {field: 'authorityCode', header: '权限编码', width: '11%'},
            {field: 'path', header: '请求路径', width: '13%'},
            {field: 'authorityType', header: '类型', width: '5%'},
            {field: 'level', header: '层级', width: '3%'},
            {field: 'leaf', header: '叶子节点', width: '7%'},
            {field: 'sort', header: '排序', sortable: true, width: '3%'},
            {field: 'createdByFullName', header: '创建人', width: '5%'},
            {field: 'createdTime', header: '创建时间', width: '10%'},
            {field: 'lastModifiedByFullName', header: '修改人', width: '5%'},
            {field: 'lastUpdatedTime', header: '修改时间', width: '10%'},
            {field: 'operation', header: '操作', width: '5%'}
        ];
    }


    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this._loading = true;

        const currentSort = queryParams.sort.find(item => item.value !== null);
        let query: AuthorityQuery = {
            authorityType: this.authorityType,
            authorityName: this.authorityName,
            /**
             * 排序条件
             */
            sortField: (currentSort && currentSort.key) || null,
            sortOrder: (currentSort && currentSort.value) || null,
        };
        this.authorityService.list(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.treeNodeListOfAuthority = res;

                this.treeNodeListOfAuthority.forEach(item => {
                    this.mapOfExpandedData.set(item.id, this.convertTreeToList(item));
                });
            },
            error: (err) => {
                this.messageService.create('error', err.error.errorTips);
                this._loading = false;
            },
            complete: () => this._loading = false
        });
    }


    search(): void {
        this.onQueryParamsChange(this.authorityService.createLazyLoadMetaData(0));
    }

    reset(): void {
        this.authorityName = '';
        this.authorityType = '';
        this.expandedNodes.clear();
        this.search();
    }

    /**
     * 折叠
     * @param array
     * @param data
     * @param $event
     */
    collapse(array: Authority[], data: Authority, $event: boolean): void {
        if (!$event) {
            this.expandedNodes.delete(data.id);
            if (data.children) {
                data.children.forEach(d => {
                    const target = array.find(a => a.id === d.id)!;
                    target.expand = false;
                    this.collapse(array, target, false);
                });
            } else {
                return;
            }
        } else {
            this.expandedNodes.add(data.id);
        }
    }

    /**
     * 将树转换为数组
     * @param root
     */
    convertTreeToList(root: Authority): Authority[] {
        const stack: Authority[] = [];
        const array: Authority[] = [];
        const hashMap = {};

        stack.push({...root, level: 0, expand: this.expandedNodes.has(root.id)});

        while (stack.length !== 0) {
            const node = stack.pop()!;
            this.visitNode(node, hashMap, array);
            if (node.children) {
                for (let i = node.children.length - 1; i >= 0; i--) {
                    stack.push({
                        ...node.children[i],
                        level: node.level! + 1,
                        expand: this.expandedNodes.has(node.children[i].id),
                        parent: node
                    });
                }
            }
        }


        return array;
    }

    visitNode(node: Authority,
              hashMap: { [key: string]: boolean },
              array: Authority[]): void {
        if (!hashMap[node.id]) {
            hashMap[node.id] = true;
            array.push(node);
        }
    }


    /**
     * 显示权限 新增、编辑表单弹窗
     * @param id 可选
     */
    showFormDialog(id?: number) {
        this.formDialogDisplay = true;
        this.formTitle = (id ? '编辑' : '新增') + '权限资源';
        this.authorityId = id;
    }

    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable(observer => {
            this.authorityService.deleteById(this.idToDelete)
                .pipe(
                    finalize(() => {
                        observer.complete(); // 确保在流结束时完成订阅
                    })
                )
                .subscribe({
                    next: x => {
                        observer.next(true);
                        this.messageService.create('success', '操作成功');
                        this.search();
                        this.cancelButtonDisabled = false;
                    },
                    error: err => {
                        observer.next(false);
                        this.messageService.create('error', err.error?.errorTips, {nzDuration: 10000});
                        this.cancelButtonDisabled = false;
                    }
                });
        });
    }
}
