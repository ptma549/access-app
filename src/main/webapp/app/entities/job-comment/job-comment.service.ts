import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobComment } from 'app/shared/model/job-comment.model';

type EntityResponseType = HttpResponse<IJobComment>;
type EntityArrayResponseType = HttpResponse<IJobComment[]>;

@Injectable({ providedIn: 'root' })
export class JobCommentService {
  public resourceUrl = SERVER_API_URL + 'api/job-comments';

  constructor(protected http: HttpClient) {}

  create(jobComment: IJobComment): Observable<EntityResponseType> {
    return this.http.post<IJobComment>(this.resourceUrl, jobComment, { observe: 'response' });
  }

  update(jobComment: IJobComment): Observable<EntityResponseType> {
    return this.http.put<IJobComment>(this.resourceUrl, jobComment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJobComment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJobComment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
