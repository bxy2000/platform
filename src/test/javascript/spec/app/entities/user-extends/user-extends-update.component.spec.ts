/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { UserExtendsUpdateComponent } from 'app/entities/user-extends/user-extends-update.component';
import { UserExtendsService } from 'app/entities/user-extends/user-extends.service';
import { UserExtends } from 'app/shared/model/user-extends.model';

describe('Component Tests', () => {
  describe('UserExtends Management Update Component', () => {
    let comp: UserExtendsUpdateComponent;
    let fixture: ComponentFixture<UserExtendsUpdateComponent>;
    let service: UserExtendsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [UserExtendsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserExtendsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserExtendsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExtendsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserExtends(123);
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
        const entity = new UserExtends();
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
