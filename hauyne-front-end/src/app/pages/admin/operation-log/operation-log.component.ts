import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NzModalComponent, NzModalContentDirective} from "ng-zorro-antd/modal";
import {
    NzTableCellDirective,
    NzTableComponent,
    NzTableQueryParams,
    NzTbodyComponent,
    NzTheadComponent,
    NzThMeasureDirective,
    NzTrDirective
} from "ng-zorro-antd/table";
import {Column} from "../../../common/column";

import {OperationLogService} from "./operation-log.service";
import {OperationLogQuery} from "./operation-log-query";

export interface OperationLog {
    operator: string,
    operateTime: string,
    subType: string;
    actions: string[];
}

@Component({
    selector: 'app-operation-log',
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        NzTableComponent,
        NzTableCellDirective,
        NzThMeasureDirective,
        NzTheadComponent,
        NzTrDirective,
        NzTbodyComponent
    ],
    templateUrl: './operation-log.component.html',
    styleUrl: './operation-log.component.less'
})
export class OperationLogComponent {

    /**
     * 表单弹窗开关（双向绑定）
     */
    @Input() operationLogDialogDisplay: boolean = false;
    @Output() operationLogDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() titlePrefix: string = '';

    @Input() type: string = '';
    @Input() bizId: number = 0;

    // 列定义
    cols: Column[] = [];

    // 列表数据
    listOfOperationLog: OperationLog[] = [];

    // 是否加载中？
    _loading: boolean = true;

    // 总记录数
    totalRecords: number = 0;
    pageSize: number = 10;

    constructor(private operationLogService: OperationLogService) {

        this.cols = [
            {field: 'operator', header: '操作人', width: '10%'},
            {field: 'operateTime', header: '操作时间', width: '10%'},
            {field: 'subType', header: '动作', width: '10%'},
            {field: 'action', header: '日志内容', width: '70%'}
        ];
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this._loading = true;

        let query: OperationLogQuery = new OperationLogQuery(queryParams, this.type, this.bizId);
        this.operationLogService.loadPageData<OperationLog, OperationLogQuery>(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.listOfOperationLog = res.rows;
                this.totalRecords = res.total;
            },
            error: (err) => {
                this._loading = false;
            },
            complete: () => this._loading = false
        });
    }

    closeDialog() {
        this.operationLogDialogDisplayChange.emit(false);
    }
}
