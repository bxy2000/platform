import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataPrimaryKey } from 'app/shared/model/data-primary-key.model';

@Component({
  selector: 'jhi-data-primary-key-detail',
  templateUrl: './data-primary-key-detail.component.html'
})
export class DataPrimaryKeyDetailComponent implements OnInit {
  dataPrimaryKey: IDataPrimaryKey;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataPrimaryKey }) => {
      this.dataPrimaryKey = dataPrimaryKey;
    });
  }

  previousState() {
    window.history.back();
  }
}
