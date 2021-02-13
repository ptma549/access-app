import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobVisit, JobVisit } from 'app/shared/model/job-visit.model';
import { JobVisitService } from './job-visit.service';
import { JobVisitComponent } from './job-visit.component';
import { JobVisitDetailComponent } from './job-visit-detail.component';
import { JobVisitUpdateComponent } from './job-visit-update.component';

@Injectable({ providedIn: 'root' })
export class JobVisitResolve implements Resolve<IJobVisit> {
  constructor(private service: JobVisitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobVisit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobVisit: HttpResponse<JobVisit>) => {
          if (jobVisit.body) {
            return of(jobVisit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobVisit());
  }
}

export const jobVisitRoute: Routes = [
  {
    path: '',
    component: JobVisitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.jobVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobVisitDetailComponent,
    resolve: {
      jobVisit: JobVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobVisitUpdateComponent,
    resolve: {
      jobVisit: JobVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobVisitUpdateComponent,
    resolve: {
      jobVisit: JobVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
