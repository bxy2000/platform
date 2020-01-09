/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { UserExtendsDeleteDialogComponent } from 'app/entities/user-extends/user-extends-delete-dialog.component';
import { UserExtendsService } from 'app/entities/user-extends/user-extends.service';

describe('Component Tests', () => {
  describe('UserExtends Management Delete Component', () => {
    let comp: UserExtendsDeleteDialogComponent;
    let fixture: ComponentFixture<UserExtendsDeleteDialogComponent>;
    let service: UserExtendsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [UserExtendsDeleteDialogComponent]
      })
        .overrideTemplate(UserExtendsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExtendsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExtendsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
