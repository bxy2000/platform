import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  DataCatalogComponent,
  DataCatalogDetailComponent,
  DataCatalogUpdateComponent,
  DataCatalogDeletePopupComponent,
  DataCatalogDeleteDialogComponent,
  dataCatalogRoute,
  dataCatalogPopupRoute
} from './';

const ENTITY_STATES = [...dataCatalogRoute, ...dataCatalogPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataCatalogComponent,
    DataCatalogDetailComponent,
    DataCatalogUpdateComponent,
    DataCatalogDeleteDialogComponent,
    DataCatalogDeletePopupComponent
  ],
  entryComponents: [DataCatalogComponent, DataCatalogUpdateComponent, DataCatalogDeleteDialogComponent, DataCatalogDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDataCatalogModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
