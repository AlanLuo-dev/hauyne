export interface ImgCode {

    // 图形验证码（base64编码形式）
    base64ImageCode: string;

    // 服务器端生成的公钥，用于登录时加密密码，防止登录过程明文传输密码
    rsaPublicKey: string;

    captchaKey: string;
}
