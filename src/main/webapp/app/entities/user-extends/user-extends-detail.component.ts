import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserExtends } from 'app/shared/model/user-extends.model';

@Component({
  selector: 'jhi-user-extends-detail',
  templateUrl: './user-extends-detail.component.html'
})
export class UserExtendsDetailComponent implements OnInit {
  userExtends: IUserExtends;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userExtends }) => {
      this.userExtends = userExtends;
    });
  }

  previousState() {
    window.history.back();
  }
}
