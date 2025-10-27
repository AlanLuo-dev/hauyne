import {NzTableQueryParams} from "ng-zorro-antd/table";
import {PageQuery} from "../../../common/page-query";

/**
 * 登录日志 查询条件
 */
export class LoginHistoryQuery extends PageQuery {

    type: number | null;

    /**
     * 用户登录名
     */
    username: string;


    constructor(queryParams: NzTableQueryParams, type: number| null, username: string) {
        super(queryParams);
        this.type = type;
        this.username = username;
    }
}
