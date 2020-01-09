import { Moment } from 'moment';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';

export interface IDataFields {
  id?: number;
  fieldName?: string;
  fieldType?: string;
  length?: number;
  scale?: number;
  allowNull?: boolean;
  primaryKey?: boolean;
  remarks?: string;
  stop?: boolean;
  createDate?: Moment;
  updateDate?: Moment;
  modifyDate?: Moment;
  dataCatalog?: IDataCatalog;
}

export class DataFields implements IDataFields {
  constructor(
    public id?: number,
    public fieldName?: string,
    public fieldType?: string,
    public length?: number,
    public scale?: number,
    public allowNull?: boolean,
    public primaryKey?: boolean,
    public remarks?: string,
    public stop?: boolean,
    public createDate?: Moment,
    public updateDate?: Moment,
    public modifyDate?: Moment,
    public dataCatalog?: IDataCatalog
  ) {
    this.allowNull = this.allowNull || false;
    this.primaryKey = this.primaryKey || false;
    this.stop = this.stop || false;
  }
}
