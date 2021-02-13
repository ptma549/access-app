import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPtmAccount, PtmAccount } from 'app/shared/model/ptm-account.model';
import { PtmAccountService } from './ptm-account.service';
import { PtmAccountComponent } from './ptm-account.component';
import { PtmAccountDetailComponent } from './ptm-account-detail.component';
import { PtmAccountUpdateComponent } from './ptm-account-update.component';

@Injectable({ providedIn: 'root' })
export class PtmAccountResolve implements Resolve<IPtmAccount> {
  constructor(private service: PtmAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPtmAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ptmAccount: HttpResponse<PtmAccount>) => {
          if (ptmAccount.body) {
            return of(ptmAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PtmAccount());
  }
}

export const ptmAccountRoute: Routes = [
  {
    path: '',
    component: PtmAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.ptmAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PtmAccountDetailComponent,
    resolve: {
      ptmAccount: PtmAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.ptmAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PtmAccountUpdateComponent,
    resolve: {
      ptmAccount: PtmAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.ptmAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PtmAccountUpdateComponent,
    resolve: {
      ptmAccount: PtmAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.ptmAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
