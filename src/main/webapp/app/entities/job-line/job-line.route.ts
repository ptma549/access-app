import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobLine, JobLine } from 'app/shared/model/job-line.model';
import { JobLineService } from './job-line.service';
import { JobLineComponent } from './job-line.component';
import { JobLineDetailComponent } from './job-line-detail.component';
import { JobLineUpdateComponent } from './job-line-update.component';

@Injectable({ providedIn: 'root' })
export class JobLineResolve implements Resolve<IJobLine> {
  constructor(private service: JobLineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobLine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobLine: HttpResponse<JobLine>) => {
          if (jobLine.body) {
            return of(jobLine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobLine());
  }
}

export const jobLineRoute: Routes = [
  {
    path: '',
    component: JobLineComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.jobLine.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobLineDetailComponent,
    resolve: {
      jobLine: JobLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobLine.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobLineUpdateComponent,
    resolve: {
      jobLine: JobLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobLine.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobLineUpdateComponent,
    resolve: {
      jobLine: JobLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.jobLine.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
