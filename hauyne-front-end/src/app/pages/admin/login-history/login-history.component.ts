import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {LoginHistoryService} from "./login-history.service";
import {LoginHistory} from "./login-history";
import {Column} from "../../../common/column";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzRadioComponent, NzRadioGroupComponent} from "ng-zorro-antd/radio";
import {
    NzTableCellDirective,
    NzTableComponent,
    NzTableQueryParams,
    NzTbodyComponent,
    NzThAddOnComponent,
    NzTheadComponent,
    NzThMeasureDirective,
    NzTrDirective
} from "ng-zorro-antd/table";
import {LoginHistoryQuery} from "./login-history-query";

/**
 * 登录日志组件
 */
@Component({
    selector: 'app-login-history',
    templateUrl: './login-history.component.html',
    styleUrls: ['./login-history.component.less'],
    imports: [
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
        NzTableCellDirective,
        NzTableComponent,
        NzTbodyComponent,
        NzThAddOnComponent,
        NzThMeasureDirective,
        NzTheadComponent,
        NzTrDirective,
        ReactiveFormsModule
    ]
})
export class LoginHistoryComponent implements OnInit, AfterViewInit {

    // 列定义
    cols: Column[] = [];

    // 列表数据
    listOfLoginHistory: LoginHistory[] = [];

    // 是否加载中？
    _loading: boolean = true;

    // 总记录数
    totalRecords: number = 0;
    pageSize: number = 10;

    @ViewChild('input') input!: ElementRef;


    types: any[]=[];

    // 过滤条件
    type: number | null = null;
    username: string = '';


    constructor(
        private loginLogService: LoginHistoryService,
    ) {
    }

    ngAfterViewInit(): void {

    }

    ngOnInit(): void {
        this.cols = [
            {field: 'id', header: 'ID'},
            {field: 'type', header: '类型'},
            {field: 'result', header: '结果'},
            {field: 'failReason', header: '原因'},
            {field: 'username', header: '用户名'},
            {field: 'ipAddress', header: '客户端ip',},
            {field: 'location', header: '登录地点',},
            {field: 'browser', header: '客户端浏览器',},
            {field: 'browserVersion', header: '客户端浏览器版本',},
            {field: 'osName', header: '客户端操作系统名称',},
            {field: 'createTime', header: '创建时间'}
        ];

        this.types = [
            {name: '登录', code: 1},
            {name: '注销', code: 0}
        ];
    }

    onQueryParamsChange(queryParams: NzTableQueryParams): void {
        this.pageSize = queryParams.pageSize;
        this._loading = true;

        let query: LoginHistoryQuery = new LoginHistoryQuery(queryParams, this.type, this.username);
        this.loginLogService.loadPageData<LoginHistory, LoginHistoryQuery>(query).subscribe({
            next: (res) => {
                this._loading = false;
                this.listOfLoginHistory = res.rows;
                this.totalRecords = res.total;
            },
            error: (err) => {
                this._loading = false;
            },
            complete: () => this._loading = false
        });
    }

    search(){
        this.onQueryParamsChange(this.loginLogService.createLazyLoadMetaData(this.pageSize));
    }

    reset(){
        this.type = null;
        this.username = '';
        this.search();
    }
}

