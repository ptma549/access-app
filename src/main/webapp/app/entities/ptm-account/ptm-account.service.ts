import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPtmAccount } from 'app/shared/model/ptm-account.model';

type EntityResponseType = HttpResponse<IPtmAccount>;
type EntityArrayResponseType = HttpResponse<IPtmAccount[]>;

@Injectable({ providedIn: 'root' })
export class PtmAccountService {
  public resourceUrl = SERVER_API_URL + 'api/ptm-accounts';

  constructor(protected http: HttpClient) {}

  create(ptmAccount: IPtmAccount): Observable<EntityResponseType> {
    return this.http.post<IPtmAccount>(this.resourceUrl, ptmAccount, { observe: 'response' });
  }

  update(ptmAccount: IPtmAccount): Observable<EntityResponseType> {
    return this.http.put<IPtmAccount>(this.resourceUrl, ptmAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPtmAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPtmAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
