import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {AuditService} from "../audit.service";
import {NzTimelineModule} from "ng-zorro-antd/timeline";
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzCardModule} from "ng-zorro-antd/card";
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzSpinModule} from "ng-zorro-antd/spin";
import {NzBadgeModule} from "ng-zorro-antd/badge";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";

export interface AuditType {
    typeName: string;
    remark: string;
}

export interface AuditQuery {
    localId: number;
    typeName: string;
}

export interface AuditChangeInfo {
    /**
     * 版本号
     */
    version: number;

    // 变更类型（CREATE=创建，MODIFY=修改，DELETE=删除）
    changeType: string; // "MODIFY", "CREATE", "DELETE"

    /**
     * 提交人
     */
    author: string;

    // 提交时间
    commitDate: string;

    // 字段变更明细列表
    changes: Array<FieldChangeInfo>;
}

export interface FieldChangeInfo {

    // 字段名
    property: string;

    // 字段中文名（展示给用户查看）
    label: string;

    // 原始值（left）
    oldValue: object;

    // 新值（right）
    newValue: object;
}

@Component({
    selector: 'app-audit-list',
    imports: [NzDrawerModule, NzTimelineModule, NzButtonModule, NzTabsModule,
        NzIconDirective, NzCardModule, NzTagModule, NzSpinModule, NzBadgeModule, NzTooltipDirective
    ],
    standalone: true,
    templateUrl: './audit-list.component.html',
    styleUrl: './audit-list.component.less'
})
export class AuditListComponent implements OnInit, OnDestroy {

    /*~ START 生命周期绑定（创建 / 销毁） *******************************/
    @Input() auditDisplay: boolean = false;
    @Output() auditDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();
    /*~ END   生命周期绑定（创建 / 销毁） *********************************/

    // 数据的主键id
    @Input() localId!: number;

    // 数据的 审计日志类型名称 集合，如：角色的增删改是hyn_sys_role，角色授权是hyn_sys_role_authority
    @Input() typeRemarkMap!: Map<string, string>;

    groupChangesMap: Map<string, Array<AuditChangeInfo>> = new Map();

    // 是否正在加载数据 true=正在加载 false=加载完成
    isLoadingAudit: boolean = true;

    selectedTabIndex = 0;
    latestTypeName: string | null = null;

    constructor(private readonly auditService: AuditService) {

    }

    ngOnInit(): void {
        let loadedCount = 0;
        for (const entry of this.typeRemarkEntries) {
            const key: string = entry[0];
            this.auditService.groupChangesByCommit({
                localId: this.localId,
                typeName: key
            }).subscribe({
                next: result => {
                    this.groupChangesMap.set(key, result);
                    onLoadDone();
                },
                error: () => {
                    onLoadDone();
                }
            });
        }

        const onLoadDone = () => {
            loadedCount++;
            if (loadedCount === this.typeRemarkEntries.length) {
                this.isLoadingAudit = false;
                this.detectLatestTab(); // ✅ 在这里调用
            }
        };
    }

    /**
     * 激活数据提交时间最新的tab页
     * @private
     */
    private detectLatestTab(): void {
        let latestDate: string | null = null;
        let latestKey: string | null = null;

        this.typeRemarkEntries.forEach(([typeName]) => {
            const changes = this.groupChangesMap.get(typeName);
            if (changes?.length) {
                const firstChangeTime = changes[0].commitDate;
                if (!latestDate || new Date(firstChangeTime) > new Date(latestDate)) {
                    latestDate = firstChangeTime;
                    latestKey = typeName;
                }
            }
        });

        this.latestTypeName = latestKey;

        // 设置 tab index
        const index = this.typeRemarkEntries.findIndex(([key]) => key === latestKey);
        this.selectedTabIndex = index !== -1 ? index : 0;
    }

    get typeRemarkEntries(): [string, string][] {
        return Array.from(this.typeRemarkMap.entries());
    }

    get groupChangesEntries(): [string, Array<AuditChangeInfo>][] {
        return Array.from(this.groupChangesMap.entries());
    }

    getGroupChanges(typeName: string): AuditChangeInfo[] {
        return this.groupChangesMap.get(typeName) || [];
    }


    closeAuditDialog() {
        this.auditDisplayChange.emit(false);
    }

    ngOnDestroy(): void {
        console.log(1);
    }


}
