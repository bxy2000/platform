/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { UserExtendsDetailComponent } from 'app/entities/user-extends/user-extends-detail.component';
import { UserExtends } from 'app/shared/model/user-extends.model';

describe('Component Tests', () => {
  describe('UserExtends Management Detail Component', () => {
    let comp: UserExtendsDetailComponent;
    let fixture: ComponentFixture<UserExtendsDetailComponent>;
    const route = ({ data: of({ userExtends: new UserExtends(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [UserExtendsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserExtendsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExtendsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userExtends).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
