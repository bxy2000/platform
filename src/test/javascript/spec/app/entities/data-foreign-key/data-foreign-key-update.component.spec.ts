/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataForeignKeyUpdateComponent } from 'app/entities/data-foreign-key/data-foreign-key-update.component';
import { DataForeignKeyService } from 'app/entities/data-foreign-key/data-foreign-key.service';
import { DataForeignKey } from 'app/shared/model/data-foreign-key.model';

describe('Component Tests', () => {
  describe('DataForeignKey Management Update Component', () => {
    let comp: DataForeignKeyUpdateComponent;
    let fixture: ComponentFixture<DataForeignKeyUpdateComponent>;
    let service: DataForeignKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataForeignKeyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataForeignKeyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataForeignKeyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataForeignKeyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataForeignKey(123);
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
        const entity = new DataForeignKey();
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
