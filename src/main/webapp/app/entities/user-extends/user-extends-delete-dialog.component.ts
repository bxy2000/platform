import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserExtends } from 'app/shared/model/user-extends.model';
import { UserExtendsService } from './user-extends.service';

@Component({
  selector: 'jhi-user-extends-delete-dialog',
  templateUrl: './user-extends-delete-dialog.component.html'
})
export class UserExtendsDeleteDialogComponent {
  userExtends: IUserExtends;

  constructor(
    protected userExtendsService: UserExtendsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userExtendsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userExtendsListModification',
        content: 'Deleted an userExtends'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-extends-delete-popup',
  template: ''
})
export class UserExtendsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userExtends }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserExtendsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userExtends = userExtends;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-extends', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-extends', { outlets: { popup: null } }]);
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
