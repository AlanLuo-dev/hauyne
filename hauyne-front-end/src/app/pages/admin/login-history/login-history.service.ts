import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AdminBaseService} from "../admin-base.service";

@Injectable({
    providedIn: 'root'
})
export class LoginHistoryService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, 'sys/login-histories');
    }


}
