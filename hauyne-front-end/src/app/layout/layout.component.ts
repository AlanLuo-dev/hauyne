import {Component} from '@angular/core';
import {MatSidenavModule} from "@angular/material/sidenav";
import {NgTemplateOutlet} from "@angular/common";
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {NzContentComponent, NzHeaderComponent} from "ng-zorro-antd/layout";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatToolbarModule} from "@angular/material/toolbar";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {NzMenuModule} from "ng-zorro-antd/menu";
import screenfull from 'screenfull';
import {AuthService} from "../login/auth.service";
import {SystemMenu} from "./system-menu";
import {NzDropDownADirective, NzDropDownDirective, NzDropdownMenuComponent} from "ng-zorro-antd/dropdown";
import {ModifyPasswordComponent} from "./modify-password/modify-password.component";
import {NzAvatarComponent} from "ng-zorro-antd/avatar";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzModalRef, NzModalService} from "ng-zorro-antd/modal";
import {select, Store} from "@ngrx/store";
import {selectMenus, selectRealName} from "../store/auth/auth.selector";

@Component({
    selector: 'app-layout',
    imports: [
        MatSidenavModule,
        RouterOutlet,
        MatSidenavModule,
        RouterOutlet,
        NzHeaderComponent,
        NzIconDirective,
        MatButtonModule,
        MatIconModule,
        MatMenuModule,
        MatToolbarModule,
        NzTooltipDirective,
        NzMenuModule,
        RouterLink,
        NgTemplateOutlet,
        NzContentComponent,
        NzDropDownADirective,
        NzDropDownDirective,
        NzDropdownMenuComponent,
        ModifyPasswordComponent,
        NzAvatarComponent,
        NzRowDirective,
        NzColDirective
    ],
    templateUrl: './layout.component.html',
    styleUrl: './layout.component.less',
    providers: [NzModalService]
})
export class LayoutComponent {
    /**
     * 是否打开左侧导航菜单
     */
    drawerOpened: boolean = true;

    tabs: Array<{ name: string; content: string; disabled: boolean; icon: string }> = [];

    deepColor: boolean = true;

    menus: SystemMenu[] = [];

    realName: string = '';

    // 是否全屏标志
    isFullScreen = false;

    // 修改密码弹窗显示开关
    formDialogDisplay: boolean = false;

    constructor(
        private router: Router,
        private authService: AuthService,
        private modal: NzModalService,
        private store: Store) {

        this.store.pipe(select(selectMenus)).subscribe(menus => {
            this.menus = menus;
        })

        this.store.pipe(select(selectRealName)).subscribe(realName => {
            this.realName = realName;
        })
    }


    ngOnInit(): void {
        for (let i = 0; i < 10; i++) {
            this.tabs.push({
                name: `Tab ${i}`,
                disabled: i === 4,
                content: `Content of tab ${i}`,
                icon: 'apple'
            });
        }
    }

    toggleFullscreen() {
        if (!this.isFullScreen) {
            screenfull.request();
        } else {
            screenfull.exit();
        }
        this.isFullScreen = !this.isFullScreen;
    }

    closeTab({index}: { index: number }): void {
        this.tabs.splice(index, 1);
    }

    modifyPassword() {
        this.formDialogDisplay = true;
    }

    showLogoutConfirm(): void {
        const confirmModal = this.modal.confirm({
            nzTitle: '你确定要退出吗?',
            nzOnOk: () =>
                new Promise((resolve, reject) => {

                    // 禁用取消按钮
                    confirmModal.updateConfig({
                        nzCancelDisabled: true,
                        nzClosable: false
                    });

                    // 调用登出服务
                    this.authService.logout().subscribe({
                        next: res => {

                            // 跳转回登录页
                            this.router.navigate(['login']);

                            // 成功，调用 resolve
                            resolve(true);
                        },
                        error: err => {
                            confirmModal.updateConfig({
                                nzCancelDisabled: false,
                                nzClosable: true
                            }); // 恢复取消按钮
                            // 失败时调用 reject
                            reject();
                        }
                    });
                }).catch(() => console.log('Oops errors!')) // 捕获错误
        });
    }

}
