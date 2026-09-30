import {PageQuery2} from "../../../../common/page-query-2";

export class DictTypeQuery extends PageQuery2 {
    dictTypeCode: string | null = null;
    dictTypeName: string | null = null;
    enabled?: boolean | null;
    builtin?: boolean | null;
    iconType?: string | null;
}
