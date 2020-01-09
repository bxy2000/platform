import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDataPrimaryKey, DataPrimaryKey } from 'app/shared/model/data-primary-key.model';
import { DataPrimaryKeyService } from './data-primary-key.service';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from 'app/entities/data-catalog';

@Component({
  selector: 'jhi-data-primary-key-update',
  templateUrl: './data-primary-key-update.component.html'
})
export class DataPrimaryKeyUpdateComponent implements OnInit {
  isSaving: boolean;

  datacatalogs: IDataCatalog[];

  editForm = this.fb.group({
    id: [],
    name: [],
    fields: [],
    remarks: [],
    stop: [],
    createDate: [],
    updateDate: [],
    modifyDate: [],
    dataCatalog: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataPrimaryKeyService: DataPrimaryKeyService,
    protected dataCatalogService: DataCatalogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataPrimaryKey }) => {
      this.updateForm(dataPrimaryKey);
    });
    this.dataCatalogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCatalog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCatalog[]>) => response.body)
      )
      .subscribe((res: IDataCatalog[]) => (this.datacatalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataPrimaryKey: IDataPrimaryKey) {
    this.editForm.patchValue({
      id: dataPrimaryKey.id,
      name: dataPrimaryKey.name,
      fields: dataPrimaryKey.fields,
      remarks: dataPrimaryKey.remarks,
      stop: dataPrimaryKey.stop,
      createDate: dataPrimaryKey.createDate != null ? dataPrimaryKey.createDate.format(DATE_TIME_FORMAT) : null,
      updateDate: dataPrimaryKey.updateDate != null ? dataPrimaryKey.updateDate.format(DATE_TIME_FORMAT) : null,
      modifyDate: dataPrimaryKey.modifyDate != null ? dataPrimaryKey.modifyDate.format(DATE_TIME_FORMAT) : null,
      dataCatalog: dataPrimaryKey.dataCatalog
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataPrimaryKey = this.createFromForm();
    if (dataPrimaryKey.id !== undefined) {
      this.subscribeToSaveResponse(this.dataPrimaryKeyService.update(dataPrimaryKey));
    } else {
      this.subscribeToSaveResponse(this.dataPrimaryKeyService.create(dataPrimaryKey));
    }
  }

  private createFromForm(): IDataPrimaryKey {
    return {
      ...new DataPrimaryKey(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      fields: this.editForm.get(['fields']).value,
      remarks: this.editForm.get(['remarks']).value,
      stop: this.editForm.get(['stop']).value,
      createDate:
        this.editForm.get(['createDate']).value != null ? moment(this.editForm.get(['createDate']).value, DATE_TIME_FORMAT) : undefined,
      updateDate:
        this.editForm.get(['updateDate']).value != null ? moment(this.editForm.get(['updateDate']).value, DATE_TIME_FORMAT) : undefined,
      modifyDate:
        this.editForm.get(['modifyDate']).value != null ? moment(this.editForm.get(['modifyDate']).value, DATE_TIME_FORMAT) : undefined,
      dataCatalog: this.editForm.get(['dataCatalog']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataPrimaryKey>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDataCatalogById(index: number, item: IDataCatalog) {
    return item.id;
  }
}
