import {Directive, ElementRef, Input, OnChanges, SimpleChanges, TemplateRef, ViewContainerRef} from '@angular/core';
import {select, Store} from "@ngrx/store";
import {selectAuthorities, selectIsAuthenticated} from "../store/auth/auth.selector";

/**
 * 权限控制指令：根据用户拥有的权限决定是否渲染元素。
 * 使用方式：[appAuthority]="权限标识"
 * 如果用户权限列表中包含指定标识，则渲染元素，否则移除元素。
 * 权限列表通过全局状态管理（NgRx Store）获取。
 */
@Directive({
    selector: '[appAuthority]',
    standalone: true
})
export class AuthorityDirective implements OnChanges {

    @Input("appAuthority") needAuthority: string = '';

    constructor(private element: ElementRef,
                private templateRef: TemplateRef<any>,
                private viewContainer: ViewContainerRef,
                private store: Store) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.store.pipe(select(selectAuthorities))
            .subscribe(authorities => {
                    if (authorities.includes(this.needAuthority)) {
                        this.viewContainer.createEmbeddedView(this.templateRef);
                    } else {
                        this.viewContainer.clear();
                    }
                }
            )
    }

}
