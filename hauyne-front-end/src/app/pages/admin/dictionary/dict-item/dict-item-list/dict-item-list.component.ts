import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {DictItemService} from "../dict-item.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzDrawerComponent, NzDrawerContentDirective} from "ng-zorro-antd/drawer";
import {
    NzTableCellDirective,
    NzTableComponent,
    NzTableQueryParams,
    NzTbodyComponent,
    NzTdAddOnComponent,
    NzThAddOnComponent,
    NzTheadComponent,
    NzThMeasureDirective,
    NzThSelectionComponent,
    NzTrDirective
} from "ng-zorro-antd/table";

import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzPopconfirmDirective} from "ng-zorro-antd/popconfirm";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {FormsModule} from "@angular/forms";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {NzModalService} from "ng-zorro-antd/modal";
import {DictItemEditFormComponent} from "../dict-item-edit-form/dict-item-edit-form.component";
import {AuditInfo} from "../../../../../common/audit-info";
import {CdkDrag, CdkDragHandle, CdkDragSortEvent, CdkDropList, moveItemInArray} from "@angular/cdk/drag-drop";
import {AuthorityDirective} from "../../../../../directives/authority.directive";
import {finalize, Observable} from "rxjs";

export interface DictItem extends AuditInfo {
    id: number,
    dictItemCode: string,
    dictItemName: string,
    description: string;
    enabled: boolean;
    sort: number;
    remark: string;
    switchLoading: boolean;
}

@Component({
    selector: 'app-dict-item-list',
    templateUrl: './dict-item-list.component.html',
    styleUrls: ['dict-item-list.component.less'],
    imports: [
        NzDrawerComponent,
        NzTableComponent,
        NzTableCellDirective,
        NzThAddOnComponent,
        NzThMeasureDirective,
        NzThSelectionComponent,
        NzTheadComponent,
        NzTrDirective,
        NzButtonComponent,
        NzIconDirective,
        NzPopconfirmDirective,
        NzSwitchComponent,
        NzTbodyComponent,
        NzTdAddOnComponent,
        NzTooltipDirective,
        FormsModule,
        NzDrawerContentDirective,
        NzColDirective,
        NzFormDirective,
        NzFormLabelComponent,
        NzInputDirective,
        NzRadioComponent,
        NzRadioGroupComponent,
        NzRowDirective,
        DictItemEditFormComponent,
        CdkDropList,
        CdkDrag,
        CdkDragHandle,
        AuthorityDirective
    ]
})
export class DictItemListComponent implements OnInit, OnDestroy {

    /* ------------------------------ 父级组件传入的参数 START ---------------------------------- */

    // 是否显示字典值列表
    @Input() visibleSidebar: boolean = false;
    @Output() visibleSidebarChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() dictTypeId!: number;
    @Input() dictTypeName!: string;

    /* ------------------------------ 父级组件传入的参数 END ---------------------------------- */

    // 列定义
    cols: any[] = [];

    // 列表数据
    listOfDictItemForDictTypeId: DictItem[] = [];

    // 是否加载中？
    _loading: boolean = true;

    selectedDictItems: any[] = [];

    dictItemCountOfDictType: number = 0;

    /* ----------------- 过滤条件 START ---------------  */
    dictItemCode: string = "";
    dictItemName: string = "";
    enabled: boolean | null = null;

    enabledOption: any[] = [];

    /* ----------------- 过滤条件 END ---------------  */

    // @ViewChild("dt") dt: Table;

    /*复选框逻辑 start*/
    checked: boolean = false;
    indeterminate = false;
    setOfCheckedId: Set<number> = new Set<number>();

    dictItemIdToDelete!: number;

    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.listOfDictItemForDictTypeId.forEach(({id}) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.listOfDictItemForDictTypeId.every(({id}) => this.setOfCheckedId.has(id));
        this.indeterminate = this.listOfDictItemForDictTypeId.some(({id}) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    /*复选框逻辑 end*/

    // ~ 字典值表单相关 START ------------------------------------------------------

    formDialogDisplay: boolean = false;
    dictItemId?: number;

    // ~ 字典值表单相关 END ------------------------------------------------------
    cancelButtonDisabled: boolean = false;

    constructor(private readonly dictItemService: DictItemService,
                private readonly messageService: NzMessageService,
                private readonly modal: NzModalService) {
    }

    ngOnInit(): void {
        this.enabledOption = [
            {name: '启用', code: 1},
            {name: '禁用', code: 0}
        ];


        this.cols = [
            {field: 'reorderRow', header: '', reorderRow: true, width: '1%'},
            {field: 'id', header: 'Id', isDataKey: true, width: '1%'},
            {field: 'dictItemCode', header: '字典值编码', sortable: true, width: '16%'},
            {field: 'dictItemName', header: '字典值名称', sortable: true, width: '16%'},
            {field: 'sort', header: '排序', sortable: true, width: '10%'},
            {field: 'enabled', header: '启用状态', width: '10%'},
            {field: 'remark', header: '备注', width: '30%'},
            {field: 'createdByFullName', header: '创建人', width: '5%'},
            {field: 'createdTime', header: '创建时间', sortable: true, width: '10%'},
            {field: 'lastModifiedByFullName', header: '修改人', width: '5%'},
            {field: 'lastUpdatedTime', header: '修改时间', sortable: true, width: '10%'},
            {field: 'operation', header: '操作', width: '5%'}
        ];
    }

    findDictItemCountByDictTypeId() {
        this.dictItemService.findDictItemCountByDictTypeId(this.dictTypeId).subscribe(res => {
            this.dictItemCountOfDictType = res.dictItemCount;
        });
    }

    ngOnDestroy(): void {
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.findDictItemCountByDictTypeId();
        this._loading = true;
        const currentSort = queryParams.sort.find(item => item.value !== null);
        let query: any = {

            /**
             * 排序条件
             */
            sortField: (currentSort && currentSort.key) || null,
            sortOrder: (currentSort && currentSort.value) || null,

            /**
             * 过滤条件
             */
            dictTypeId: this.dictTypeId,
            dictItemCode: this.dictItemCode,
            dictItemName: this.dictItemName,
            enabled: this.enabled
        }
        this.dictItemService.list(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.listOfDictItemForDictTypeId = res;
            },
            error: (err) => {
                this._loading = false;
                this.messageService.create('error', err.error.errorTips);
            },
            complete: () => this._loading = false
        });
    }

    search() {
        this.onQueryParamsChange(this.dictItemService.createLazyLoadMetaData(0));
    }

    clearFilters() {
        this.dictItemName = '';
        this.dictItemCode = '';
        this.enabled = null;
        this.search();
    }

    /**
     * 关闭弹窗
     */
    hide() {
        this.visibleSidebarChange.emit(false);
    }


    dictItemStatusChange(rowData: DictItem) {
        rowData.switchLoading = true;
        const id: number = rowData['id'];
        if (!rowData.enabled) {
            this.dictItemService.enabled(id).subscribe({
                next: x => {
                    rowData.switchLoading = false;
                    rowData.enabled = !rowData.enabled;
                    this.messageService.create('success', '已启用');
                },
                error: err => {
                    this.errorMessage(err, rowData);
                }
            })
        } else {
            this.dictItemService.disabled(id).subscribe({
                next: x => {
                    rowData.switchLoading = false;
                    rowData.enabled = !rowData.enabled;
                    this.messageService.create('success', '已禁用');
                },
                error: err => {
                    this.errorMessage(err, rowData);
                }
            })
        }
    }

    errorMessage(err: any, rowData: DictItem) {
        let tips;
        if (err.error.msg) {
            tips = err.error.msg;
        } else {
            tips = err.error.error;
        }
        this.messageService.create('success', tips);
        rowData.switchLoading = false;
        setTimeout(() => {
            rowData['enabled'] = !rowData['enabled'];
        }, 500);
    }

    /**
     * 显示编辑弹窗
     */
    showEditFormDialog(id?: number) {
        this.formDialogDisplay = true;
        this.dictItemId = id;
    }

    /**
     * 单个删除
     * @param id
     */
    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable(observer => {
            this.dictItemService.deleteById(this.dictItemIdToDelete)
                .pipe(
                    finalize(() => {
                        observer.complete(); // 确保在流结束时完成订阅
                    })
                )
                .subscribe({
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
                });
        });
    }

    /**
     * 批量删除 确认
     */
    batchDeleteConfirm(): void {
        const modalRef = this.modal.confirm({
            nzTitle: '你确定要删除选中的字典值吗？',
            nzOkDanger: true,
            nzOnOk: () =>
                new Promise<void>((resolve, reject) => {
                    // 禁用取消按钮
                    modalRef.updateConfig({
                        nzCancelDisabled: true,
                        nzClosable: false
                    });

                    this.dictItemService.deleteByIds(this.setOfCheckedId).subscribe({
                        next: x => {
                            this.setOfCheckedId.clear();
                            this.refreshCheckedStatus();
                            this.messageService.create('success', '操作成功');
                            this.search();
                            resolve(); // 关闭弹窗
                        },
                        error: err => {
                            this.messageService.create('error', err.error.msg);
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

    /**
     * 拖拽排序
     * @param event
     */
    drop(event: CdkDragSortEvent) {
        moveItemInArray(this.listOfDictItemForDictTypeId, event.previousIndex, event.currentIndex);
        if (event.currentIndex != event.previousIndex) {
            const modalRef = this.modal.confirm({
                nzTitle: '你确定要改变排序吗？',
                nzOkText: '确定',
                nzOkType: 'primary',
                nzOkDanger: true,
                nzOnOk: () => new Promise<void>(
                    (resolve, reject) => {
                        // 禁用取消按钮
                        modalRef.updateConfig({
                            nzCancelDisabled: true,
                            nzClosable: false
                        });

                        this.dictItemService.reorder(
                            this.dictTypeId,
                            this.listOfDictItemForDictTypeId.map(item => item.id)
                        ).subscribe({
                            next: x => {
                                this.messageService.create('success', '操作成功');
                                this.search();
                                this.setOfCheckedId.clear();
                                this.refreshCheckedStatus();
                                resolve(); // 关闭弹窗
                            },
                            error: err => {
                                let tips;
                                if (err.error.errorTips) {
                                    tips = err.error.errorTips;
                                } else {
                                    tips = err.error.msg;
                                }
                                this.messageService.create('error', tips);
                                modalRef.updateConfig({
                                    nzCancelDisabled: false,
                                    nzClosable: true
                                }); // 恢复取消按钮
                                reject(); // 阻止关闭弹窗
                            }
                        });
                    }),
                nzCancelText: '取消',
                nzOnCancel: () => {
                    moveItemInArray(this.listOfDictItemForDictTypeId, event.currentIndex, event.previousIndex);
                }
            });
        }
    }
}
