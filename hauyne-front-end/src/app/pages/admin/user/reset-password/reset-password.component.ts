import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormItemComponent, NzFormLabelComponent, NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../user.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzIconModule} from "ng-zorro-antd/icon";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {AuthService} from "../../../../login/auth.service";
import {encryptAsymmetricKey, importRsaPublicKeyBase64} from "../../../../util/rsa-util";

@Component({
    selector: 'app-reset-password',
    imports: [
        NzModalModule,
        NzColDirective,
        NzFormModule,
        NzFormDirective,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzInputModule,
        NzRowDirective,
        ReactiveFormsModule,
        NzIconModule
    ],
    templateUrl: './reset-password.component.html',
    styleUrl: './reset-password.component.less'
})
export class ResetPasswordComponent implements OnInit {

    /**
     * 表单弹窗开关（双向绑定）
     */
    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    /**
     * 子传父，触发角色列表刷新
     */
    @Output() triggerUserListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    @Input() userId!: number;
    @Input() username!: string;

    /**
     * 确认按钮loading状态
     */
    isOkLoading: boolean = false;

    resetPasswordForm: FormGroup;
    formSubmitSubject$: any;

    passwordVisible: boolean = false;

    rsaPublicKey!: string;

    /**
     * 构造函数
     * @param fb
     * @param dictTypeService
     * @param messageService
     */
    constructor(private fb: FormBuilder,
                private userService: UserService,
                private authService: AuthService,
                private messageService: NzMessageService) {
        // 构建表单验证
        this.resetPasswordForm = this.fb.group({
            username: [''],
            password: ['', {validators: [Validators.required], updateOn: 'blur'}]
        }, {updateOn: 'submit'});
    }

    ngOnInit(): void {
        this.resetPasswordForm.setValue({
            username: this.username,
            password: '123456'
        })


        this.formSubmitSubject$ = new Subject();
        this.formSubmitSubject$.pipe(
            tap(() => {
                Object.keys(this.resetPasswordForm.controls).forEach(key => {

                    // 将每个FormControl 标记为脏
                    const control = this.resetPasswordForm.get(key);
                    control?.markAsDirty();
                    control?.updateValueAndValidity({onlySelf: true});
                })
            }),
            switchMap(() =>
                this.resetPasswordForm.statusChanges.pipe(
                    startWith(this.resetPasswordForm.status),
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

    /**
     * 提交表单
     */
    submit() {
        if (this.resetPasswordForm.valid) {
            this.authService.getPublickKey("resetPassword").subscribe({
                next: async result => {
                    this.rsaPublicKey = result.rsaPublicKey;

                    const password = await this.getEncryptPwd(this.password.value);
                    const formData = {
                        userId: this.userId,
                        key: result.key,
                        encryptPassword: password,
                    };

                    // 执行修改密码
                    this.userService.resetPassword(formData).subscribe({
                        next: (x: any) => {
                            this.messageService.create('success', '操作成功');
                            this.triggerUserListRefreshEmitter.emit();
                            this.formDialogDisplayChange.emit(false);
                            this.isOkLoading = false;
                        },
                        error: (err: any) => {
                            this.messageService.create('error', err.error.errorTips);
                            this.isOkLoading = false;
                        }
                    });
                },
                error: (err: any) => {
                    this.messageService.create('error', err.error.errorTips);
                    this.isOkLoading = false;
                }
            });
        }
    }

    /**
     * 执行RSA公钥加密
     */
    async getEncryptPwd(password: string): Promise<string> {
        try {
            const publicKey = await importRsaPublicKeyBase64(this.rsaPublicKey);
            const encryptedData = await encryptAsymmetricKey(publicKey, password);
            console.log("加密后的数据: ", encryptedData);
            return encryptedData;
        } catch (err) {
            console.error("加密失败: ", err);
            return '';
        }
    }


    closeDialog() {
        this.formDialogDisplayChange.emit(false);
    }

    onConfirmClick(): void {
        this.isOkLoading = true;

        // 触发表单验证逻辑
        this.formSubmitSubject$.next();
    }


    get password(): FormControl {
        return this.resetPasswordForm.get('password') as FormControl;
    }
}
