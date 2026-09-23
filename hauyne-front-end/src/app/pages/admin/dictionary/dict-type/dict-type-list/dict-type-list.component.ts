import {
    AfterViewInit,
    ChangeDetectionStrategy,
    Component,
    ElementRef,
    OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import {DictTypeService} from "../dict-type.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {FormsModule} from "@angular/forms";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzPopconfirmDirective} from "ng-zorro-antd/popconfirm";
import {
    NzCellAlignDirective,
    NzCellFixedDirective,
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
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {DictTypeQuery} from "../dict-type-query";
import {AuditInfo} from "../../../../../common/audit-info";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {NzModalService} from "ng-zorro-antd/modal";
import {DictTypeEditFormComponent} from "../dict-type-edit-form/dict-type-edit-form.component";
import {DictItemListComponent} from "../../dict-item/dict-item-list/dict-item-list.component";
import {OperationLogComponent} from "../../../operation-log/operation-log.component";
import {DeletedDictTypeListComponent} from "../deleted-dict-type-list/deleted-dict-type-list.component";
import {finalize, Observable} from "rxjs";
import {AuthorityDirective} from "../../../../../directives/authority.directive";
import {EnumOption} from "../../../../../common/enum-option";

export interface DictType extends AuditInfo {
    id: number,
    dictTypeCode: string,
    dictTypeName: string,
    description: string;
    enabled: boolean;
    builtin: EnumOption<boolean>;

    switchLoading: boolean;
}


@Component({
    selector: 'app-dict-type',
    templateUrl: './dict-type-list.component.html',
    imports: [
        FormsModule,
        NzButtonComponent,
        NzIconDirective,
        NzInputDirective,
        NzPopconfirmDirective,
        NzTableCellDirective,
        NzTableComponent,
        NzTbodyComponent,
        NzTdAddOnComponent,
        NzThAddOnComponent,
        NzThMeasureDirective,
        NzThSelectionComponent,
        NzTheadComponent,
        NzTooltipDirective,
        NzTrDirective,
        NzSwitchComponent,
        NzRadioGroupComponent,
        NzRadioComponent,
        DictTypeEditFormComponent,
        DictItemListComponent,
        OperationLogComponent,
        DeletedDictTypeListComponent,
        AuthorityDirective,
        NzCellFixedDirective,
        NzCellAlignDirective
    ],
    styleUrls: ['./dict-type-list.component.less'],
    changeDetection: ChangeDetectionStrategy.Eager,
    providers: [NzModalService]
})
export class DictTypeListComponent implements OnInit, AfterViewInit, OnDestroy {

    // 列表数据
    listOfDictType: DictType[] = [];

    // 是否加载中？
    _loading: boolean = true;

    // 总记录数
    totalRecords: number = 0;
    pageSize: number = 20;

    // 过滤条件
    _dictTypeCode: string = '';
    _dictTypeName: string = '';
    selectedEnabled: boolean | null = null;
    selectedBuiltin: boolean | null = null;

    builtInOption: any[] = [
        {value: false, label: '自定义', icon: 'unlock', class: 'sys-status-custom'},
        {value: true, label: '系统内置', icon: 'lock', class: 'sys-status-builtin'}
    ]

    enabledOption: any[] = [];


    // @ViewChild("dictTypeTable") dt: Table;

    formDialogDisplay: boolean = false;

    // 是否显示字典值列表弹窗
    dictItemListDialogDisplay: boolean = false;
    selectedDictTypeName: string = "";

    checked: boolean = false;
    indeterminate = false;
    setOfCheckedId: Set<number> = new Set<number>();

    formTitle: string = "";
    dictTypeId?: number;

    operationLogDialogDisplay: boolean = false;
    dictTypeName: string = "";

    visibleTrash: boolean = false;

    dictTypeIdToDelete!: number;

    cancelButtonDisabled: boolean = false;

    @ViewChild('tableContainer') tableContainer!: ElementRef<HTMLElement>;

    // 1. 声明动态滚动高度变量
    tableScrollY: string = '400px';
    private resizeObserver?: ResizeObserver;

    constructor(private readonly dictTypeService: DictTypeService,
                private readonly messageService: NzMessageService,
                private readonly modal: NzModalService,
                private readonly elementRef: ElementRef
    ) {


    }

    ngAfterViewInit(): void {
        // 延迟一帧，确保页面初始化渲染完成后计算高度
        setTimeout(() => this.calculateTableScrollY(), 0);

        // 2. 监听容器尺寸变化（兼容浏览器缩放、分辨率改变、窗口 Resize）
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
    /**
     * 动态计算表格内容区域可滚动的真实高度
     */
    private calculateTableScrollY(): void {
        if (!this.tableContainer) return;

        // 获取 table 容器外层的实际像素高度
        const containerHeight = this.tableContainer.nativeElement.clientHeight;
        if (containerHeight === 0) return;

        /**
         * 扣除项说明：
         * 1. nzSize="small" 表头高度 (~39px)
         * 2. 底部分页栏高度 (~48px)
         * 综合扣除约 87px - 90px
         */
        const calculatedHeight = containerHeight - 120;

        // 赋值给 nzScroll.y
        this.tableScrollY = `${Math.max(calculatedHeight, 100)}px`;
    }

    ngOnInit(): void {
        this.enabledOption = [
            {name: '启用', code: true},
            {name: '禁用', code: false}
        ];

    }

    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.listOfDictType.forEach(({id}) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.listOfDictType.every(({id}) => this.setOfCheckedId.has(id));
        this.indeterminate = this.listOfDictType.some(({id}) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    search(): void {
        this.onQueryParamsChange(this.dictTypeService.createLazyLoadMetaData(this.pageSize));
    }

    reset(): void {
        this._dictTypeCode = '';
        this._dictTypeName = '';
        this.selectedEnabled = null;
        this.selectedBuiltin = null;
        this.search();
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this._loading = true;

        let query: DictTypeQuery = new DictTypeQuery(queryParams, this._dictTypeCode, this._dictTypeName, this.selectedEnabled, this.selectedBuiltin);
        this.dictTypeService.loadPageData<DictType, DictTypeQuery>(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.listOfDictType = res.rows;
                this.totalRecords = res.total;
            },
            error: (err) => {
                this._loading = false;
                if (err.error?.errorTips) {
                    this.messageService.create('error', err.error.errorTips);
                } else if (err.error?.error) {
                    this.messageService.create('error', err.error.error);
                }
            },
            complete: () => this._loading = false
        });
    }

    /**
     * 显示编辑弹窗
     */
    showFormDialog(id?: number) {
        this.formDialogDisplay = true;
        this.dictTypeId = id;
        this.formTitle = (id ? '编辑' : '创建') + '字典类型';
    }

    showDeleteConfirm(): void {
        const modalRef = this.modal.confirm({
            nzTitle: '你确定要删除选中的字典类型吗？',
            nzContent: '<b style="color: red;">有字典值的字典类型，需要先删除字典值才能删除</b>',
            nzOkDanger: true,
            nzOnOk: () =>
                new Promise<void>((resolve, reject) => {

                    // 禁用取消按钮
                    modalRef.updateConfig({
                        nzCancelDisabled: true,
                        nzClosable: false
                    });

                    this.dictTypeService.deleteByIds(this.setOfCheckedId)
                        .subscribe(
                            {
                                next: x => {
                                    this.setOfCheckedId.clear();
                                    this.refreshCheckedStatus();
                                    this.messageService.create('success', '操作成功');
                                    this.search();
                                    resolve(); // 关闭弹窗
                                },
                                error: err => {
                                    this.messageService.create('error', err.error.errorTips);
                                    modalRef.updateConfig({
                                        nzCancelDisabled: false,
                                        nzClosable: true
                                    }); // 恢复取消按钮
                                    reject(); // 阻止关闭弹窗
                                }
                            }
                        )
                })
        });
    }

    beforeConfirm = (): Observable<boolean> => {
        this.cancelButtonDisabled = true;
        return new Observable(observer => {
            this.dictTypeService.deleteById(this.dictTypeIdToDelete)
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

    showDictItemListDialog(row: any) {
        this.dictItemListDialogDisplay = true;
        this.dictTypeId = row.id;
        this.selectedDictTypeName = row.dictTypeName;
    }

    showOperationLogDialog(row: any) {
        this.dictTypeId = row.id;
        this.dictTypeName = row.dictTypeName;
        this.operationLogDialogDisplay = true;
    }


    statusChange(rowData: DictType) {
        rowData.switchLoading = true;
        const id: number = rowData['id'];
        if (!rowData.enabled) {
            this.dictTypeService.enabled(id).subscribe({
                next: x => {
                    rowData.switchLoading = false;
                    rowData.enabled = !rowData.enabled;
                    this.messageService.create('success', '已启用');
                },
                error: err => {
                    this.errorMsg(err, rowData);
                }
            })
        } else {
            this.dictTypeService.disabled(id).subscribe({
                next: x => {
                    rowData.switchLoading = false;
                    rowData.enabled = !rowData.enabled;
                    this.messageService.create('success', '已禁用');
                },
                error: err => {
                    this.errorMsg(err, rowData);
                }
            })
        }
    }

    errorMsg(err: any, rowData: DictType) {
        let errTips;
        if (err.error.msg) {
            errTips = err.error.msg;
        } else {
            errTips = err.error.error;
        }
        rowData.switchLoading = false;
        this.messageService.create('error', errTips);
    }
}
