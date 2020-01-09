import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatabaseConnection } from 'app/shared/model/database-connection.model';
import { DatabaseConnectionService } from './database-connection.service';

@Component({
  selector: 'jhi-database-connection-delete-dialog',
  templateUrl: './database-connection-delete-dialog.component.html'
})
export class DatabaseConnectionDeleteDialogComponent {
  databaseConnection: IDatabaseConnection;

  constructor(
    protected databaseConnectionService: DatabaseConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.databaseConnectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'databaseConnectionListModification',
        content: 'Deleted an databaseConnection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-database-connection-delete-popup',
  template: ''
})
export class DatabaseConnectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ databaseConnection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatabaseConnectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.databaseConnection = databaseConnection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/database-connection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/database-connection', { outlets: { popup: null } }]);
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
