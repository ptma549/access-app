import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INumber, Number } from 'app/shared/model/number.model';
import { NumberService } from './number.service';
import { NumberComponent } from './number.component';
import { NumberDetailComponent } from './number-detail.component';
import { NumberUpdateComponent } from './number-update.component';

@Injectable({ providedIn: 'root' })
export class NumberResolve implements Resolve<INumber> {
  constructor(private service: NumberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INumber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((number: HttpResponse<Number>) => {
          if (number.body) {
            return of(number.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Number());
  }
}

export const numberRoute: Routes = [
  {
    path: '',
    component: NumberComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.number.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NumberDetailComponent,
    resolve: {
      number: NumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.number.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NumberUpdateComponent,
    resolve: {
      number: NumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.number.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NumberUpdateComponent,
    resolve: {
      number: NumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.number.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
