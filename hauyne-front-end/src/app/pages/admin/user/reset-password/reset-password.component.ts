import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormDirective, NzFormItemComponent, NzFormLabelComponent, NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../user.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzIconModule} from "ng-zorro-antd/icon";
import {concatMap, finalize, from} from "rxjs";
import {AuthService} from "../../../../login/auth.service";
import {encryptAsymmetricKey, importRsaPublicKeyBase64} from "../../../../util/rsa-util";
import {NzButtonModule} from "ng-zorro-antd/button";

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
        NzIconModule,
        NzButtonModule
    ],
    templateUrl: './reset-password.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
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
    isSubmitting: boolean = false;

    resetPasswordForm: FormGroup;
    formSubmitSubject$: any;

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
    }

    /**
     * 提交表单
     */
    submit() {

        // 1. 统一开启 Loading 并禁用表单
        this.isSubmitting = true;
        this.resetPasswordForm.disable();

        // 2. 链式调用：获取公钥 -> 加密密码 -> 提交修改
        this.authService.getPublickKey("resetPassword").pipe(
            concatMap(result => {
                this.rsaPublicKey = result.rsaPublicKey;
                // 将 Promise (getEncryptPwd) 转为 Observable 链入流中
                return from(this.getEncryptPwd(this.password.value))
                    .pipe(
                        concatMap(password => {
                            const formData = {
                                userId: this.userId,
                                key: result.key,
                                encryptPassword: password,
                            };
                            return this.userService.resetPassword(formData);
                        })
                    );
            }),
            // 3. 全局统一收尾：无论公钥接口失败、加密失败还是提交失败，都只在此处恢复一次状态
            finalize(() => {
                this.isSubmitting = false;
                this.resetPasswordForm.enable();
            })
        ).subscribe({
            next: () => {
                this.messageService.create('success', '操作成功');
                this.triggerUserListRefreshEmitter.emit();
                this.formDialogDisplayChange.emit(false);
            },
            error: (err: any) => {
                this.messageService.create('error', err.error?.errorTips || '密码修改失败');
            }
        });
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

    /**
     * 点击确定按钮
     */
    async onConfirmClick(): Promise<void> {
        // 1. 标记所有控件为 dirty 以触发错误提示显示
        Object.values(this.resetPasswordForm.controls)
            .forEach(control => {
                control.markAsDirty();
                control.updateValueAndValidity({onlySelf: true});
            });

        // 2. 如果表单还在异步验证中 (PENDING)，等待验证完成
        if (this.resetPasswordForm.pending) {
            this.isSubmitting = true;
            await new Promise<void>(resolve => {
                const sub = this.resetPasswordForm.statusChanges.subscribe(status => {
                    if (status !== 'PENDING') {
                        sub.unsubscribe();
                        resolve();
                    }
                });
            });
        }

        // 3. 校验不通过，直接打断
        if (this.resetPasswordForm.invalid) {
            this.isSubmitting = false;
            return;
        }

        // 4. 校验通过，执行提交
        this.submit();
    }


    get password(): FormControl {
        return this.resetPasswordForm.get('password') as FormControl;
    }
}
