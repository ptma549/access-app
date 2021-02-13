import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INumber } from 'app/shared/model/number.model';

type EntityResponseType = HttpResponse<INumber>;
type EntityArrayResponseType = HttpResponse<INumber[]>;

@Injectable({ providedIn: 'root' })
export class NumberService {
  public resourceUrl = SERVER_API_URL + 'api/numbers';

  constructor(protected http: HttpClient) {}

  create(number: INumber): Observable<EntityResponseType> {
    return this.http.post<INumber>(this.resourceUrl, number, { observe: 'response' });
  }

  update(number: INumber): Observable<EntityResponseType> {
    return this.http.put<INumber>(this.resourceUrl, number, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INumber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INumber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
