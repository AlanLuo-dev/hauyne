import {PageQuery2} from "../../../../common/page-query-2";

export class UserQuery extends PageQuery2 {
    username: string | null = null;
    roleCode: string | null = null;
    nickname: string | null = null;
    realName: string | null = null;
    gender: number | null = null;
    phone: string | null = null;
    enabled: boolean | null = null;
    builtin: boolean | null = null;
}
