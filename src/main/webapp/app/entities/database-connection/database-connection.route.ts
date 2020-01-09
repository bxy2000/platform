import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatabaseConnection } from 'app/shared/model/database-connection.model';
import { DatabaseConnectionService } from './database-connection.service';
import { DatabaseConnectionComponent } from './database-connection.component';
import { DatabaseConnectionDetailComponent } from './database-connection-detail.component';
import { DatabaseConnectionUpdateComponent } from './database-connection-update.component';
import { DatabaseConnectionDeletePopupComponent } from './database-connection-delete-dialog.component';
import { IDatabaseConnection } from 'app/shared/model/database-connection.model';

@Injectable({ providedIn: 'root' })
export class DatabaseConnectionResolve implements Resolve<IDatabaseConnection> {
  constructor(private service: DatabaseConnectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatabaseConnection> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatabaseConnection>) => response.ok),
        map((databaseConnection: HttpResponse<DatabaseConnection>) => databaseConnection.body)
      );
    }
    return of(new DatabaseConnection());
  }
}

export const databaseConnectionRoute: Routes = [
  {
    path: '',
    component: DatabaseConnectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'platformApp.databaseConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DatabaseConnectionDetailComponent,
    resolve: {
      databaseConnection: DatabaseConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.databaseConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DatabaseConnectionUpdateComponent,
    resolve: {
      databaseConnection: DatabaseConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.databaseConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DatabaseConnectionUpdateComponent,
    resolve: {
      databaseConnection: DatabaseConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.databaseConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const databaseConnectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatabaseConnectionDeletePopupComponent,
    resolve: {
      databaseConnection: DatabaseConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.databaseConnection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
