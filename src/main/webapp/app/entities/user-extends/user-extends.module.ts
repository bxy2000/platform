import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PlatformSharedModule } from 'app/shared';
import {
  UserExtendsComponent,
  UserExtendsDetailComponent,
  UserExtendsUpdateComponent,
  UserExtendsDeletePopupComponent,
  UserExtendsDeleteDialogComponent,
  userExtendsRoute,
  userExtendsPopupRoute
} from './';

const ENTITY_STATES = [...userExtendsRoute, ...userExtendsPopupRoute];

@NgModule({
  imports: [PlatformSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserExtendsComponent,
    UserExtendsDetailComponent,
    UserExtendsUpdateComponent,
    UserExtendsDeleteDialogComponent,
    UserExtendsDeletePopupComponent
  ],
  entryComponents: [UserExtendsComponent, UserExtendsUpdateComponent, UserExtendsDeleteDialogComponent, UserExtendsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformUserExtendsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
