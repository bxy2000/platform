import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IDataCatalog } from 'app/shared/model/data-catalog.model';
import { AccountService } from 'app/core';
import { DataCatalogService } from './data-catalog.service';

@Component({
  selector: 'jhi-data-catalog',
  templateUrl: './data-catalog.component.html'
})
export class DataCatalogComponent implements OnInit, OnDestroy {
  dataCatalogs: IDataCatalog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataCatalogService: DataCatalogService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataCatalogService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataCatalog[]>) => res.ok),
        map((res: HttpResponse<IDataCatalog[]>) => res.body)
      )
      .subscribe(
        (res: IDataCatalog[]) => {
          this.dataCatalogs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataCatalogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataCatalog) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInDataCatalogs() {
    this.eventSubscriber = this.eventManager.subscribe('dataCatalogListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
