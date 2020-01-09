import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserExtends } from 'app/shared/model/user-extends.model';

type EntityResponseType = HttpResponse<IUserExtends>;
type EntityArrayResponseType = HttpResponse<IUserExtends[]>;

@Injectable({ providedIn: 'root' })
export class UserExtendsService {
  public resourceUrl = SERVER_API_URL + 'api/user-extends';

  constructor(protected http: HttpClient) {}

  create(userExtends: IUserExtends): Observable<EntityResponseType> {
    return this.http.post<IUserExtends>(this.resourceUrl, userExtends, { observe: 'response' });
  }

  update(userExtends: IUserExtends): Observable<EntityResponseType> {
    return this.http.put<IUserExtends>(this.resourceUrl, userExtends, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserExtends>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserExtends[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
