import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMenu, Menu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
  selector: 'jhi-menu-update',
  templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
  isSaving: boolean;

  menus: IMenu[];

  roles: IRole[];

  editForm = this.fb.group({
    id: [],
    text: [],
    group: [],
    link: [],
    externalLink: [],
    target: [],
    icon: [],
    hide: [],
    description: [],
    parent: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected menuService: MenuService,
    protected roleService: RoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ menu }) => {
      this.updateForm(menu);
    });
    this.menuService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMenu[]>) => response.body)
      )
      .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.roleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRole[]>) => response.body)
      )
      .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(menu: IMenu) {
    this.editForm.patchValue({
      id: menu.id,
      text: menu.text,
      group: menu.group,
      link: menu.link,
      externalLink: menu.externalLink,
      target: menu.target,
      icon: menu.icon,
      hide: menu.hide,
      description: menu.description,
      parent: menu.parent
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const menu = this.createFromForm();
    if (menu.id !== undefined) {
      this.subscribeToSaveResponse(this.menuService.update(menu));
    } else {
      this.subscribeToSaveResponse(this.menuService.create(menu));
    }
  }

  private createFromForm(): IMenu {
    return {
      ...new Menu(),
      id: this.editForm.get(['id']).value,
      text: this.editForm.get(['text']).value,
      group: this.editForm.get(['group']).value,
      link: this.editForm.get(['link']).value,
      externalLink: this.editForm.get(['externalLink']).value,
      target: this.editForm.get(['target']).value,
      icon: this.editForm.get(['icon']).value,
      hide: this.editForm.get(['hide']).value,
      description: this.editForm.get(['description']).value,
      parent: this.editForm.get(['parent']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMenuById(index: number, item: IMenu) {
    return item.id;
  }

  trackRoleById(index: number, item: IRole) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
