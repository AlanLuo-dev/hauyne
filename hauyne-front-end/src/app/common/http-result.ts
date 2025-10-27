export interface HttpResult<T> {

    // 状态码
    code: number;

    // 错误描述
    msg: string;

    // 数据对象
    data: T;

    // 是否成功 (true=成功， false=失败)
    success: boolean;
}
