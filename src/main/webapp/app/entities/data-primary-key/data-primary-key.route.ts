import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataPrimaryKey } from 'app/shared/model/data-primary-key.model';
import { DataPrimaryKeyService } from './data-primary-key.service';
import { DataPrimaryKeyComponent } from './data-primary-key.component';
import { DataPrimaryKeyDetailComponent } from './data-primary-key-detail.component';
import { DataPrimaryKeyUpdateComponent } from './data-primary-key-update.component';
import { DataPrimaryKeyDeletePopupComponent } from './data-primary-key-delete-dialog.component';
import { IDataPrimaryKey } from 'app/shared/model/data-primary-key.model';

@Injectable({ providedIn: 'root' })
export class DataPrimaryKeyResolve implements Resolve<IDataPrimaryKey> {
  constructor(private service: DataPrimaryKeyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataPrimaryKey> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataPrimaryKey>) => response.ok),
        map((dataPrimaryKey: HttpResponse<DataPrimaryKey>) => dataPrimaryKey.body)
      );
    }
    return of(new DataPrimaryKey());
  }
}

export const dataPrimaryKeyRoute: Routes = [
  {
    path: '',
    component: DataPrimaryKeyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataPrimaryKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataPrimaryKeyDetailComponent,
    resolve: {
      dataPrimaryKey: DataPrimaryKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataPrimaryKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataPrimaryKeyUpdateComponent,
    resolve: {
      dataPrimaryKey: DataPrimaryKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataPrimaryKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataPrimaryKeyUpdateComponent,
    resolve: {
      dataPrimaryKey: DataPrimaryKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataPrimaryKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataPrimaryKeyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataPrimaryKeyDeletePopupComponent,
    resolve: {
      dataPrimaryKey: DataPrimaryKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataPrimaryKey.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
