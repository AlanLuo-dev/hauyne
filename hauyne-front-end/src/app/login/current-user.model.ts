import {SystemMenu} from "../layout/system-menu";

/**
 * LocalStorage存储的 用户信息结构
 */
export interface CurrentUserModel {

    /**
     * 用户名
     */
    username: string;

    menus: SystemMenu[];
}
