/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataCatalogDetailComponent } from 'app/entities/data-catalog/data-catalog-detail.component';
import { DataCatalog } from 'app/shared/model/data-catalog.model';

describe('Component Tests', () => {
  describe('DataCatalog Management Detail Component', () => {
    let comp: DataCatalogDetailComponent;
    let fixture: ComponentFixture<DataCatalogDetailComponent>;
    const route = ({ data: of({ dataCatalog: new DataCatalog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataCatalogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataCatalogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCatalogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataCatalog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
