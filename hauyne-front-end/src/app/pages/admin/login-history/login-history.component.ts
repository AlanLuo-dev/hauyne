import {
    AfterViewInit,
    ChangeDetectionStrategy,
    Component,
    ElementRef,
    OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import {LoginHistoryService} from "./login-history.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {NzTableComponent, NzTableModule, NzTableQueryParams} from "ng-zorro-antd/table";
import {LoginHistoryQuery} from "./login-history-query";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import {DictTypeService} from "../dictionary/dict-type/dict-type.service";
import {Option} from "../dictionary/dict-type/dict-dropdown";

export interface LoginHistory {
    id: number;
    type: string;
    result: string;
    failReason: string;
    username: string;
    ipAddress: string;
    location: string;
    browser: string;
    browserVersion: string;
    osName: string;
    loginTime: string;
}

@Component({
    selector: 'app-login-history',
    templateUrl: './login-history.component.html',
    styleUrls: ['./login-history.component.less'],
    changeDetection: ChangeDetectionStrategy.Eager,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        NzButtonComponent,
        NzIconDirective,
        NzInputDirective,
        NzRadioComponent,
        NzRadioGroupComponent,
        NzTableModule,
        NzTooltipDirective,
        NzDatePickerModule
    ]
})
export class LoginHistoryComponent implements OnInit, AfterViewInit, OnDestroy {

    listOfLoginHistory: LoginHistory[] = [];
    _loading: boolean = true;

    totalRecords: number = 0;
    pageSize: number = 20; // 遵循标准默认 20 条

    // 唯一的查询状态源
    query = new LoginHistoryQuery();

    browserOptions: Option[] = [];
    osOptions: Option[] = [];
    browserOptionMap = new Map<string, Option>();
    osOptionMap = new Map<string, Option>();

    @ViewChild('tableContainer') tableContainer!: ElementRef<HTMLElement>;
    @ViewChild('basicTable', { static: false }) table!: NzTableComponent<any>;

    // 动态滚动高度
    tableScrollY: string = '400px';
    private resizeObserver?: ResizeObserver;

    constructor(private readonly loginLogService: LoginHistoryService,
                private readonly dictTypeService: DictTypeService) {
        this.dictTypeService.loadDropdownData(['Browser', 'OS'])
            .subscribe(data => {
                const dictMap = new Map(
                    data.map(item => [item.dictTypeCode, item.options])
                );

                this.browserOptions = dictMap.get('Browser') ?? [];
                this.osOptions = dictMap.get('OS') ?? [];

                this.browserOptionMap = new Map(
                    this.browserOptions.map(item => [item.value, item])
                );
                this.osOptionMap = new Map(
                    this.osOptions.map(item => [item.value, item])
                );
            });
    }

    ngOnInit(): void {}

    ngAfterViewInit(): void {
        setTimeout(() => this.calculateTableScrollY(), 0);

        if (typeof ResizeObserver !== 'undefined') {
            this.resizeObserver = new ResizeObserver(() => {
                window.requestAnimationFrame(() => this.calculateTableScrollY());
            });
            if (this.tableContainer?.nativeElement) {
                this.resizeObserver.observe(this.tableContainer.nativeElement);
            }
        }
    }

    ngOnDestroy(): void {
        this.resizeObserver?.disconnect();
    }

    /**
     * 动态计算表格内容区域的真实滚动高度
     */
    private calculateTableScrollY(): void {
        if (!this.tableContainer) return;

        const containerHeight = this.tableContainer.nativeElement.clientHeight;
        if (containerHeight === 0) return;

        // 扣除表头 (~39px) 与底部分页条 (~48px)
        const calculatedHeight = containerHeight - 88;
        this.tableScrollY = `${Math.max(calculatedHeight, 120)}px`;
    }

    // 分页/排序变动时更新 query 状态并加载
    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.query.updateQueryParams(queryParams);
        this.loadData();
    }

    search(): void {
        this.loadData();
    }

    reset(): void {
        this.query.resetFilter();
    }

    private loadData(): void {
        this._loading = true;
        this.loginLogService.loadPageData2<LoginHistory, LoginHistoryQuery>(this.query).subscribe({
            next: (res) => {
                this.listOfLoginHistory = res.rows;
                this.totalRecords = res.total;
            },
            error: () => this._loading = false,
            complete: () => this._loading = false
        });
    }

}
