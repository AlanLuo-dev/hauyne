import {Injectable} from '@angular/core';
import {map, Observable, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError} from "rxjs/operators";
import {AdminBaseService} from "../admin-base.service";
import {RoleDropdown} from "./role-dropdown";

@Injectable({
    providedIn: 'root'
})
export class RoleService extends AdminBaseService<number> {

    constructor(http: HttpClient) {
        super(http, "sys/roles");
    }



    /**
     * 角色编码异步校验【新增、编辑共用】
     * @param id 为可选参数。新增不传，编辑要传
     */
    roleCodeAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const roleCode = control.value;
            return this.http.get<any>(`${this.apiUrl}/check-role-code-unique?roleCode=${roleCode}`
                + (id ? `&excludeRoleId=${id}` : '')
            )
                .pipe(
                    map(data => (data.availability === false ? {msg: `角色编码 ${roleCode} 已存在，请勿重复添加！`} : null)),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 角色名称异步校验【新增、编辑共用】
     * @param id 为可选参数。新增不传，编辑要传
     */
    roleNameAsyncValidator(id?: number): AsyncValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const roleName = control.value;
            return this.http.get<any>(`${this.apiUrl}/check-role-name-unique?roleName=${roleName}`
                + (id ? `&excludeRoleId=${id}` : '')
            )
                .pipe(
                    map(data => (data.availability === false ? {msg: `角色名称 ${roleName} 已存在，请勿重复添加！`} : null)),
                    catchError(() => of(null))
                );
        }
    }

    /**
     * 查询角色已有的权限（只查询叶子节点，前端Ng-Zorro的复选框树会根据子节点的选中情况决定的父节点是否半选中）
     * @param roleId 角色id
     */
    authorityLeafNodeKeys(roleId: number): Observable<any> {
        return this.http.get<any>(this.apiUrl + `/${roleId}/authority-leaf-node-keys`);
    }

    jingdu(): Observable<any> {
        return this.http.get<any>(this.apiUrl + `/jingdu`);
    }

    /**
     * 修改角色的权限资源
     * @param roleId 角色id
     * @param authorityIds 权限资源id数组
     */
    updateRoleAuthorities(roleId: number, authorityIds: number[]): Observable<any> {
        return this.http.put<any>(this.apiUrl + `/${roleId}/authorities`, authorityIds);
    }

    selectDropdown(): Observable<RoleDropdown[]> {
        return this.http.get<RoleDropdown[]>(this.apiUrl + `/dropdown`);
    }
}
