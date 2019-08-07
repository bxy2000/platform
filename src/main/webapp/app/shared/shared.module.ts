import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PlatformSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PlatformSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PlatformSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformSharedModule {
  static forRoot() {
    return {
      ngModule: PlatformSharedModule
    };
  }
}
