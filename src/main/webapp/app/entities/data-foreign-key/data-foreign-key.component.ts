import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataForeignKey } from 'app/shared/model/data-foreign-key.model';
import { AccountService } from 'app/core';
import { DataForeignKeyService } from './data-foreign-key.service';

@Component({
  selector: 'jhi-data-foreign-key',
  templateUrl: './data-foreign-key.component.html'
})
export class DataForeignKeyComponent implements OnInit, OnDestroy {
  dataForeignKeys: IDataForeignKey[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataForeignKeyService: DataForeignKeyService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataForeignKeyService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataForeignKey[]>) => res.ok),
        map((res: HttpResponse<IDataForeignKey[]>) => res.body)
      )
      .subscribe(
        (res: IDataForeignKey[]) => {
          this.dataForeignKeys = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataForeignKeys();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataForeignKey) {
    return item.id;
  }

  registerChangeInDataForeignKeys() {
    this.eventSubscriber = this.eventManager.subscribe('dataForeignKeyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
