import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  DataPrimaryKeyComponent,
  DataPrimaryKeyDetailComponent,
  DataPrimaryKeyUpdateComponent,
  DataPrimaryKeyDeletePopupComponent,
  DataPrimaryKeyDeleteDialogComponent,
  dataPrimaryKeyRoute,
  dataPrimaryKeyPopupRoute
} from './';

const ENTITY_STATES = [...dataPrimaryKeyRoute, ...dataPrimaryKeyPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataPrimaryKeyComponent,
    DataPrimaryKeyDetailComponent,
    DataPrimaryKeyUpdateComponent,
    DataPrimaryKeyDeleteDialogComponent,
    DataPrimaryKeyDeletePopupComponent
  ],
  entryComponents: [
    DataPrimaryKeyComponent,
    DataPrimaryKeyUpdateComponent,
    DataPrimaryKeyDeleteDialogComponent,
    DataPrimaryKeyDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDataPrimaryKeyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
