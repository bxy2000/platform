import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  DataFieldsComponent,
  DataFieldsDetailComponent,
  DataFieldsUpdateComponent,
  DataFieldsDeletePopupComponent,
  DataFieldsDeleteDialogComponent,
  dataFieldsRoute,
  dataFieldsPopupRoute
} from './';

const ENTITY_STATES = [...dataFieldsRoute, ...dataFieldsPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataFieldsComponent,
    DataFieldsDetailComponent,
    DataFieldsUpdateComponent,
    DataFieldsDeleteDialogComponent,
    DataFieldsDeletePopupComponent
  ],
  entryComponents: [DataFieldsComponent, DataFieldsUpdateComponent, DataFieldsDeleteDialogComponent, DataFieldsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDataFieldsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
