import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobComment, JobComment } from 'app/shared/model/job-comment.model';
import { JobCommentService } from './job-comment.service';
import { JobCommentComponent } from './job-comment.component';
import { JobCommentDetailComponent } from './job-comment-detail.component';
import { JobCommentUpdateComponent } from './job-comment-update.component';

@Injectable({ providedIn: 'root' })
export class JobCommentResolve implements Resolve<IJobComment> {
  constructor(private service: JobCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobComment: HttpResponse<JobComment>) => {
          if (jobComment.body) {
            return of(jobComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobComment());
  }
}

export const jobCommentRoute: Routes = [
  {
    path: '',
    component: JobCommentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.jobComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobCommentDetailComponent,
    resolve: {
      jobComment: JobCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobCommentUpdateComponent,
    resolve: {
      jobComment: JobCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobCommentUpdateComponent,
    resolve: {
      jobComment: JobCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
