import { Moment } from 'moment';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';

export interface IDataPrimaryKey {
  id?: number;
  name?: string;
  fields?: string;
  remarks?: string;
  stop?: boolean;
  createDate?: Moment;
  updateDate?: Moment;
  modifyDate?: Moment;
  dataCatalog?: IDataCatalog;
}

export class DataPrimaryKey implements IDataPrimaryKey {
  constructor(
    public id?: number,
    public name?: string,
    public fields?: string,
    public remarks?: string,
    public stop?: boolean,
    public createDate?: Moment,
    public updateDate?: Moment,
    public modifyDate?: Moment,
    public dataCatalog?: IDataCatalog
  ) {
    this.stop = this.stop || false;
  }
}
