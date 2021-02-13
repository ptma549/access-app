import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobVisit } from 'app/shared/model/job-visit.model';

type EntityResponseType = HttpResponse<IJobVisit>;
type EntityArrayResponseType = HttpResponse<IJobVisit[]>;

@Injectable({ providedIn: 'root' })
export class JobVisitService {
  public resourceUrl = SERVER_API_URL + 'api/job-visits';

  constructor(protected http: HttpClient) {}

  create(jobVisit: IJobVisit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobVisit);
    return this.http
      .post<IJobVisit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobVisit: IJobVisit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobVisit);
    return this.http
      .put<IJobVisit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobVisit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobVisit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobVisit: IJobVisit): IJobVisit {
    const copy: IJobVisit = Object.assign({}, jobVisit, {
      arrived: jobVisit.arrived && jobVisit.arrived.isValid() ? jobVisit.arrived.toJSON() : undefined,
      departed: jobVisit.departed && jobVisit.departed.isValid() ? jobVisit.departed.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.arrived = res.body.arrived ? moment(res.body.arrived) : undefined;
      res.body.departed = res.body.departed ? moment(res.body.departed) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobVisit: IJobVisit) => {
        jobVisit.arrived = jobVisit.arrived ? moment(jobVisit.arrived) : undefined;
        jobVisit.departed = jobVisit.departed ? moment(jobVisit.departed) : undefined;
      });
    }
    return res;
  }
}
