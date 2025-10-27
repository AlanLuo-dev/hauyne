import {EventEmitter, Injectable, Output} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ProgressBarService {

    @Output() progressEvent = new EventEmitter<boolean>();

    constructor() {
    }

    show() {
        this.progressEvent.emit(true);
    }

    hide() {
        this.progressEvent.emit(false);
    }


}
