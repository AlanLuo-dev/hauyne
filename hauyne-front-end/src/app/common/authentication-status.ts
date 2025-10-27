export interface AuthenticationStatus {

    // 是否认证通过
    authenticated: boolean;
    principal: any; // 用户信息
}
