import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataFields } from 'app/shared/model/data-fields.model';

type EntityResponseType = HttpResponse<IDataFields>;
type EntityArrayResponseType = HttpResponse<IDataFields[]>;

@Injectable({ providedIn: 'root' })
export class DataFieldsService {
  public resourceUrl = SERVER_API_URL + 'api/data-fields';

  constructor(protected http: HttpClient) {}

  create(dataFields: IDataFields): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataFields);
    return this.http
      .post<IDataFields>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dataFields: IDataFields): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataFields);
    return this.http
      .put<IDataFields>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDataFields>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDataFields[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dataFields: IDataFields): IDataFields {
    const copy: IDataFields = Object.assign({}, dataFields, {
      createDate: dataFields.createDate != null && dataFields.createDate.isValid() ? dataFields.createDate.toJSON() : null,
      updateDate: dataFields.updateDate != null && dataFields.updateDate.isValid() ? dataFields.updateDate.toJSON() : null,
      modifyDate: dataFields.modifyDate != null && dataFields.modifyDate.isValid() ? dataFields.modifyDate.toJSON() : null
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
      res.body.forEach((dataFields: IDataFields) => {
        dataFields.createDate = dataFields.createDate != null ? moment(dataFields.createDate) : null;
        dataFields.updateDate = dataFields.updateDate != null ? moment(dataFields.updateDate) : null;
        dataFields.modifyDate = dataFields.modifyDate != null ? moment(dataFields.modifyDate) : null;
      });
    }
    return res;
  }
}
