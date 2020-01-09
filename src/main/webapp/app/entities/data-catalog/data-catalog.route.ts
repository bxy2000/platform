import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from './data-catalog.service';
import { DataCatalogComponent } from './data-catalog.component';
import { DataCatalogDetailComponent } from './data-catalog-detail.component';
import { DataCatalogUpdateComponent } from './data-catalog-update.component';
import { DataCatalogDeletePopupComponent } from './data-catalog-delete-dialog.component';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';

@Injectable({ providedIn: 'root' })
export class DataCatalogResolve implements Resolve<IDataCatalog> {
  constructor(private service: DataCatalogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataCatalog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataCatalog>) => response.ok),
        map((dataCatalog: HttpResponse<DataCatalog>) => dataCatalog.body)
      );
    }
    return of(new DataCatalog());
  }
}

export const dataCatalogRoute: Routes = [
  {
    path: '',
    component: DataCatalogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataCatalogDetailComponent,
    resolve: {
      dataCatalog: DataCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataCatalogUpdateComponent,
    resolve: {
      dataCatalog: DataCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataCatalogUpdateComponent,
    resolve: {
      dataCatalog: DataCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataCatalogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataCatalogDeletePopupComponent,
    resolve: {
      dataCatalog: DataCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'platformApp.dataCatalog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
