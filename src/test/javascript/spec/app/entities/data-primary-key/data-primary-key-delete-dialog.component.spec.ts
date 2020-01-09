/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { DataPrimaryKeyDeleteDialogComponent } from 'app/entities/data-primary-key/data-primary-key-delete-dialog.component';
import { DataPrimaryKeyService } from 'app/entities/data-primary-key/data-primary-key.service';

describe('Component Tests', () => {
  describe('DataPrimaryKey Management Delete Component', () => {
    let comp: DataPrimaryKeyDeleteDialogComponent;
    let fixture: ComponentFixture<DataPrimaryKeyDeleteDialogComponent>;
    let service: DataPrimaryKeyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataPrimaryKeyDeleteDialogComponent]
      })
        .overrideTemplate(DataPrimaryKeyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataPrimaryKeyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataPrimaryKeyService);
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
