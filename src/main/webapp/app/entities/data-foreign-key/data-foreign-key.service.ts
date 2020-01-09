import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataForeignKey } from 'app/shared/model/data-foreign-key.model';

type EntityResponseType = HttpResponse<IDataForeignKey>;
type EntityArrayResponseType = HttpResponse<IDataForeignKey[]>;

@Injectable({ providedIn: 'root' })
export class DataForeignKeyService {
  public resourceUrl = SERVER_API_URL + 'api/data-foreign-keys';

  constructor(protected http: HttpClient) {}

  create(dataForeignKey: IDataForeignKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataForeignKey);
    return this.http
      .post<IDataForeignKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dataForeignKey: IDataForeignKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataForeignKey);
    return this.http
      .put<IDataForeignKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDataForeignKey>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDataForeignKey[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dataForeignKey: IDataForeignKey): IDataForeignKey {
    const copy: IDataForeignKey = Object.assign({}, dataForeignKey, {
      createDate: dataForeignKey.createDate != null && dataForeignKey.createDate.isValid() ? dataForeignKey.createDate.toJSON() : null,
      updateDate: dataForeignKey.updateDate != null && dataForeignKey.updateDate.isValid() ? dataForeignKey.updateDate.toJSON() : null,
      modifyDate: dataForeignKey.modifyDate != null && dataForeignKey.modifyDate.isValid() ? dataForeignKey.modifyDate.toJSON() : null
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
      res.body.forEach((dataForeignKey: IDataForeignKey) => {
        dataForeignKey.createDate = dataForeignKey.createDate != null ? moment(dataForeignKey.createDate) : null;
        dataForeignKey.updateDate = dataForeignKey.updateDate != null ? moment(dataForeignKey.updateDate) : null;
        dataForeignKey.modifyDate = dataForeignKey.modifyDate != null ? moment(dataForeignKey.modifyDate) : null;
      });
    }
    return res;
  }
}
