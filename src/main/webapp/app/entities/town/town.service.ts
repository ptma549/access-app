import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITown } from 'app/shared/model/town.model';

type EntityResponseType = HttpResponse<ITown>;
type EntityArrayResponseType = HttpResponse<ITown[]>;

@Injectable({ providedIn: 'root' })
export class TownService {
  public resourceUrl = SERVER_API_URL + 'api/towns';

  constructor(protected http: HttpClient) {}

  create(town: ITown): Observable<EntityResponseType> {
    return this.http.post<ITown>(this.resourceUrl, town, { observe: 'response' });
  }

  update(town: ITown): Observable<EntityResponseType> {
    return this.http.put<ITown>(this.resourceUrl, town, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITown>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITown[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
