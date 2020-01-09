import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataPrimaryKey } from 'app/shared/model/data-primary-key.model';
import { DataPrimaryKeyService } from './data-primary-key.service';

@Component({
  selector: 'jhi-data-primary-key-delete-dialog',
  templateUrl: './data-primary-key-delete-dialog.component.html'
})
export class DataPrimaryKeyDeleteDialogComponent {
  dataPrimaryKey: IDataPrimaryKey;

  constructor(
    protected dataPrimaryKeyService: DataPrimaryKeyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataPrimaryKeyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataPrimaryKeyListModification',
        content: 'Deleted an dataPrimaryKey'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-primary-key-delete-popup',
  template: ''
})
export class DataPrimaryKeyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataPrimaryKey }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataPrimaryKeyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataPrimaryKey = dataPrimaryKey;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-primary-key', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-primary-key', { outlets: { popup: null } }]);
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
