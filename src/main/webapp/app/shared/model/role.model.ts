import { IMenu } from 'app/shared/model/menu.model';
import { IUserExtends } from 'app/shared/model/user-extends.model';

export interface IRole {
  id?: number;
  roleName?: string;
  menus?: IMenu[];
  userExtends?: IUserExtends[];
}

export class Role implements IRole {
  constructor(public id?: number, public roleName?: string, public menus?: IMenu[], public userExtends?: IUserExtends[]) {}
}
