import {NzTableQueryParams} from "ng-zorro-antd/table";
import {PageQuery} from "../../../common/page-query";

export class OperationLogQuery extends PageQuery {
    type: string;
    bizId: number;

    constructor(queryParams: NzTableQueryParams, type: string, bizId: number) {
        super(queryParams);
        this.type = type;
        this.bizId = bizId;
    }

}
