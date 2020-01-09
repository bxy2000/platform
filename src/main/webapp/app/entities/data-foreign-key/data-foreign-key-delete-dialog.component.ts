import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataForeignKey } from 'app/shared/model/data-foreign-key.model';
import { DataForeignKeyService } from './data-foreign-key.service';

@Component({
  selector: 'jhi-data-foreign-key-delete-dialog',
  templateUrl: './data-foreign-key-delete-dialog.component.html'
})
export class DataForeignKeyDeleteDialogComponent {
  dataForeignKey: IDataForeignKey;

  constructor(
    protected dataForeignKeyService: DataForeignKeyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataForeignKeyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataForeignKeyListModification',
        content: 'Deleted an dataForeignKey'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-foreign-key-delete-popup',
  template: ''
})
export class DataForeignKeyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataForeignKey }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataForeignKeyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataForeignKey = dataForeignKey;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-foreign-key', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-foreign-key', { outlets: { popup: null } }]);
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
