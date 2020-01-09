/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DataForeignKeyDetailComponent } from 'app/entities/data-foreign-key/data-foreign-key-detail.component';
import { DataForeignKey } from 'app/shared/model/data-foreign-key.model';

describe('Component Tests', () => {
  describe('DataForeignKey Management Detail Component', () => {
    let comp: DataForeignKeyDetailComponent;
    let fixture: ComponentFixture<DataForeignKeyDetailComponent>;
    const route = ({ data: of({ dataForeignKey: new DataForeignKey(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DataForeignKeyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataForeignKeyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataForeignKeyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataForeignKey).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
