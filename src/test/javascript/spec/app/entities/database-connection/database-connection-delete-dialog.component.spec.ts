/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { DatabaseConnectionDeleteDialogComponent } from 'app/entities/database-connection/database-connection-delete-dialog.component';
import { DatabaseConnectionService } from 'app/entities/database-connection/database-connection.service';

describe('Component Tests', () => {
  describe('DatabaseConnection Management Delete Component', () => {
    let comp: DatabaseConnectionDeleteDialogComponent;
    let fixture: ComponentFixture<DatabaseConnectionDeleteDialogComponent>;
    let service: DatabaseConnectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DatabaseConnectionDeleteDialogComponent]
      })
        .overrideTemplate(DatabaseConnectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatabaseConnectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatabaseConnectionService);
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
