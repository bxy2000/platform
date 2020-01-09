import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataCatalog } from 'app/shared/model/data-catalog.model';
import { DataCatalogService } from './data-catalog.service';

@Component({
  selector: 'jhi-data-catalog-delete-dialog',
  templateUrl: './data-catalog-delete-dialog.component.html'
})
export class DataCatalogDeleteDialogComponent {
  dataCatalog: IDataCatalog;

  constructor(
    protected dataCatalogService: DataCatalogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataCatalogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataCatalogListModification',
        content: 'Deleted an dataCatalog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-catalog-delete-popup',
  template: ''
})
export class DataCatalogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCatalog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataCatalogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataCatalog = dataCatalog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-catalog', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-catalog', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
