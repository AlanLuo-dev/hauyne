import {NzTableQueryParams} from "ng-zorro-antd/table";
import {PageQuery} from "../../../common/page-query";
import {format} from "date-fns";

/**
 * 登录日志 查询条件
 */
export class LoginHistoryQuery extends PageQuery {

    type: number | null = null;

    /**
     * 用户登录名
     */
    username: string | null = null;
    startTime: string | null = null;
    endTime: string | null = null;


    constructor(queryParams: NzTableQueryParams, type: number | null, username: string, startTime: Date | null, endTime: Date | null) {
        super(queryParams);
        this.type = type;
        this.username = username;

        // 使用 date-fns 的 format 函数进行格式化
        if (startTime) {
            this.startTime = format(startTime, 'yyyy-MM-dd HH:mm:ss');
        }
        if (endTime) {
            this.endTime = format(endTime, 'yyyy-MM-dd HH:mm:ss');
        }
    }
}
