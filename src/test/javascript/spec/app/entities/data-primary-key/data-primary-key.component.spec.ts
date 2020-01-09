/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PlatformTestModule } from '../../../test.module';
import { DataPrimaryKeyComponent } from 'app/entities/data-primary-key/data-primary-key.component';
import { DataPrimaryKeyService } from 'app/entities/data-primary-key/data-primary-key.service';
import { DataPrimaryKey } from 'app/shared/model/data-primary-key.model';

describe('Component Tests', () => {
  describe('DataPrimaryKey Management Component', () => {
    let comp: DataPrimaryKeyComponent;
    let fixture: ComponentFixture<DataPrimaryKeyComponent>;
    let service: DataPrimaryKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataPrimaryKeyComponent],
        providers: []
      })
        .overrideTemplate(DataPrimaryKeyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataPrimaryKeyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataPrimaryKeyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataPrimaryKey(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataPrimaryKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
