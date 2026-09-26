import {NzTableQueryParams} from "ng-zorro-antd/table";
import {PageQuery} from "../../../../common/page-query";

export class DictTypeQuery extends PageQuery {
    dictTypeCode: string;
    dictTypeName: string;
    enabled?: boolean | null;
    builtin?: boolean | null;
    iconType?: string | null;

    constructor(queryParams: NzTableQueryParams, dictTypeCode: string, dictTypeName: string, enabled: boolean| null,
                builtin: boolean | null, iconType?: string | null) {
        super(queryParams);
        this.dictTypeCode = dictTypeCode;
        this.dictTypeName = dictTypeName;
        this.enabled = enabled;
        this.builtin = builtin;
        this.iconType = iconType;
    }

}
