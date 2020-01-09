import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataForeignKey } from 'app/shared/model/data-foreign-key.model';
import { DataForeignKeyService } from './data-foreign-key.service';
import { DataForeignKeyComponent } from './data-foreign-key.component';
import { DataForeignKeyDetailComponent } from './data-foreign-key-detail.component';
import { DataForeignKeyUpdateComponent } from './data-foreign-key-update.component';
import { DataForeignKeyDeletePopupComponent } from './data-foreign-key-delete-dialog.component';
import { IDataForeignKey } from 'app/shared/model/data-foreign-key.model';

@Injectable({ providedIn: 'root' })
export class DataForeignKeyResolve implements Resolve<IDataForeignKey> {
  constructor(private service: DataForeignKeyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataForeignKey> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataForeignKey>) => response.ok),
        map((dataForeignKey: HttpResponse<DataForeignKey>) => dataForeignKey.body)
      );
    }
    return of(new DataForeignKey());
  }
}

export const dataForeignKeyRoute: Routes = [
  {
    path: '',
    component: DataForeignKeyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataForeignKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataForeignKeyDetailComponent,
    resolve: {
      dataForeignKey: DataForeignKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataForeignKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataForeignKeyUpdateComponent,
    resolve: {
      dataForeignKey: DataForeignKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataForeignKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataForeignKeyUpdateComponent,
    resolve: {
      dataForeignKey: DataForeignKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataForeignKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataForeignKeyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataForeignKeyDeletePopupComponent,
    resolve: {
      dataForeignKey: DataForeignKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataForeignKey.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
