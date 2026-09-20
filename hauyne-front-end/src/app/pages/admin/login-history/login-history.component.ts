import {
    AfterViewInit,
    ChangeDetectionStrategy,
    Component,
    ElementRef,
    OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import { LoginHistoryService } from "./login-history.service";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NzButtonComponent } from "ng-zorro-antd/button";
import { NzFormDirective } from "ng-zorro-antd/form";
import { NzIconDirective } from "ng-zorro-antd/icon";
import { NzInputDirective } from "ng-zorro-antd/input";
import { NzRadioComponent, NzRadioGroupComponent } from "ng-zorro-antd/radio";
import { NzTableComponent, NzTableModule, NzTableQueryParams } from "ng-zorro-antd/table";
import { LoginHistoryQuery } from "./login-history-query";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";

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
        NzFormDirective,
        NzIconDirective,
        NzInputDirective,
        NzRadioComponent,
        NzRadioGroupComponent,
        NzTableModule,
        NzTooltipDirective
    ]
})
export class LoginHistoryComponent implements OnInit, AfterViewInit, OnDestroy {

    listOfLoginHistory: LoginHistory[] = [];
    _loading: boolean = true;

    totalRecords: number = 0;
    pageSize: number = 20; // 遵循标准默认 20 条

    // 过滤条件
    type: number | null = null;
    username: string = '';

    @ViewChild('tableContainer') tableContainer!: ElementRef<HTMLElement>;
    @ViewChild('basicTable', { static: false }) table!: NzTableComponent<any>;

    // 动态滚动高度
    tableScrollY: string = '400px';
    private resizeObserver?: ResizeObserver;

    constructor(private readonly loginLogService: LoginHistoryService) {}

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

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this._loading = true;

        const query = new LoginHistoryQuery(queryParams, this.type, this.username);
        this.loginLogService.loadPageData<LoginHistory, LoginHistoryQuery>(query).subscribe({
            next: (res) => {
                this.listOfLoginHistory = res.rows;
                this.totalRecords = res.total;
            },
            error: () => {
                this._loading = false;
            },
            complete: () => {
                this._loading = false;
            }
        });
    }

    search(): void {
        this.onQueryParamsChange(this.loginLogService.createLazyLoadMetaData(this.pageSize));
    }

    reset(): void {
        this.type = null;
        this.username = '';
        this.search();
    }

    // 在 LoginHistoryComponent 中添加以下解析方法

    /**
     * 根据操作系统名称匹配 Ant Design 图标
     */
    getOsIcon(osName: string): string {
        if (!osName) return 'desktop';
        const name = osName.toLowerCase();
        if (name.includes('win')) return 'windows';
        if (name.includes('mac') || name.includes('darwin') || name.includes('ios')) return 'apple';
        if (name.includes('android')) return 'android';
        if (name.includes('linux')) return 'linux';
        return 'desktop'; // 兜底图标
    }

    /**
     * 根据浏览器名称匹配 Ant Design 图标
     */
    getBrowserIcon(browser: string): string {
        if (!browser) return 'global';
        const name = browser.toLowerCase();
        if (name.includes('chrome')) return 'chrome';
        if (name.includes('firefox')) return 'firefox';
        if (name.includes('safari')) return 'compass'; // 或 apple
        if (name.includes('edge') || name.includes('ie')) return 'ie';
        return 'global'; // 兜底图标
    }
}
