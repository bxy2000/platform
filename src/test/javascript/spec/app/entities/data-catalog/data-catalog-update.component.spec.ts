/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataCatalogUpdateComponent } from 'app/entities/data-catalog/data-catalog-update.component';
import { DataCatalogService } from 'app/entities/data-catalog/data-catalog.service';
import { DataCatalog } from 'app/shared/model/data-catalog.model';

describe('Component Tests', () => {
  describe('DataCatalog Management Update Component', () => {
    let comp: DataCatalogUpdateComponent;
    let fixture: ComponentFixture<DataCatalogUpdateComponent>;
    let service: DataCatalogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataCatalogUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataCatalogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCatalogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCatalogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataCatalog(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataCatalog();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
