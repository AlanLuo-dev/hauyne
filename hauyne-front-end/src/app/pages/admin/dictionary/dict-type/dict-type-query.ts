import {NzTableQueryParams} from "ng-zorro-antd/table";
import {PageQuery} from "../../../../common/page-query";

export class DictTypeQuery extends PageQuery {
    dictTypeCode: string;
    dictTypeName: string;
    enabled?: boolean | null;


    constructor(queryParams: NzTableQueryParams, dictTypeCode: string, dictTypeName: string, enabled: boolean| null) {
        super(queryParams);
        this.dictTypeCode = dictTypeCode;
        this.dictTypeName = dictTypeName;
        this.enabled = enabled;
    }

}
