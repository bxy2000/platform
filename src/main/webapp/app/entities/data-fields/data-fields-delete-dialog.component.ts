import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataFields } from 'app/shared/model/data-fields.model';
import { DataFieldsService } from './data-fields.service';

@Component({
  selector: 'jhi-data-fields-delete-dialog',
  templateUrl: './data-fields-delete-dialog.component.html'
})
export class DataFieldsDeleteDialogComponent {
  dataFields: IDataFields;

  constructor(
    protected dataFieldsService: DataFieldsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataFieldsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataFieldsListModification',
        content: 'Deleted an dataFields'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-fields-delete-popup',
  template: ''
})
export class DataFieldsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataFields }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataFieldsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataFields = dataFields;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-fields', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-fields', { outlets: { popup: null } }]);
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
