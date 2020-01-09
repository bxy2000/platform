import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatabaseConnection } from 'app/shared/model/database-connection.model';

@Component({
  selector: 'jhi-database-connection-detail',
  templateUrl: './database-connection-detail.component.html'
})
export class DatabaseConnectionDetailComponent implements OnInit {
  databaseConnection: IDatabaseConnection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ databaseConnection }) => {
      this.databaseConnection = databaseConnection;
    });
  }

  previousState() {
    window.history.back();
  }
}
