/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlatformTestModule } from '../../../test.module';
import { DatabaseConnectionDetailComponent } from 'app/entities/database-connection/database-connection-detail.component';
import { DatabaseConnection } from 'app/shared/model/database-connection.model';

describe('Component Tests', () => {
  describe('DatabaseConnection Management Detail Component', () => {
    let comp: DatabaseConnectionDetailComponent;
    let fixture: ComponentFixture<DatabaseConnectionDetailComponent>;
    const route = ({ data: of({ databaseConnection: new DatabaseConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlatformTestModule],
        declarations: [DatabaseConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DatabaseConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatabaseConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.databaseConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
