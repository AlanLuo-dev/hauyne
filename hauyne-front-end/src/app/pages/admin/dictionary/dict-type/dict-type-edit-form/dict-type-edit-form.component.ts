import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {DictTypeService} from "../dict-type.service";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzModalComponent, NzModalContentDirective} from "ng-zorro-antd/modal";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzSwitchComponent} from "ng-zorro-antd/switch";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";

@Component({
    selector: 'app-dict-type-edit-form',
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        ReactiveFormsModule,
        NzFormDirective,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzFormControlComponent,
        NzColDirective,
        NzInputDirective,
        NzRowDirective,
        NzSwitchComponent,
        NzTooltipDirective,
        FormsModule
    ],
    templateUrl: './dict-type-edit-form.component.html'
})
export class DictTypeEditFormComponent implements OnInit {

    isOkLoading: boolean = false;

    /**
     * 表单弹窗开关（双向绑定）
     */
    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input() formTitle: string = '';

    dictTypeForm: FormGroup;
    formSubmitSubject: any;

    isCancelDisabled: boolean = false;


    @Input() dictTypeId?: number;

    /**
     * 子传父，触发字典类型列表刷新
     */
    @Output() triggerDictTypeListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    /**
     * 构造函数
     * @param fb
     * @param dictTypeService
     * @param messageService
     */
    constructor(private fb: FormBuilder,
                private dictTypeService: DictTypeService,
                private messageService: NzMessageService) {
        // 构建表单验证
        this.dictTypeForm = this.fb.group({
            dictTypeCode: ['', {
                validators: [Validators.required],
                updateOn: 'blur'
            }],
            dictTypeName: ['', {
                validators: [Validators.required],
                updateOn: 'blur'
            }],
            enabled: [false],
            description: ['', {validators: Validators.maxLength(50), updateOn: 'change'}]
        }, {updateOn: 'submit'});
    }

    ngOnInit(): void {
        const id = this.dictTypeId;
        if (id) {
            this.dictTypeService.getOne<any>(id).subscribe(data => {
                this.dictTypeForm.patchValue(data);
                this.dictTypeForm.addControl('id', new FormControl(data.id, Validators.required));
                this.dictTypeForm.addControl('enabled', new FormControl(data.enabled, Validators.required));
                this.dictTypeCode.addAsyncValidators(this.dictTypeService.dictTypeCodeAsyncValidator(id));
                this.dictTypeName.addAsyncValidators(this.dictTypeService.dictTypeNameAsyncValidator(id));
            });
        } else {
            this.dictTypeCode.addAsyncValidators(this.dictTypeService.dictTypeCodeAsyncValidator());
            this.dictTypeName.addAsyncValidators(this.dictTypeService.dictTypeNameAsyncValidator());
        }

        this.formSubmitSubject = new Subject();
        this.formSubmitSubject.pipe(
            tap(() =>
                Object.keys(this.dictTypeForm.controls).forEach(key => {

                    // 将每个FormControl 标记为脏
                    const control = this.dictTypeForm.get(key);
                    control?.markAsDirty();
                    control?.updateValueAndValidity({onlySelf: true});
                })
            ),
            switchMap(() =>
                this.dictTypeForm.statusChanges.pipe(
                    startWith(this.dictTypeForm.status),
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
     * 新增字典类型
     */
    submit() {
        const observer = {
            next: (x: any) => {
                this.formDialogDisplay = false;
                this.messageService.create('success', '操作成功');
                this.triggerDictTypeListRefreshEmitter.emit();
                this.formDialogDisplayChange.emit(false);
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            },
            error: (err: any) => {
                this.messageService.create('error', err.error.errorTips);
                this.isOkLoading = false;
                this.isCancelDisabled = false;
            }
        };
        if (this.id) {
            this.dictTypeService.update(this.dictTypeForm.value).subscribe(observer);
        } else {
            this.dictTypeService.save(this.dictTypeForm.value).subscribe(observer);
        }
    }

    closeDialog() {
        this.formDialogDisplayChange.emit(false);
    }

    statusChange() {
        this.enabled.setValue(!this.enabled.value);
    }

    // enabledChange(event) {
    //     this.enabledFormControl.patchValue(event.checked);
    // }

    /* ----------------------------------- 获取响应式表单中的字段 Start ----------------------------------------------- */

    get id(): FormControl {
        return this.dictTypeForm.get('id') as FormControl;
    }

    get dictTypeCode(): FormControl {
        return this.dictTypeForm.get('dictTypeCode') as FormControl;
    }

    get dictTypeName(): FormControl {
        return this.dictTypeForm.get('dictTypeName') as FormControl;
    }

    get enabled(): FormControl {
        return this.dictTypeForm.get('enabled') as FormControl;
    }

    get descriptionFormControl(): FormControl {
        return this.dictTypeForm.get('description') as FormControl;
    }

    /* ----------------------------------- 获取响应式表单中的字段 End ----------------------------------------------- */
}
