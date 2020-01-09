import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDatabaseConnection, DatabaseConnection } from 'app/shared/model/database-connection.model';
import { DatabaseConnectionService } from './database-connection.service';

@Component({
  selector: 'jhi-database-connection-update',
  templateUrl: './database-connection-update.component.html'
})
export class DatabaseConnectionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    connectionName: [],
    host: [],
    port: [],
    databaseName: [],
    params: [],
    type: [],
    driver: [],
    url: [],
    username: [],
    password: [],
    test: []
  });

  constructor(
    protected databaseConnectionService: DatabaseConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ databaseConnection }) => {
      this.updateForm(databaseConnection);
    });
  }

  updateForm(databaseConnection: IDatabaseConnection) {
    this.editForm.patchValue({
      id: databaseConnection.id,
      connectionName: databaseConnection.connectionName,
      host: databaseConnection.host,
      port: databaseConnection.port,
      databaseName: databaseConnection.databaseName,
      params: databaseConnection.params,
      type: databaseConnection.type,
      driver: databaseConnection.driver,
      url: databaseConnection.url,
      username: databaseConnection.username,
      password: databaseConnection.password,
      test: databaseConnection.test
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const databaseConnection = this.createFromForm();
    if (databaseConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.databaseConnectionService.update(databaseConnection));
    } else {
      this.subscribeToSaveResponse(this.databaseConnectionService.create(databaseConnection));
    }
  }

  private createFromForm(): IDatabaseConnection {
    return {
      ...new DatabaseConnection(),
      id: this.editForm.get(['id']).value,
      connectionName: this.editForm.get(['connectionName']).value,
      host: this.editForm.get(['host']).value,
      port: this.editForm.get(['port']).value,
      databaseName: this.editForm.get(['databaseName']).value,
      params: this.editForm.get(['params']).value,
      type: this.editForm.get(['type']).value,
      driver: this.editForm.get(['driver']).value,
      url: this.editForm.get(['url']).value,
      username: this.editForm.get(['username']).value,
      password: this.editForm.get(['password']).value,
      test: this.editForm.get(['test']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatabaseConnection>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
