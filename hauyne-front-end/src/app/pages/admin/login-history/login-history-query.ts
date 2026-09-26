import {format} from "date-fns";
import {PageQuery2} from "../../../common/page-query-2";

/**
 * 登录日志 查询条件
 */
export class LoginHistoryQuery extends PageQuery2 {

    // 模板直接绑定的筛选条件
    type: number | null = null;
    username: string = '';
    startTime: Date | null = null;
    endTime: Date | null = null;

    /**
     * 重置筛选条件（保留分页参数）
     */
    resetFilter(): void {
        this.type = null;
        this.username = '';
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * 重写 HttpParams 序列化逻辑，在此处自动对 Date 进行格式化
     */
    override toHttpParams(): { [key: string]: any } {
        const params = super.toHttpParams();

        // 格式化日期为后端的 yyyy-MM-dd HH:mm:ss 字符串
        if (this.startTime) {
            params['startTime'] = format(this.startTime, 'yyyy-MM-dd HH:mm:ss');
        }
        if (this.endTime) {
            params['endTime'] = format(this.endTime, 'yyyy-MM-dd HH:mm:ss');
        }

        return params;
    }
}
