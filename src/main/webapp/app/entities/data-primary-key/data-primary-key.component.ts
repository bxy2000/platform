import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataPrimaryKey } from 'app/shared/model/data-primary-key.model';
import { AccountService } from 'app/core';
import { DataPrimaryKeyService } from './data-primary-key.service';

@Component({
  selector: 'jhi-data-primary-key',
  templateUrl: './data-primary-key.component.html'
})
export class DataPrimaryKeyComponent implements OnInit, OnDestroy {
  dataPrimaryKeys: IDataPrimaryKey[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataPrimaryKeyService: DataPrimaryKeyService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataPrimaryKeyService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataPrimaryKey[]>) => res.ok),
        map((res: HttpResponse<IDataPrimaryKey[]>) => res.body)
      )
      .subscribe(
        (res: IDataPrimaryKey[]) => {
          this.dataPrimaryKeys = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataPrimaryKeys();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataPrimaryKey) {
    return item.id;
  }

  registerChangeInDataPrimaryKeys() {
    this.eventSubscriber = this.eventManager.subscribe('dataPrimaryKeyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
