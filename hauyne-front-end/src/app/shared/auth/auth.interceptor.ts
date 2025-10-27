// import {
//     HttpEvent,
//     HttpHandler,
//     HttpInterceptor,
//     HttpRequest, HttpResponse,
// } from '@angular/common/http';
// import {Injectable} from '@angular/core';
// import {Router} from '@angular/router';
// // import {catchError, Observable, switchMap, throwError} from 'rxjs';
// import {finalize, Observable, share, tap, throwError} from "rxjs";
// import {catchError, switchMap} from "rxjs/operators";
// import {ProgressBarService} from "./progress-bar.service";
// import {AuthService} from "../../login/auth.service";
//
// /**
//  * 认证 拦截器
//  */
// @Injectable({
//     providedIn: 'root',  // 这行代码确保 AuthGuard 在应用程序级别被提供
// })
// export class AuthInterceptor implements HttpInterceptor {
//
//     /** 令牌刷新器 */
//     private refresher!: Observable<HttpResponse<any>> | null;
//
//     /**
//      * 依赖注入
//      * @param authService 认证 Service
//      * @param route 路由
//      * @param progressBarService
//      */
//     constructor(private authService: AuthService,
//                 private route: Router,
//                 private progressBarService: ProgressBarService) {
//     }
//
//     intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//         return next.handle(req).pipe(
//             tap(() => {
//                 // 显示进度条
//                 this.progressBarService.show();
//             }),
//             finalize(() => {
//                 // 隐藏进度条
//                 this.progressBarService.hide();
//             }),
//             catchError((error) => {
//
//                 // 当访问令牌（access-token）失效、过期的时候，使用刷新令牌(refresh-token)去获取新的访问令牌（access-token），用户无感知
//                 if (error.status === 401) {
//
//                     return this.handleAccessToken401Error(req, next, error);
//                 } else if (error.status === 504) {
//                     alert('网关超时 ' + error.statusText);
//                     localStorage.removeItem('user-profile');
//                     this.authService.clearUserProfile();
//
//                     // 跳转回登录页
//                     this.route.navigate(['login']);
//
//                     return throwError(() => error);
//                 } else if (error.status === 400) {
//                     return throwError(() => error);
//                 } else {
//                     return throwError(() => error);
//                 }
//             })
//         );
//     }
//
//     private handleAccessToken401Error(
//         request: HttpRequest<any>,
//         next: HttpHandler,
//         error: any
//     ) {
//         // let grant_type: string = request.body ? request.body.get('grant_type') : null;
//         // 只要不是【刷新令牌请求】报401.都执行刷新令牌请求
//         if (request.url !== '/api/uaa/oauth/token') {
//             if (this.refresher) {
//                 return this.waitRefresh(request, next);
//             }
//             this.refreshToken();
//             return this.waitRefresh(request, next);
//
//             // return throwError(() => error);
//         } else {
//             return next.handle(request);
//         }
//     }
//
//     private refreshToken() {
//         this.refresher = this.authService.refreshCookie().pipe(
//             share(),
//             tap(() => {
//             }),
//             catchError((error) => {
//
//                 // 如果刷新访问令牌不成功，则删除LocalStorage中的用户信息
//                 localStorage.removeItem('user-profile');
//                 this.authService.clearUserProfile();
//
//                 // 跳转回登录页
//                 this.route.navigate(['login']);
//                 return throwError(() => error);
//             }),
//             finalize(() => this.refresher = null)
//         );
//     }
//
//     /**
//      * 等待令牌续签后再发送请求
//      * @param request
//      * @param next
//      */
//     private waitRefresh(request: HttpRequest<unknown>, next: HttpHandler) {
//         // @ts-ignore
//         return this.refresher.pipe(
//             switchMap(() => {
//                 return next.handle(request);
//             })
//         );
//     }
// }
