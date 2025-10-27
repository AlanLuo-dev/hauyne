import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NzDrawerComponent, NzDrawerContentDirective} from "ng-zorro-antd/drawer";
import {FormsModule} from "@angular/forms";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {Column} from "../../../../../common/column";
import {DictTypeService} from "../dict-type.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzModalService} from "ng-zorro-antd/modal";
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
import {DictTypeQuery} from "../dict-type-query";
import {NgClass} from "@angular/common";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";

export interface DeletedDictType {
    id: number,
    dictTypeCode: string,
    dictTypeName: string,
    description: string;
    enabled: boolean;

    switchLoading: boolean;

    createdBy: string;
    createdTime: string;
    lastUpdatedBy: string;
    lastUpdatedTime: string;
    deletedBy: string;
    deletedTime: string;
}


@Component({
    selector: 'app-deleted-dict-type-list',
    imports: [
        NzDrawerComponent,
        NzDrawerContentDirective,
        FormsModule,
        NzButtonComponent,
        NzColDirective,
        NzFormDirective,
        NzFormLabelComponent,
        NzIconDirective,
        NzInputDirective,
        NzRadioComponent,
        NzRadioGroupComponent,
        NzRowDirective,
        NzSwitchComponent,
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
        NgClass
    ],
    templateUrl: './deleted-dict-type-list.component.html',
    styleUrl: './deleted-dict-type-list.component.less'
})
export class DeletedDictTypeListComponent implements OnInit {

    // 是否显示字典值列表
    @Input() visibleTrash: boolean = false;
    @Output() visibleTrashChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    // 列定义
    cols: Column[] = [];

    // 列表数据
    deletedDictTypes: DeletedDictType[] = [];

    // 是否加载中？
    _loading: boolean = true;

    // 总记录数
    totalRecords: number = 0;
    pageSize: number = 10;

    // 过滤条件
    _dictTypeCode: string = '';
    _dictTypeName: string = '';
    selectedEnabled: boolean | null = null;

    enabledOption: any[] = [];


    checked: boolean = false;
    indeterminate = false;
    setOfCheckedId: Set<number> = new Set<number>();

    constructor(private dictTypeService: DictTypeService,
                private messageService: NzMessageService,
                private modal: NzModalService
    ) {
        this.enabledOption = [
            {name: '启用', code: true},
            {name: '禁用', code: false}
        ];

        this.cols = [
            {field: 'id', header: 'Id', isDataKey: true, width: '2%'},
            {field: 'dictTypeCode', header: '字典类型编码', sortable: true, width: '12%'},
            {field: 'dictTypeName', header: '字典类型名称', width: '12%'},
            {field: 'enabled', header: '是否已启用', width: '6%'},
            {field: 'description', header: '描述', width: '28%'},
            {field: 'createdBy', header: '创建人', width: '5%'},
            {field: 'createdTime', header: '创建时间', sortable: true, width: '10%'},
            {field: 'lastUpdatedBy', header: '修改人', width: '5%'},
            {field: 'lastUpdatedTime', header: '修改时间', sortable: true, width: '10%'},
            {field: 'deletedBy', header: '删除人', width: '5%'},
            {field: 'deletedTime', header: '删除时间', sortable: true, width: '10%'},
            {field: 'operation', header: '操作', width: '10%'}
        ];

    }

    ngOnInit(): void {

    }


    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this._loading = true;

        let query: DictTypeQuery = new DictTypeQuery(queryParams, this._dictTypeCode, this._dictTypeName, this.selectedEnabled);
        this.dictTypeService.findDeletedDictTypes(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.deletedDictTypes = res.rows;
                this.totalRecords = res.total;
            },
            error: (err) => {
                this._loading = false;
            },
            complete: () => this._loading = false
        });
    }


    search(): void {
        this.onQueryParamsChange(this.dictTypeService.createLazyLoadMetaData(this.pageSize));
    }

    reset(): void {
        this._dictTypeCode = '';
        this._dictTypeName = '';
        this.selectedEnabled = null;
        this.search();
    }

    /**
     * 关闭弹窗
     */
    hide() {
        this.visibleTrashChange.emit(false);
    }


    updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    onAllChecked(checked: boolean): void {
        this.deletedDictTypes.forEach(({id}) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    refreshCheckedStatus(): void {
        this.checked = this.deletedDictTypes.every(({id}) => this.setOfCheckedId.has(id));
        this.indeterminate = this.deletedDictTypes.some(({id}) => this.setOfCheckedId.has(id)) && !this.checked;
    }

    onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }
}
