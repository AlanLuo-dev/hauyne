import {NzModalComponent, NzModalContentDirective, NzModalService} from "ng-zorro-antd/modal";
import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {RoleService} from "../role.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzInputDirective} from "ng-zorro-antd/input";


@Component({
    selector: 'app-role-edit-form',
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        ReactiveFormsModule,
        NzFormDirective,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzFormControlComponent,
        NzColDirective,
        NzInputDirective,
        NzRowDirective
    ],
    templateUrl: './role-edit-form.component.html',
    styleUrl: './role-edit-form.component.less',
    providers: [
        NzModalService
    ]
})
export class RoleEditFormComponent implements OnInit, OnDestroy {

    isOkLoading: boolean = false;

    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() formTitle: string = '';
    @Input() roleId?: number;

    /**
     * 子传父，触发角色列表刷新
     */
    @Output() triggerRoleListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    roleForm: FormGroup;
    formSubmitSubject$: any;

    isCancelDisabled: boolean = false;

    constructor(private fb: FormBuilder,
                private roleService: RoleService,
                private messageService: NzMessageService) {
        this.roleForm = this.fb.group({
            roleCode: ['', {validators: [Validators.required], updateOn: 'blur'}],
            roleName: ['', {validators: [Validators.required], updateOn: 'blur'}]
        }, {updateOn: 'submit'});
    }


    ngOnInit(): void {

        // 编辑、数据回显、authorityForm对象增加id字段
        if (this.roleId) {
            this.roleService.getOne<any>(this.roleId).subscribe(data => {
                this.roleForm.patchValue(data);
                this.roleForm.addControl('id', new FormControl(data.id, Validators.required));

                this.roleCode.addAsyncValidators(this.roleService.roleCodeAsyncValidator(this.roleId));
                this.roleName.addAsyncValidators(this.roleService.roleNameAsyncValidator(this.roleId));
            });
        } else {
            this.roleCode.addAsyncValidators(this.roleService.roleCodeAsyncValidator());
            this.roleName.addAsyncValidators(this.roleService.roleNameAsyncValidator());
        }

        this.formSubmitSubject$ = new Subject();
        this.formSubmitSubject$.pipe(
            tap(() => {
                Object.keys(this.roleForm.controls).forEach(key => {
                    // 将每个FormControl 标记为脏
                    const control = this.roleForm.get(key);
                    control?.markAsDirty();
                    control?.updateValueAndValidity({onlySelf: true});
                });
            }),
            switchMap(() =>
                this.roleForm.statusChanges.pipe(
                    startWith(this.roleForm.status),
                    filter(status => status !== 'PENDING'),
                    take(1),
                    map(status => (status === 'VALID' ? 'VALID' : 'INVALID'))
                )
            )
        ).subscribe((result: 'VALID' | 'INVALID') => {
            if (result === 'VALID') {
                this.submit();
            } else {
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        });
    }

    onConfirmClick(): void {
        this.isOkLoading = true;
        this.isCancelDisabled = true;

        // 触发表单验证逻辑
        this.formSubmitSubject$.next();
    }

    /**
     * 提交表单
     */
    submit() {
        const observer = {
            next: (x: any) => {
                this.formDialogDisplayChange.emit(false);
                this.messageService.create('success', '操作成功');
                this.triggerRoleListRefreshEmitter.emit();
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            },
            error: (err: any) => {
                this.messageService.create('error', err.error.errorTips);
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        };
        const formData = this.roleForm.getRawValue();
        if (this.id) {
            this.roleService.update(formData).subscribe(observer);
        } else {
            this.roleService.save(formData).subscribe(observer);
        }
    }

    /* ------------------------ get FormControl Start --------------------------------------------------- */

    get id(): FormControl {
        return this.roleForm.get('id') as FormControl;
    }

    get roleCode(): FormControl {
        return this.roleForm.get('roleCode') as FormControl;
    }

    get roleName(): FormControl {
        return this.roleForm.get('roleName') as FormControl;
    }

    ngOnDestroy(): void {
    }


    /* ------------------------ get FormControl End --------------------------------------------------- */

    closeRoleEditForm() {
        this.formDialogDisplayChange.emit(false);
    }

}
