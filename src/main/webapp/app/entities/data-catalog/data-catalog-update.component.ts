import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDataCatalog, DataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from './data-catalog.service';
import { IDatabaseConnection } from 'app/shared/model/database-connection.model';
import { DatabaseConnectionService } from 'app/entities/database-connection';

@Component({
  selector: 'jhi-data-catalog-update',
  templateUrl: './data-catalog-update.component.html'
})
export class DataCatalogUpdateComponent implements OnInit {
  isSaving: boolean;

  databaseconnections: IDatabaseConnection[];

  datacatalogs: IDataCatalog[];

  editForm = this.fb.group({
    id: [],
    title: [],
    type: [],
    icon: [],
    tableName: [],
    tableType: [],
    remarks: [],
    relationGraph: [],
    dbConnection: [],
    parent: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected dataCatalogService: DataCatalogService,
    protected databaseConnectionService: DatabaseConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataCatalog }) => {
      this.updateForm(dataCatalog);
    });
    this.databaseConnectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDatabaseConnection[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDatabaseConnection[]>) => response.body)
      )
      .subscribe((res: IDatabaseConnection[]) => (this.databaseconnections = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.dataCatalogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCatalog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCatalog[]>) => response.body)
      )
      .subscribe((res: IDataCatalog[]) => (this.datacatalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataCatalog: IDataCatalog) {
    this.editForm.patchValue({
      id: dataCatalog.id,
      title: dataCatalog.title,
      type: dataCatalog.type,
      icon: dataCatalog.icon,
      tableName: dataCatalog.tableName,
      tableType: dataCatalog.tableType,
      remarks: dataCatalog.remarks,
      relationGraph: dataCatalog.relationGraph,
      dbConnection: dataCatalog.dbConnection,
      parent: dataCatalog.parent
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataCatalog = this.createFromForm();
    if (dataCatalog.id !== undefined) {
      this.subscribeToSaveResponse(this.dataCatalogService.update(dataCatalog));
    } else {
      this.subscribeToSaveResponse(this.dataCatalogService.create(dataCatalog));
    }
  }

  private createFromForm(): IDataCatalog {
    return {
      ...new DataCatalog(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      type: this.editForm.get(['type']).value,
      icon: this.editForm.get(['icon']).value,
      tableName: this.editForm.get(['tableName']).value,
      tableType: this.editForm.get(['tableType']).value,
      remarks: this.editForm.get(['remarks']).value,
      relationGraph: this.editForm.get(['relationGraph']).value,
      dbConnection: this.editForm.get(['dbConnection']).value,
      parent: this.editForm.get(['parent']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataCatalog>>) {
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

  trackDatabaseConnectionById(index: number, item: IDatabaseConnection) {
    return item.id;
  }

  trackDataCatalogById(index: number, item: IDataCatalog) {
    return item.id;
  }
}
