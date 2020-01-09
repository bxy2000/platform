/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { DataForeignKeyDeleteDialogComponent } from 'app/entities/data-foreign-key/data-foreign-key-delete-dialog.component';
import { DataForeignKeyService } from 'app/entities/data-foreign-key/data-foreign-key.service';

describe('Component Tests', () => {
  describe('DataForeignKey Management Delete Component', () => {
    let comp: DataForeignKeyDeleteDialogComponent;
    let fixture: ComponentFixture<DataForeignKeyDeleteDialogComponent>;
    let service: DataForeignKeyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataForeignKeyDeleteDialogComponent]
      })
        .overrideTemplate(DataForeignKeyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataForeignKeyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataForeignKeyService);
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
