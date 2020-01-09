import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataFields } from 'app/shared/model/data-fields.model';
import { DataFieldsService } from './data-fields.service';
import { DataFieldsComponent } from './data-fields.component';
import { DataFieldsDetailComponent } from './data-fields-detail.component';
import { DataFieldsUpdateComponent } from './data-fields-update.component';
import { DataFieldsDeletePopupComponent } from './data-fields-delete-dialog.component';
import { IDataFields } from 'app/shared/model/data-fields.model';

@Injectable({ providedIn: 'root' })
export class DataFieldsResolve implements Resolve<IDataFields> {
  constructor(private service: DataFieldsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataFields> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataFields>) => response.ok),
        map((dataFields: HttpResponse<DataFields>) => dataFields.body)
      );
    }
    return of(new DataFields());
  }
}

export const dataFieldsRoute: Routes = [
  {
    path: '',
    component: DataFieldsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'platformApp.dataFields.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataFieldsDetailComponent,
    resolve: {
      dataFields: DataFieldsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataFields.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataFieldsUpdateComponent,
    resolve: {
      dataFields: DataFieldsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataFields.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataFieldsUpdateComponent,
    resolve: {
      dataFields: DataFieldsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataFields.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataFieldsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataFieldsDeletePopupComponent,
    resolve: {
      dataFields: DataFieldsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataFields.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
