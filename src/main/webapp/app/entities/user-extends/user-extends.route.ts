import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserExtends } from 'app/shared/model/user-extends.model';
import { UserExtendsService } from './user-extends.service';
import { UserExtendsComponent } from './user-extends.component';
import { UserExtendsDetailComponent } from './user-extends-detail.component';
import { UserExtendsUpdateComponent } from './user-extends-update.component';
import { UserExtendsDeletePopupComponent } from './user-extends-delete-dialog.component';
import { IUserExtends } from 'app/shared/model/user-extends.model';

@Injectable({ providedIn: 'root' })
export class UserExtendsResolve implements Resolve<IUserExtends> {
  constructor(private service: UserExtendsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserExtends> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserExtends>) => response.ok),
        map((userExtends: HttpResponse<UserExtends>) => userExtends.body)
      );
    }
    return of(new UserExtends());
  }
}

export const userExtendsRoute: Routes = [
  {
    path: '',
    component: UserExtendsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'platformApp.userExtends.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserExtendsDetailComponent,
    resolve: {
      userExtends: UserExtendsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.userExtends.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserExtendsUpdateComponent,
    resolve: {
      userExtends: UserExtendsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.userExtends.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserExtendsUpdateComponent,
    resolve: {
      userExtends: UserExtendsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.userExtends.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userExtendsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserExtendsDeletePopupComponent,
    resolve: {
      userExtends: UserExtendsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.userExtends.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
