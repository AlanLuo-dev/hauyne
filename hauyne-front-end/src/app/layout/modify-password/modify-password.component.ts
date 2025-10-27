import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {filter, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {NzMessageService} from "ng-zorro-antd/message";
import {AuthService} from "../../login/auth.service";
import {NzModalComponent, NzModalContentDirective, NzModalService} from "ng-zorro-antd/modal";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzInputDirective, NzInputGroupComponent, NzInputGroupWhitSuffixOrPrefixDirective} from "ng-zorro-antd/input";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {encryptAsymmetricKey, importRsaPublicKeyBase64} from "../../util/rsa-util";


@Component({
    selector: 'app-modify-password',
    templateUrl: './modify-password.component.html',
    styleUrls: ['./modify-password.component.less'],
    imports: [
        NzModalComponent,
        NzFormDirective,
        NzModalContentDirective,
        NzFormItemComponent,
        ReactiveFormsModule,
        NzFormLabelComponent,
        NzColDirective,
        NzFormControlComponent,
        NzInputDirective,
        NzRowDirective,
        NzInputGroupWhitSuffixOrPrefixDirective,
        NzInputGroupComponent,
        FormsModule,
        NzIconDirective
    ],
    providers: [NzModalService]
})
export class ModifyPasswordComponent implements OnInit {

    /*~ ------------------------ 是否显示弹窗，双向绑定 start ------------------------ */
    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();


    modifyPasswordForm: FormGroup;
    formSubmitSubject: any;
    oldPwdVisible = false;
    newPwdVisible = false;
    confirmPwdVisible = false;

    rsaPublicKey!: string;

    isOkLoading: boolean = false;
    isCancelDisabled: boolean = false;

    constructor(private formBuilder: FormBuilder,
                private messageService: NzMessageService,
                private authService: AuthService) {

        // 构建表单验证
        this.modifyPasswordForm = this.formBuilder.group({
            oldPassword: ['', {validators: [Validators.required], updateOn: 'blur'}],
            newPassword: ['', {validators: [Validators.required], updateOn: 'blur'}],
            confirmPassword: ['', {validators: [Validators.required], updateOn: 'blur'}]
        }, {updateOn: 'submit'});

    }

    ngOnInit(): void {
        this.formSubmitSubject = new Subject();
        this.formSubmitSubject.pipe(
            tap(() =>
                    Object.keys(this.modifyPasswordForm.controls).forEach(key => {

                        // 将每个FormControl 标记为脏
                        const control = this.modifyPasswordForm.get(key);
                        control?.markAsDirty();
                        control?.updateValueAndValidity({onlySelf: true});
                    })
            ),
            switchMap(() =>
                this.modifyPasswordForm.statusChanges.pipe(
                    startWith(this.modifyPasswordForm.status),
                    filter(status => status !== 'PENDING'),
                    take(1)
                )
            ),
            filter(status => status === 'VALID')
        ).subscribe((validationSuccessful: any) => this.submit());
    }

    /**
     * 提交表单
     */
    submit() {
        if (this.modifyPasswordForm.valid) {
            this.authService.getPublickKey("modifyPassword").subscribe({
                next: async result => {
                    this.rsaPublicKey = result.rsaPublicKey;

                    const oldPassword = await this.getEncryptPwd(this.oldPasswordFormControl.value);
                    const newPassword = await this.getEncryptPwd(this.newPasswordFormControl.value);
                    const confirmPassword = await this.getEncryptPwd(this.confirmPasswordFormControl.value);
                    const formData = {
                        key: result.key,
                        oldPassword: oldPassword,
                        newPassword: newPassword,
                        confirmPassword: confirmPassword
                    };

                    // 执行修改密码
                    this.authService.modifyPassword(formData).subscribe({
                        next: (x: any) => {
                            this.messageService.create('success', '操作成功');
                            this.formDialogDisplayChange.emit(false);
                            this.resetLoadingState();
                        },
                        error: (err: any) => {
                            this.messageService.create('error', err.error.errorTips);
                            this.resetLoadingState();
                        }
                    });
                },
                error: (err: any) => {
                    this.messageService.create('error', err.error.errorTips);
                    this.resetLoadingState();
                }
            });
        }
    }

    resetLoadingState() {
        this.isOkLoading = false;
        this.isCancelDisabled = false;
    }

    onConfirmClick(): void {
        this.isOkLoading = true;
        this.isCancelDisabled = true;

        // 触发表单验证逻辑
        this.formSubmitSubject.next();
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


    /* ----------------------------------- 获取响应式表单中的字段 Start ----------------------------------------------- */

    get oldPasswordFormControl() {
        return this.modifyPasswordForm.get('oldPassword') as FormControl;
    }

    get newPasswordFormControl() {
        return this.modifyPasswordForm.get('newPassword') as FormControl;
    }

    get confirmPasswordFormControl() {
        return this.modifyPasswordForm.get('confirmPassword') as FormControl;
    }

    /* ----------------------------------- 获取响应式表单中的字段 End ----------------------------------------------- */
}
