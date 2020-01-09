import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataCatalog } from 'app/shared/model/data-catalog.model';

@Component({
  selector: 'jhi-data-catalog-detail',
  templateUrl: './data-catalog-detail.component.html'
})
export class DataCatalogDetailComponent implements OnInit {
  dataCatalog: IDataCatalog;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCatalog }) => {
      this.dataCatalog = dataCatalog;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
