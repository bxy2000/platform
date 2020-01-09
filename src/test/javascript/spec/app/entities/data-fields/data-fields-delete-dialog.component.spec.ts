/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { DataFieldsDeleteDialogComponent } from 'app/entities/data-fields/data-fields-delete-dialog.component';
import { DataFieldsService } from 'app/entities/data-fields/data-fields.service';

describe('Component Tests', () => {
  describe('DataFields Management Delete Component', () => {
    let comp: DataFieldsDeleteDialogComponent;
    let fixture: ComponentFixture<DataFieldsDeleteDialogComponent>;
    let service: DataFieldsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataFieldsDeleteDialogComponent]
      })
        .overrideTemplate(DataFieldsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataFieldsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataFieldsService);
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
