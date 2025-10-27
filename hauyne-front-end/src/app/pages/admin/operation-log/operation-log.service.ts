import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "../../../common/base.service";

@Injectable({
    providedIn: 'root'
})
export class OperationLogService extends BaseService<number>{

    constructor(http: HttpClient) {
        super(http, "tracelog/operation-logs");
    }
}
