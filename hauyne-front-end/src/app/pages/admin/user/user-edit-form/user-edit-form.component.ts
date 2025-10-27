import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective, NzInputModule} from "ng-zorro-antd/input";
import {FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzOptionComponent, NzSelectComponent} from "ng-zorro-antd/select";
import {RoleService} from "../../role/role.service";
import {RoleDropdown} from "../../role/role-dropdown";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {UserService} from "../user.service";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
    selector: 'app-user-edit-form',
    imports: [
        NzDrawerModule,
        NzButtonModule,
        NzToolTipModule,
        NzColDirective,
        NzFormControlComponent,
        NzFormDirective,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzInputDirective,
        NzRowDirective,
        ReactiveFormsModule,
        NzInputModule,
        NzIconModule,
        NzSwitchComponent,
        NzOptionComponent,
        NzSelectComponent
    ],
    templateUrl: './user-edit-form.component.html',
    styleUrl: './user-edit-form.component.less'
})
export class UserEditFormComponent implements OnInit {

    // 是否显示授权弹窗
    @Input() userFormDialogDisplay: boolean = false;
    @Output() userFormDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    /**
     * 子传父，触发角色列表刷新
     */
    @Output() triggerUserListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    @Input() userId?: number;
    @Input() formTitle!: string;

    isOkLoading: boolean = false;

    userForm: FormGroup;
    formSubmitSubject$: any;

    passwordVisible: boolean = false;

    roles: RoleDropdown[] = [];

    constructor(private readonly formBuilder: FormBuilder,
                private readonly roleService: RoleService,
                private readonly userService: UserService,
                private readonly messageService: NzMessageService) {

        this.userForm = this.formBuilder.group({
            username: ['', {validators: [Validators.required], updateOn: 'blur'}],
            password: ['', {validators: [Validators.required], updateOn: 'blur'}],
            roleIds: [[], {validators: [Validators.required], updateOn: 'blur'}],
            enabled: [true],

            profile: this.formBuilder.group({
                realName: ['', {validators: [Validators.required], updateOn: 'blur'}],
                nickname: [null, {updateOn: 'blur'}],
                avatar: [null, {updateOn: 'blur'}],
                gender: [null, {updateOn: 'blur'}],
                birthday: [null, {updateOn: 'blur'}],
                email: [null, {updateOn: 'blur'}],
                phone: [null, {updateOn: 'blur'}],
                position: [null, {updateOn: 'blur'}],
                remark: [null, {updateOn: 'blur'}]
            })
        }, {updateOn: 'submit'});

        this.roleService.selectDropdown().subscribe(
            (value) => {
                this.roles = value;
            }
        )
    }

    ngOnInit(): void {
        // 编辑、数据回显、authorityForm对象增加id字段
        if (this.userId) {
            this.userService.getOne<any>(this.userId).subscribe(data => {
                this.userForm.patchValue(data);
                this.userForm.addControl('id', new FormControl(data.id, Validators.required));

                // 移除 password 控件
                this.userForm.removeControl('password');

                // this.roleCode.addAsyncValidators(this.roleService.roleCodeAsyncValidator(this.roleId));
                // this.roleName.addAsyncValidators(this.roleService.roleNameAsyncValidator(this.roleId));
            });
        } else {
            // this.roleCode.addAsyncValidators(this.roleService.roleCodeAsyncValidator());
            // this.roleName.addAsyncValidators(this.roleService.roleNameAsyncValidator());
        }

        this.formSubmitSubject$ = new Subject();
        this.formSubmitSubject$.pipe(
            tap(() => {
                this.markFormGroupDirty(this.userForm);
            }),
            switchMap(() =>
                this.userForm.statusChanges.pipe(
                    startWith(this.userForm.status),
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
            }
        });
    }

    private markFormGroupDirty(formGroup: FormGroup | FormArray): void {
        Object.values(formGroup.controls)
            .forEach(control => {
                if (control instanceof FormControl) {
                    control.markAsDirty();
                    control.updateValueAndValidity({onlySelf: true});
                } else if (control instanceof FormGroup || control instanceof FormArray) {
                    this.markFormGroupDirty(control);
                }
            });
    }


    onConfirmClick(): void {
        this.isOkLoading = true;

        // 触发表单验证逻辑
        this.formSubmitSubject$.next();
    }

    /**
     * 提交表单
     */
    submit() {
        const observer = {
            next: (x: any) => {
                this.userFormDialogDisplayChange.emit(false);
                this.messageService.create('success', '操作成功');
                this.triggerUserListRefreshEmitter.emit();
                this.isOkLoading = false;
            },
            error: (err: any) => {
                this.messageService.create('error', err.error.errorTips);
                this.isOkLoading = false;
            }
        };
        const formData = this.userForm.getRawValue();
        if (this.userId) {
            this.userService.update(formData).subscribe(observer);
        } else {
            this.userService.save(formData).subscribe(observer);
        }
    }

    statusChange() {
        this.enabled.setValue(!this.enabled.value);
    }

    /**
     * 关闭弹窗
     */
    handleCancel(): void {
        this.userFormDialogDisplayChange.emit(false);
    }

    handleOk(): void {
        this.userForm.markAllAsTouched();
        if (this.userForm.invalid) {
            return;
        }
    }

    get username(): FormControl {
        return this.userForm.get('username') as FormControl;
    }

    get password(): FormControl {
        return this.userForm.get('password') as FormControl;
    }

    get role(): FormControl {
        return this.userForm.get('roleIds') as FormControl;
    }

    get enabled(): FormControl {
        return this.userForm.get('enabled') as FormControl;
    }

    get realName(): FormControl {
        return this.userForm.get('profile.realName') as FormControl;
    }

    get nickname(): FormControl {
        return this.userForm.get('profile.nickname') as FormControl;
    }

    get avatar(): FormControl {
        return this.userForm.get('profile.avatar') as FormControl;
    }

    get gender(): FormControl {
        return this.userForm.get('profile.gender') as FormControl;
    }

    get birthday(): FormControl {
        return this.userForm.get('profile.birthday') as FormControl;
    }

    get email(): FormControl {
        return this.userForm.get('profile.email') as FormControl;
    }

    get phone(): FormControl {
        return this.userForm.get('profile.phone') as FormControl;
    }

    get position(): FormControl {
        return this.userForm.get('profile.position') as FormControl;
    }

    get remark(): FormControl {
        return this.userForm.get('profile.remark') as FormControl;
    }

}
