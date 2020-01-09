/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlatformTestModule } from '../../../test.module';
import { DataCatalogDeleteDialogComponent } from 'app/entities/data-catalog/data-catalog-delete-dialog.component';
import { DataCatalogService } from 'app/entities/data-catalog/data-catalog.service';

describe('Component Tests', () => {
  describe('DataCatalog Management Delete Component', () => {
    let comp: DataCatalogDeleteDialogComponent;
    let fixture: ComponentFixture<DataCatalogDeleteDialogComponent>;
    let service: DataCatalogService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataCatalogDeleteDialogComponent]
      })
        .overrideTemplate(DataCatalogDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCatalogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCatalogService);
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
