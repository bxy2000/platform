/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataPrimaryKeyUpdateComponent } from 'app/entities/data-primary-key/data-primary-key-update.component';
import { DataPrimaryKeyService } from 'app/entities/data-primary-key/data-primary-key.service';
import { DataPrimaryKey } from 'app/shared/model/data-primary-key.model';

describe('Component Tests', () => {
  describe('DataPrimaryKey Management Update Component', () => {
    let comp: DataPrimaryKeyUpdateComponent;
    let fixture: ComponentFixture<DataPrimaryKeyUpdateComponent>;
    let service: DataPrimaryKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataPrimaryKeyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataPrimaryKeyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataPrimaryKeyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataPrimaryKeyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataPrimaryKey(123);
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
        const entity = new DataPrimaryKey();
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
