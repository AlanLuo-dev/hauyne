import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzCheckboxComponent} from "ng-zorro-antd/checkbox";
import {NzImageModule} from "ng-zorro-antd/image";
import {AuthService} from "./auth.service";
import {HttpParams} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzSpinComponent} from "ng-zorro-antd/spin";
import {Store} from "@ngrx/store";
import {AppState} from "../store";
import {authFailure, getTokenSuccess, loginStart} from "../store/auth/auth.action";
import {
    decryptAsymmetricKey,
    encryptAsymmetricKey,
    importRsaPrivateKeyBase64,
    importRsaPublicKeyBase64
} from "../util/rsa-util";
import {map, Observable} from "rxjs";
import {selectIsLoggingIn} from "../store/auth/auth.selector";
import {AsyncPipe} from "@angular/common";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
    imports: [
        NzFormModule,
        FormsModule,
        ReactiveFormsModule,
        NzInputGroupComponent,
        NzIconDirective,
        NzInputDirective,
        NzButtonComponent,
        NzCheckboxComponent,
        NzImageModule,
        NzSpinComponent,
        AsyncPipe
    ]
})
export class LoginComponent implements OnInit {
    passwordVisible: boolean = false;


    captchaKey: string = '';
    base64ImageCode: string = '';
    rsaPublicKey!: string;
    loginForm: FormGroup;

    // 是否正在登录（true=是，false=否）
    isLoggingIn$: Observable<boolean> = this.store.select(selectIsLoggingIn).pipe(map(isLoggingIn => isLoggingIn));


    /**
     * 构造函数
     * @param authService
     * @param fb
     * @param router
     * @param messageService
     * @param store
     */
    constructor(private readonly authService: AuthService,
                private readonly fb: FormBuilder,
                private readonly messageService: NzMessageService,
                private readonly store: Store<AppState>) {

        // 构建响应式表单
        this.loginForm = this.fb.nonNullable.group({
            username: ['', [Validators.required]],
            password: ['', [Validators.required]],
            captcha: ['', [Validators.required]],
            remember: [true]
        });


    }


    ngOnInit(): void {
        this.refreshImgCode();
        this.test();
    }

    async test() {
        let pwd: string = "123456";

        const publicKey: CryptoKey = await importRsaPublicKeyBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWm7+3DvP4qYr0iRFz9QPonfB5GuGuXsL5Ir50sa0EyvVM+v/0fmW36xzK3I+2v1U9Zd6B9C0IBkYJlbpwJ9P4BFNEqjutVh5/ekM5dcZFyHpJWd5cTAUEnBG5dBjPb5kzPmaHQ9xQekp8H2L7XeZ2GffnTVa7la0GnxqAMeMMiQIDAQAB");
        const encryptedData: string = await encryptAsymmetricKey(publicKey, pwd);
        console.log("加密后的数据: ", encryptedData);

        const privateKey: CryptoKey = await importRsaPrivateKeyBase64("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANabv7cO8/ipivSJEXP1A+id8Hka4a5ewvkivnSxrQTK9Uz6//R+ZbfrHMrcj7a/VT1l3oH0LQgGRgmVunAn0/gEU0SqO61WHn96Qzl1xkXIeklZ3lxMBQScEbl0GM9vmTM+ZodD3FB6SnwfYvtd5nYZ9+dNVruVrQafGoAx4wyJAgMBAAECgYAX2IJ64qx1KtGHzlskGUtv42y+6B+/ckQTzqp+6OciqzKKdTFPh2PiBbvTRqKpWaUtB06r/eerBpdtpTdsEuDYb/vQ8UqYH7hwPKZ6aVKrwM1yS90ww3GGowY33RX7kwOvI5tWo0lcJhp8GtklxVbg4XcmsM4Hu4Q487VgHkHUoQJBAOm3SzkhQIUIxWkTkje2xTgay708UqHf8KpyJRWrvk2q4Z4osQZgwiXgx292E1cxR/CtuFNtJc5NvsZ6drY05GUCQQDrEhAu9Rxe4Y7buaKb4wc6D15ZFS8eJZE3sVY9LLWxq97DhUN7ARheKe1PfGNVXLjfHHntuTFSPJnmmvsVGWtVAkAy/TRCIIkM/R7kj8qEsTFRzjbI8FIu0saUyRZiJff8xd03PjVw6McysFmSbbZGfY/uaFggi12GJtwKPUmM8vkpAkEAlH3RmUgextTnQGeQj7anHwcMS2u1Wu7SQAMW/gfbMsPmMU5iZTY45WrIzNg/i3HKGq5LW18MB+3eLi0ihJ6NDQJAQ1IbeiZ5V+TuE3TSWh+gANi41gldAjstbx4KWFvrFtfqUgLsATgIYqDy6jpZ3ecoqRB5OhH+BjJz+heULJXp2g==");
        const decryptedData = await decryptAsymmetricKey(privateKey, encryptedData);


        console.log("Decrypted Data: ", decryptedData);
    }


    /*******************************************/


    refreshImgCode() {
        this.authService.imgCode().subscribe(res => {
            this.base64ImageCode = res.base64ImageCode;
            this.rsaPublicKey = res.rsaPublicKey;
            this.captchaKey = res.captchaKey;
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

            // 对加密后的字符串进行 URL 编码
            return encodeURIComponent(encryptedData);
        } catch (err) {
            console.error("加密失败: ", err);
            return '';
        }
    }

    /**
     * 登录
     */
    async login(): Promise<void> {
        const base64ClientInfo = 'Basic ' + btoa('service-admin:123456');

        // 先加密密码（等待 Promise 完成）
        const encryptedPwd = await this.getEncryptPwd(this.loginForm.value.password);

        const param = new HttpParams()
            .set('grant_type', 'captcha') // 图形验证码模式（后台自定义授权模式）
            .set('username', this.loginForm.value.username)
            .set('password', encryptedPwd)
            .set('captcha', this.loginForm.value.captcha)
            .set('captchaKey', this.captchaKey)
            .set('scope', 'profile')
        ;
        this.store.dispatch(loginStart());
        this.authService.login(param, base64ClientInfo).subscribe({
            next: (us) => {
                this.store.dispatch(getTokenSuccess({redirect: true}));
            },
            error: (err) => {
                this.messageService.create('error', err.error.errorTips);
                this.store.dispatch(authFailure());
            },
        });
    }
}
