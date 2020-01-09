import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-extends',
        loadChildren: () => import('./user-extends/user-extends.module').then(m => m.PlatformUserExtendsModule)
      },
      {
        path: 'role',
        loadChildren: () => import('./role/role.module').then(m => m.PlatformRoleModule)
      },
      {
        path: 'menu',
        loadChildren: () => import('./menu/menu.module').then(m => m.PlatformMenuModule)
      },
      {
        path: 'database-connection',
        loadChildren: () => import('./database-connection/database-connection.module').then(m => m.PlatformDatabaseConnectionModule)
      },
      {
        path: 'data-catalog',
        loadChildren: () => import('./data-catalog/data-catalog.module').then(m => m.PlatformDataCatalogModule)
      },
      {
        path: 'data-primary-key',
        loadChildren: () => import('./data-primary-key/data-primary-key.module').then(m => m.PlatformDataPrimaryKeyModule)
      },
      {
        path: 'data-foreign-key',
        loadChildren: () => import('./data-foreign-key/data-foreign-key.module').then(m => m.PlatformDataForeignKeyModule)
      },
      {
        path: 'data-fields',
        loadChildren: () => import('./data-fields/data-fields.module').then(m => m.PlatformDataFieldsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformEntityModule {}
