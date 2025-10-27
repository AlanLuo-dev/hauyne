import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {NzModalComponent, NzModalContentDirective} from "ng-zorro-antd/modal";
import {DictItemService} from "../dict-item.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";


@Component({
    selector: 'app-dict-item-edit-form',
    templateUrl: './dict-item-edit-form.component.html',
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        FormsModule,
        NzColDirective,
        NzFormControlComponent,
        NzFormDirective,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzInputDirective,
        NzRowDirective,
        ReactiveFormsModule,
        NzSwitchComponent,
        NzTooltipDirective
    ]
})
export class DictItemEditFormComponent implements OnInit {

    isOkLoading: boolean = false;

    /**
     * 表单弹窗开关（双向绑定）
     */
    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();


    /**
     * 子传父，触发字典值列表刷新
     */
    @Output() triggerDictItemListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    // 要编辑的字典值id
    @Input() dictItemId?: number;
    @Input() dictTypeId!: number;

    dictItemForm: FormGroup;
    formSubmitSubject: any;

    isCancelDisabled: boolean = false;

    /**
     * 构造方法
     * @param formBuilder
     * @param dictItemService
     * @param messageService
     */
    constructor(private formBuilder: FormBuilder,
                private dictItemService: DictItemService,
                private messageService: NzMessageService) {

        // 构建表单验证
        this.dictItemForm = this.formBuilder.group({
            dictItemCode: ['', {validators: [Validators.required, Validators.maxLength(50)], updateOn: 'blur'}],
            dictItemName: ['', {validators: [Validators.required, Validators.maxLength(50)], updateOn: 'blur'}],
            sort: ['', {validators: [Validators.required], updateOn: 'blur'}],
            remark: ['', {validators: Validators.maxLength(60), updateOn: 'change'}]
        }, {updateOn: 'submit'});
    }

    ngOnInit(): void {
        this.dictItemForm.addControl('dictTypeId', new FormControl(this.dictTypeId, Validators.required));
        const id: number | undefined = this.dictItemId;
        if (id) {
            this.dictItemService.getOne<any>(id).subscribe(data => {
                this.dictItemForm.patchValue(data);
                this.dictItemForm.addControl('id', new FormControl(data.id, Validators.required));
                this.dictItemForm.addControl('enabled', new FormControl(data.enabled, Validators.required));
                this.dictItemCodeFormControl.addAsyncValidators(this.dictItemService.dictItemCodeAsyncValidator(this.dictTypeId, id));
                this.dictItemNameFormControl.addAsyncValidators(this.dictItemService.dictItemNameAsyncValidator(this.dictTypeId, id));
            });
        } else {
            this.dictItemCodeFormControl.addAsyncValidators(this.dictItemService.dictItemCodeAsyncValidator(this.dictTypeId));
            this.dictItemNameFormControl.addAsyncValidators(this.dictItemService.dictItemNameAsyncValidator(this.dictTypeId));
        }

        this.formSubmitSubject = new Subject();
        this.formSubmitSubject.pipe(
            tap(() =>
                Object.keys(this.dictItemForm.controls).forEach(key => {

                    // 将每个FormControl 标记为脏
                    const control = this.dictItemForm.get(key);
                    control?.markAsDirty();
                    control?.updateValueAndValidity({onlySelf: true});
                })
            ),
            switchMap(() =>
                this.dictItemForm.statusChanges.pipe(
                    startWith(this.dictItemForm.status),
                    filter(status => status !== 'PENDING'),
                    take(1),
                    map(status => (status === 'VALID' ? 'VALID' : 'INVALID'))
                )
            )
        ).subscribe((result: 'VALID' | 'INVALID') => {
            if (result === 'VALID') {
                this.submit();
            } else {
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        });
    }

    onConfirmClick(): void {
        this.isOkLoading = true;
        this.isCancelDisabled = true;

        // 触发表单验证逻辑
        this.formSubmitSubject.next();
    }

    /**
     * 提交表单
     */
    submit() {
        const observer = {
            next: (x: any) => {
                this.formDialogDisplayChange.emit(false);
                this.messageService.create('success', '操作成功');
                this.triggerDictItemListRefreshEmitter.emit();
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            },
            error: (err: any) => {
                this.messageService.create('error', err.error.msg);
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        };
        if (this.id) {
            this.dictItemService.update(this.dictItemForm.value).subscribe(observer);
        } else {
            this.dictItemService.save(this.dictItemForm.value).subscribe(observer);
        }
    }

    closeDialog() {
        this.formDialogDisplayChange.emit(false);
    }


    /* ----------------------------------- 获取响应式表单中的字段 Start ----------------------------------------------- */

    get id(): FormControl {
        return this.dictItemForm.get('id') as FormControl;
    }

    get dictItemCodeFormControl(): FormControl {
        return this.dictItemForm.get('dictItemCode') as FormControl;
    }

    get dictItemNameFormControl(): FormControl {
        return this.dictItemForm.get('dictItemName') as FormControl;
    }

    get sortFormControl(): FormControl {
        return this.dictItemForm.get('sort') as FormControl;
    }

    get enabledFormControl(): FormControl {
        return this.dictItemForm.get('enabled') as FormControl;
    }

    get remarkFormControl(): FormControl {
        return this.dictItemForm.get('remark') as FormControl;
    }

    /* ----------------------------------- 获取响应式表单中的字段 End ----------------------------------------------- */
}
