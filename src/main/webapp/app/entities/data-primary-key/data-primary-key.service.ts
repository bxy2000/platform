import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataPrimaryKey } from 'app/shared/model/data-primary-key.model';

type EntityResponseType = HttpResponse<IDataPrimaryKey>;
type EntityArrayResponseType = HttpResponse<IDataPrimaryKey[]>;

@Injectable({ providedIn: 'root' })
export class DataPrimaryKeyService {
  public resourceUrl = SERVER_API_URL + 'api/data-primary-keys';

  constructor(protected http: HttpClient) {}

  create(dataPrimaryKey: IDataPrimaryKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataPrimaryKey);
    return this.http
      .post<IDataPrimaryKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dataPrimaryKey: IDataPrimaryKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataPrimaryKey);
    return this.http
      .put<IDataPrimaryKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDataPrimaryKey>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDataPrimaryKey[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dataPrimaryKey: IDataPrimaryKey): IDataPrimaryKey {
    const copy: IDataPrimaryKey = Object.assign({}, dataPrimaryKey, {
      createDate: dataPrimaryKey.createDate != null && dataPrimaryKey.createDate.isValid() ? dataPrimaryKey.createDate.toJSON() : null,
      updateDate: dataPrimaryKey.updateDate != null && dataPrimaryKey.updateDate.isValid() ? dataPrimaryKey.updateDate.toJSON() : null,
      modifyDate: dataPrimaryKey.modifyDate != null && dataPrimaryKey.modifyDate.isValid() ? dataPrimaryKey.modifyDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate != null ? moment(res.body.createDate) : null;
      res.body.updateDate = res.body.updateDate != null ? moment(res.body.updateDate) : null;
      res.body.modifyDate = res.body.modifyDate != null ? moment(res.body.modifyDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dataPrimaryKey: IDataPrimaryKey) => {
        dataPrimaryKey.createDate = dataPrimaryKey.createDate != null ? moment(dataPrimaryKey.createDate) : null;
        dataPrimaryKey.updateDate = dataPrimaryKey.updateDate != null ? moment(dataPrimaryKey.updateDate) : null;
        dataPrimaryKey.modifyDate = dataPrimaryKey.modifyDate != null ? moment(dataPrimaryKey.modifyDate) : null;
      });
    }
    return res;
  }
}
