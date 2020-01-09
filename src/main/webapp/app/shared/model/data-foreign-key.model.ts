import { Moment } from 'moment';
import { IDataCatalog } from 'app/shared/model/data-catalog.model';

export interface IDataForeignKey {
  id?: number;
  name?: string;
  field?: string;
  referenceTable?: string;
  referenceField?: string;
  remarks?: string;
  stop?: boolean;
  createDate?: Moment;
  updateDate?: Moment;
  modifyDate?: Moment;
  dataCatalog?: IDataCatalog;
}

export class DataForeignKey implements IDataForeignKey {
  constructor(
    public id?: number,
    public name?: string,
    public field?: string,
    public referenceTable?: string,
    public referenceField?: string,
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
