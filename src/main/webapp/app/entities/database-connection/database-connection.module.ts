import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  DatabaseConnectionComponent,
  DatabaseConnectionDetailComponent,
  DatabaseConnectionUpdateComponent,
  DatabaseConnectionDeletePopupComponent,
  DatabaseConnectionDeleteDialogComponent,
  databaseConnectionRoute,
  databaseConnectionPopupRoute
} from './';

const ENTITY_STATES = [...databaseConnectionRoute, ...databaseConnectionPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatabaseConnectionComponent,
    DatabaseConnectionDetailComponent,
    DatabaseConnectionUpdateComponent,
    DatabaseConnectionDeleteDialogComponent,
    DatabaseConnectionDeletePopupComponent
  ],
  entryComponents: [
    DatabaseConnectionComponent,
    DatabaseConnectionUpdateComponent,
    DatabaseConnectionDeleteDialogComponent,
    DatabaseConnectionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDatabaseConnectionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
