import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDataFields, DataFields } from 'app/shared/model/data-fields.model';
import { DataFieldsService } from './data-fields.service';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from 'app/entities/data-catalog';

@Component({
  selector: 'jhi-data-fields-update',
  templateUrl: './data-fields-update.component.html'
})
export class DataFieldsUpdateComponent implements OnInit {
  isSaving: boolean;

  datacatalogs: IDataCatalog[];

  editForm = this.fb.group({
    id: [],
    fieldName: [],
    fieldType: [],
    length: [],
    scale: [],
    allowNull: [],
    primaryKey: [],
    remarks: [],
    stop: [],
    createDate: [],
    updateDate: [],
    modifyDate: [],
    dataCatalog: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataFieldsService: DataFieldsService,
    protected dataCatalogService: DataCatalogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataFields }) => {
      this.updateForm(dataFields);
    });
    this.dataCatalogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCatalog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCatalog[]>) => response.body)
      )
      .subscribe((res: IDataCatalog[]) => (this.datacatalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataFields: IDataFields) {
    this.editForm.patchValue({
      id: dataFields.id,
      fieldName: dataFields.fieldName,
      fieldType: dataFields.fieldType,
      length: dataFields.length,
      scale: dataFields.scale,
      allowNull: dataFields.allowNull,
      primaryKey: dataFields.primaryKey,
      remarks: dataFields.remarks,
      stop: dataFields.stop,
      createDate: dataFields.createDate != null ? dataFields.createDate.format(DATE_TIME_FORMAT) : null,
      updateDate: dataFields.updateDate != null ? dataFields.updateDate.format(DATE_TIME_FORMAT) : null,
      modifyDate: dataFields.modifyDate != null ? dataFields.modifyDate.format(DATE_TIME_FORMAT) : null,
      dataCatalog: dataFields.dataCatalog
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataFields = this.createFromForm();
    if (dataFields.id !== undefined) {
      this.subscribeToSaveResponse(this.dataFieldsService.update(dataFields));
    } else {
      this.subscribeToSaveResponse(this.dataFieldsService.create(dataFields));
    }
  }

  private createFromForm(): IDataFields {
    return {
      ...new DataFields(),
      id: this.editForm.get(['id']).value,
      fieldName: this.editForm.get(['fieldName']).value,
      fieldType: this.editForm.get(['fieldType']).value,
      length: this.editForm.get(['length']).value,
      scale: this.editForm.get(['scale']).value,
      allowNull: this.editForm.get(['allowNull']).value,
      primaryKey: this.editForm.get(['primaryKey']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataFields>>) {
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
