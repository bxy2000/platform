/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DatabaseConnectionUpdateComponent } from 'app/entities/database-connection/database-connection-update.component';
import { DatabaseConnectionService } from 'app/entities/database-connection/database-connection.service';
import { DatabaseConnection } from 'app/shared/model/database-connection.model';

describe('Component Tests', () => {
  describe('DatabaseConnection Management Update Component', () => {
    let comp: DatabaseConnectionUpdateComponent;
    let fixture: ComponentFixture<DatabaseConnectionUpdateComponent>;
    let service: DatabaseConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DatabaseConnectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DatabaseConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatabaseConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatabaseConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatabaseConnection(123);
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
        const entity = new DatabaseConnection();
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
