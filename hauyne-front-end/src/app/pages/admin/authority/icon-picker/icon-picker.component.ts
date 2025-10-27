import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {NzModalComponent, NzModalContentDirective} from "ng-zorro-antd/modal";
import {NZ_ICONS, NzIconDirective} from "ng-zorro-antd/icon";

import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-icon-picker',
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        NzIconDirective,
        NzTooltipDirective,
        NzInputGroupComponent,
        NzInputDirective,
        FormsModule
    ],
    templateUrl: './icon-picker.component.html',
    styleUrl: './icon-picker.component.less'
})
export class IconPickerComponent implements OnInit {


    @Input() iconPickerDisplay: boolean = false;
    @Output() iconPickerDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() selectedIcon: string = "";
    @Output() selectedIconChange: EventEmitter<string> = new EventEmitter<string>();

    readonly outlineIcons: string[] = inject(NZ_ICONS)
        .filter(item => item.theme == 'outline')
        .map(item => item.name);

    icons: string[] = this.outlineIcons;

    loading: boolean = false

    searchKey: string = "";

    ngOnInit(): void {
        this.loading = true;
        setTimeout(() => {

            this.loading = false;
        }, 1);
    }

    ok() {
        this.selectedIconChange.emit(this.selectedIcon);
        this.iconPickerDisplayChange.emit(false);
    }

    clickIcon(icon: string): void {
        this.selectedIcon = icon;
    }

    // 搜索
    search(): void {
        if (this.searchKey) {
            this.icons = this.icons
                .filter(
                    icon => icon.toLowerCase().includes(this.searchKey.toLowerCase())
                );
        } else {
            this.icons = this.outlineIcons;
        }
    }
}
