import { IUser } from 'app/core/user/user.model';
import { IRole } from 'app/shared/model/role.model';

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export interface IUserExtends {
  id?: number;
  username?: string;
  gender?: Gender;
  mobile?: string;
  user?: IUser;
  roles?: IRole[];
}

export class UserExtends implements IUserExtends {
  constructor(
    public id?: number,
    public username?: string,
    public gender?: Gender,
    public mobile?: string,
    public user?: IUser,
    public roles?: IRole[]
  ) {}
}
