import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDatabaseConnection } from 'app/shared/model/database-connection.model';

type EntityResponseType = HttpResponse<IDatabaseConnection>;
type EntityArrayResponseType = HttpResponse<IDatabaseConnection[]>;

@Injectable({ providedIn: 'root' })
export class DatabaseConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/database-connections';

  constructor(protected http: HttpClient) {}

  create(databaseConnection: IDatabaseConnection): Observable<EntityResponseType> {
    return this.http.post<IDatabaseConnection>(this.resourceUrl, databaseConnection, { observe: 'response' });
  }

  update(databaseConnection: IDatabaseConnection): Observable<EntityResponseType> {
    return this.http.put<IDatabaseConnection>(this.resourceUrl, databaseConnection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatabaseConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatabaseConnection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
