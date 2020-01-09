/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PlatformTestModule } from '../../../test.module';
import { DataCatalogComponent } from 'app/entities/data-catalog/data-catalog.component';
import { DataCatalogService } from 'app/entities/data-catalog/data-catalog.service';
import { DataCatalog } from 'app/shared/model/data-catalog.model';

describe('Component Tests', () => {
  describe('DataCatalog Management Component', () => {
    let comp: DataCatalogComponent;
    let fixture: ComponentFixture<DataCatalogComponent>;
    let service: DataCatalogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataCatalogComponent],
        providers: []
      })
        .overrideTemplate(DataCatalogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCatalogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCatalogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataCatalog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataCatalogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
