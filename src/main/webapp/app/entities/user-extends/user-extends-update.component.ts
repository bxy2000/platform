import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserExtends, UserExtends } from 'app/shared/model/user-extends.model';
import { UserExtendsService } from './user-extends.service';
import { IUser, UserService } from 'app/core';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
  selector: 'jhi-user-extends-update',
  templateUrl: './user-extends-update.component.html'
})
export class UserExtendsUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  roles: IRole[];

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required]],
    gender: [],
    mobile: [],
    user: [null, Validators.required],
    roles: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userExtendsService: UserExtendsService,
    protected userService: UserService,
    protected roleService: RoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userExtends }) => {
      this.updateForm(userExtends);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.roleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRole[]>) => response.body)
      )
      .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userExtends: IUserExtends) {
    this.editForm.patchValue({
      id: userExtends.id,
      username: userExtends.username,
      gender: userExtends.gender,
      mobile: userExtends.mobile,
      user: userExtends.user,
      roles: userExtends.roles
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userExtends = this.createFromForm();
    if (userExtends.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtendsService.update(userExtends));
    } else {
      this.subscribeToSaveResponse(this.userExtendsService.create(userExtends));
    }
  }

  private createFromForm(): IUserExtends {
    return {
      ...new UserExtends(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      gender: this.editForm.get(['gender']).value,
      mobile: this.editForm.get(['mobile']).value,
      user: this.editForm.get(['user']).value,
      roles: this.editForm.get(['roles']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtends>>) {
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

  trackUserById(index: number, item: IUser) {
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
