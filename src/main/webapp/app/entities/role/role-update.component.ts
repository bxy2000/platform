import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRole, Role } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';
import { IUserExtends } from 'app/shared/model/user-extends.model';
import { UserExtendsService } from 'app/entities/user-extends';

@Component({
  selector: 'jhi-role-update',
  templateUrl: './role-update.component.html'
})
export class RoleUpdateComponent implements OnInit {
  isSaving: boolean;

  menus: IMenu[];

  userextends: IUserExtends[];

  editForm = this.fb.group({
    id: [],
    roleName: [null, [Validators.required, Validators.maxLength(80)]],
    menus: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected roleService: RoleService,
    protected menuService: MenuService,
    protected userExtendsService: UserExtendsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ role }) => {
      this.updateForm(role);
    });
    this.menuService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMenu[]>) => response.body)
      )
      .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userExtendsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserExtends[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserExtends[]>) => response.body)
      )
      .subscribe((res: IUserExtends[]) => (this.userextends = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(role: IRole) {
    this.editForm.patchValue({
      id: role.id,
      roleName: role.roleName,
      menus: role.menus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const role = this.createFromForm();
    if (role.id !== undefined) {
      this.subscribeToSaveResponse(this.roleService.update(role));
    } else {
      this.subscribeToSaveResponse(this.roleService.create(role));
    }
  }

  private createFromForm(): IRole {
    return {
      ...new Role(),
      id: this.editForm.get(['id']).value,
      roleName: this.editForm.get(['roleName']).value,
      menus: this.editForm.get(['menus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>) {
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

  trackUserExtendsById(index: number, item: IUserExtends) {
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
