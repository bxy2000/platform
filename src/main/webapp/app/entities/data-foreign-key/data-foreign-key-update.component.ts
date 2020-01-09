import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDataForeignKey, DataForeignKey } from 'app/shared/model/data-foreign-key.model';
import { DataForeignKeyService } from './data-foreign-key.service';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from 'app/entities/data-catalog';

@Component({
  selector: 'jhi-data-foreign-key-update',
  templateUrl: './data-foreign-key-update.component.html'
})
export class DataForeignKeyUpdateComponent implements OnInit {
  isSaving: boolean;

  datacatalogs: IDataCatalog[];

  editForm = this.fb.group({
    id: [],
    name: [],
    field: [],
    referenceTable: [],
    referenceField: [],
    remarks: [],
    stop: [],
    createDate: [],
    updateDate: [],
    modifyDate: [],
    dataCatalog: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataForeignKeyService: DataForeignKeyService,
    protected dataCatalogService: DataCatalogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataForeignKey }) => {
      this.updateForm(dataForeignKey);
    });
    this.dataCatalogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCatalog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCatalog[]>) => response.body)
      )
      .subscribe((res: IDataCatalog[]) => (this.datacatalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataForeignKey: IDataForeignKey) {
    this.editForm.patchValue({
      id: dataForeignKey.id,
      name: dataForeignKey.name,
      field: dataForeignKey.field,
      referenceTable: dataForeignKey.referenceTable,
      referenceField: dataForeignKey.referenceField,
      remarks: dataForeignKey.remarks,
      stop: dataForeignKey.stop,
      createDate: dataForeignKey.createDate != null ? dataForeignKey.createDate.format(DATE_TIME_FORMAT) : null,
      updateDate: dataForeignKey.updateDate != null ? dataForeignKey.updateDate.format(DATE_TIME_FORMAT) : null,
      modifyDate: dataForeignKey.modifyDate != null ? dataForeignKey.modifyDate.format(DATE_TIME_FORMAT) : null,
      dataCatalog: dataForeignKey.dataCatalog
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataForeignKey = this.createFromForm();
    if (dataForeignKey.id !== undefined) {
      this.subscribeToSaveResponse(this.dataForeignKeyService.update(dataForeignKey));
    } else {
      this.subscribeToSaveResponse(this.dataForeignKeyService.create(dataForeignKey));
    }
  }

  private createFromForm(): IDataForeignKey {
    return {
      ...new DataForeignKey(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      field: this.editForm.get(['field']).value,
      referenceTable: this.editForm.get(['referenceTable']).value,
      referenceField: this.editForm.get(['referenceField']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataForeignKey>>) {
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
