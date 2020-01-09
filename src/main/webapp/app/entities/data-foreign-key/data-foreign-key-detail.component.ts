import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataForeignKey } from 'app/shared/model/data-foreign-key.model';

@Component({
  selector: 'jhi-data-foreign-key-detail',
  templateUrl: './data-foreign-key-detail.component.html'
})
export class DataForeignKeyDetailComponent implements OnInit {
  dataForeignKey: IDataForeignKey;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataForeignKey }) => {
      this.dataForeignKey = dataForeignKey;
    });
  }

  previousState() {
    window.history.back();
  }
}
