export class Resp<T> {

    constructor(
        // 状态码
        public code: number,
        // 错误描述
        public msg: string,
        // 数据对象
        public data: T,
        // 是否成功 (true=成功， false=失败)
        public success: boolean,
    ) {
    }
}
