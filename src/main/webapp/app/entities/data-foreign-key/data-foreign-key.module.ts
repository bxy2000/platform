import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  DataForeignKeyComponent,
  DataForeignKeyDetailComponent,
  DataForeignKeyUpdateComponent,
  DataForeignKeyDeletePopupComponent,
  DataForeignKeyDeleteDialogComponent,
  dataForeignKeyRoute,
  dataForeignKeyPopupRoute
} from './';

const ENTITY_STATES = [...dataForeignKeyRoute, ...dataForeignKeyPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataForeignKeyComponent,
    DataForeignKeyDetailComponent,
    DataForeignKeyUpdateComponent,
    DataForeignKeyDeleteDialogComponent,
    DataForeignKeyDeletePopupComponent
  ],
  entryComponents: [
    DataForeignKeyComponent,
    DataForeignKeyUpdateComponent,
    DataForeignKeyDeleteDialogComponent,
    DataForeignKeyDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDataForeignKeyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
