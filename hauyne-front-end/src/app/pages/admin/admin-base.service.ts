import {HttpClient} from "@angular/common/http";
import {BaseService} from "../../common/base.service";

export class AdminBaseService<ID> extends BaseService<ID> {

    /**
     * 构造函数
     * @param http HttpClient对象
     * @param endpoint 具体业务的API
     */
    constructor(http: HttpClient,
                endpoint: string) {
        super(http, `admin/${endpoint}`);
    }
}
