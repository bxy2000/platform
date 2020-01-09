/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataFieldsDetailComponent } from 'app/entities/data-fields/data-fields-detail.component';
import { DataFields } from 'app/shared/model/data-fields.model';

describe('Component Tests', () => {
  describe('DataFields Management Detail Component', () => {
    let comp: DataFieldsDetailComponent;
    let fixture: ComponentFixture<DataFieldsDetailComponent>;
    const route = ({ data: of({ dataFields: new DataFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
