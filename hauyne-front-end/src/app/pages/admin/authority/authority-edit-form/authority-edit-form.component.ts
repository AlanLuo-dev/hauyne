import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {filter, map, startWith, Subject, take, tap} from "rxjs";
import {switchMap} from "rxjs/operators";
import {AuthorityService} from "../authority.service";
import {DictTypeService} from "../../dictionary/dict-type/dict-type.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzModalComponent, NzModalContentDirective} from "ng-zorro-antd/modal";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {NzInputDirective} from "ng-zorro-antd/input";
import {NzTreeSelectComponent} from "ng-zorro-antd/tree-select";
import {NzOptionComponent, NzSelectComponent} from "ng-zorro-antd/select";

import {IconPickerComponent} from "../icon-picker/icon-picker.component";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";

@Component({
    selector: 'app-authority-edit-form',
    templateUrl: './authority-edit-form.component.html',
    styleUrls: ['./authority-edit-form.component.less'],
    imports: [
        NzModalComponent,
        NzModalContentDirective,
        ReactiveFormsModule,
        NzFormDirective,
        NzColDirective,
        NzFormControlComponent,
        NzFormItemComponent,
        NzFormLabelComponent,
        NzInputDirective,
        NzRowDirective,
        NzTreeSelectComponent,
        FormsModule,
        NzSelectComponent,
        NzOptionComponent,
        IconPickerComponent,
        NzButtonComponent,
        NzIconDirective
    ]
})
export class AuthorityEditFormComponent implements OnInit {

    isOkLoading: boolean = false;

    // 是否显示新增弹窗
    @Input() formDialogDisplay: boolean = false;
    @Output() formDialogDisplayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

    authorityForm: FormGroup;
    formSubmitSubject$: any;
    isCancelDisabled: boolean = false;

    // 父级菜单树：必须是数组结构
    authorityTreeNodes: any[] = [];
    @Input() authorityId?: number;

    /**
     * 子传父，触发权限资源列表刷新
     */
    @Output() triggerAuthorityListRefreshEmitter: EventEmitter<void> = new EventEmitter<void>();

    /**
     * 权限类型下拉列表
     */
    authorityTypeOptions: any[] = [];

    selectedAuthorityType: string = '';

    iconPickerDisplay: boolean = false;

    selectedParentId: number | null = null

    constructor(private fb: FormBuilder,
                private authorityService: AuthorityService,
                private dictTypeService: DictTypeService,
                private messageService: NzMessageService) {
        this.dictTypeService.loadDropdownData("authority_type")
            .subscribe(data => {
                this.authorityTypeOptions = data;
            });

        this.authorityForm = this.fb.group({
            parentId: [''],
            authorityType: ['', {
                validators: [Validators.required],
                updateOn: 'blur'
            }],
            authorityCode: ['', {
                validators: [Validators.required],
                updateOn: 'blur'
            }],
            authorityName: ['', {
                validators: [Validators.required],
                updateOn: 'blur'
            }],
            icon: ['', {updateOn: 'blur'}],
            path: ['', {updateOn: 'blur'}],
            sort: [null, {
                validators: [Validators.min(0), Validators.max(255)],
                updateOn: 'blur'
            }],
            remark: ['', {
                validators: [Validators.maxLength(100)],
                updateOn: 'blur'
            }]
        }, {updateOn: 'submit'});
    }

    ngOnInit(): void {

        // 编辑、数据回显、authorityForm对象增加id字段
        const id = this.authorityId;
        if (id) {
            this.authorityService.getOne<any>(id).subscribe(data => {
                this.authorityForm.patchValue(data);
                this.selectedAuthorityType = data.authorityType;
                this.authorityForm.addControl('id', new FormControl(data.id, Validators.required));

                this.authorityName.addAsyncValidators(this.authorityService.authorityNameAsyncValidator(id));
                this.selectedParentId = this.parentId.value;
            });
        } else {
            this.authorityName.addAsyncValidators(this.authorityService.authorityNameAsyncValidator());
        }

        this.authorityService.loadAuthorityTree().subscribe(data => {
            this.authorityTreeNodes = data;
        });

        this.formSubmitSubject$ = new Subject();
        this.formSubmitSubject$.pipe(
            tap(() =>
                Object.keys(this.authorityForm.controls).forEach(key => {

                    // 将每个FormControl 标记为脏
                    const control = this.authorityForm.get(key);
                    control?.markAsDirty();
                    control?.updateValueAndValidity({onlySelf: true});
                })
            ),
            switchMap(() =>
                this.authorityForm.statusChanges.pipe(
                    startWith(this.authorityForm.status),
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
        this.formSubmitSubject$.next();
    }

    /**
     * 提交表单
     */
    submit() {
        const observer = {
            next: (x: any) => {
                this.messageService.create('success', '操作成功');
                this.triggerAuthorityListRefreshEmitter.emit();
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
            this.authorityService.update(this.authorityForm.getRawValue()).subscribe(observer);
        } else {
            this.authorityService.save(this.authorityForm.getRawValue()).subscribe(observer);
        }
    }

    nodeSelected(event: any) {
        this.parentId.setValue(event);
    }

    onSelectChange(event: any) {
        this.authorityForm.get("authorityType")?.setValue(event);
    }

    /**
     * 清空已选择的项
     * @param event
     */
    // clearParentId(event) {
    //     this.parentId.setValue('');
    //     console.log(JSON.stringify(this.authorityForm.getRawValue(), null, 4));
    // }

    closeDialog() {
        this.formDialogDisplayChange.emit(false);
    }

    /**
     * 清空已选中的权限类型，并标记为干净
     */
    clearSelectedAuthorityType() {
        this.authorityType.setValue(null);

        // 将表单项标记为干净，以防止清空的时候就触发校验（增强用户体验）
        this.authorityType.markAsPristine()
    }

    iconPicker() {
        this.iconPickerDisplay = true;
    }

    iconSelect(icon: string) {
        this.icon.setValue(icon);
    }

    /* ------------------------ get FormControl Start --------------------------------------------------- */

    get id(): FormControl {
        return this.authorityForm.get('id') as FormControl;
    }

    get parentId(): FormControl {
        return this.authorityForm.get('parentId') as FormControl;
    }

    get authorityType(): FormControl {
        return this.authorityForm.get('authorityType') as FormControl;
    }

    get authorityCode(): FormControl {
        return this.authorityForm.get('authorityCode') as FormControl;
    }

    get authorityName(): FormControl {
        return this.authorityForm.get('authorityName') as FormControl;
    }

    get icon(): FormControl {
        return this.authorityForm.get('icon') as FormControl;
    }

    get sort(): FormControl {
        return this.authorityForm.get('sort') as FormControl;
    }

    get remark(): FormControl {
        return this.authorityForm.get('remark') as FormControl;
    }

    /* ------------------------ get FormControl End --------------------------------------------------- */
}
