/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataFieldsUpdateComponent } from 'app/entities/data-fields/data-fields-update.component';
import { DataFieldsService } from 'app/entities/data-fields/data-fields.service';
import { DataFields } from 'app/shared/model/data-fields.model';

describe('Component Tests', () => {
  describe('DataFields Management Update Component', () => {
    let comp: DataFieldsUpdateComponent;
    let fixture: ComponentFixture<DataFieldsUpdateComponent>;
    let service: DataFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataFieldsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataFields(123);
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
        const entity = new DataFields();
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
