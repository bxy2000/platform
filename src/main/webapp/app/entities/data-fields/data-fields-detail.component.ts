import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataFields } from 'app/shared/model/data-fields.model';

@Component({
  selector: 'jhi-data-fields-detail',
  templateUrl: './data-fields-detail.component.html'
})
export class DataFieldsDetailComponent implements OnInit {
  dataFields: IDataFields;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataFields }) => {
      this.dataFields = dataFields;
    });
  }

  previousState() {
    window.history.back();
  }
}
