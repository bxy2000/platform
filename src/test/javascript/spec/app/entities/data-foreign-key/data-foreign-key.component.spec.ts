/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PlatformTestModule } from '../../../test.module';
import { DataForeignKeyComponent } from 'app/entities/data-foreign-key/data-foreign-key.component';
import { DataForeignKeyService } from 'app/entities/data-foreign-key/data-foreign-key.service';
import { DataForeignKey } from 'app/shared/model/data-foreign-key.model';

describe('Component Tests', () => {
  describe('DataForeignKey Management Component', () => {
    let comp: DataForeignKeyComponent;
    let fixture: ComponentFixture<DataForeignKeyComponent>;
    let service: DataForeignKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataForeignKeyComponent],
        providers: []
      })
        .overrideTemplate(DataForeignKeyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataForeignKeyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataForeignKeyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataForeignKey(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataForeignKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
