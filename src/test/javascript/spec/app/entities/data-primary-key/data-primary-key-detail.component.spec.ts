/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataPrimaryKeyDetailComponent } from 'app/entities/data-primary-key/data-primary-key-detail.component';
import { DataPrimaryKey } from 'app/shared/model/data-primary-key.model';

describe('Component Tests', () => {
  describe('DataPrimaryKey Management Detail Component', () => {
    let comp: DataPrimaryKeyDetailComponent;
    let fixture: ComponentFixture<DataPrimaryKeyDetailComponent>;
    const route = ({ data: of({ dataPrimaryKey: new DataPrimaryKey(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataPrimaryKeyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataPrimaryKeyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataPrimaryKeyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataPrimaryKey).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
