import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITown, Town } from 'app/shared/model/town.model';
import { TownService } from './town.service';
import { TownComponent } from './town.component';
import { TownDetailComponent } from './town-detail.component';
import { TownUpdateComponent } from './town-update.component';

@Injectable({ providedIn: 'root' })
export class TownResolve implements Resolve<ITown> {
  constructor(private service: TownService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITown> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((town: HttpResponse<Town>) => {
          if (town.body) {
            return of(town.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Town());
  }
}

export const townRoute: Routes = [
  {
    path: '',
    component: TownComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'accessApp.town.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TownDetailComponent,
    resolve: {
      town: TownResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.town.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TownUpdateComponent,
    resolve: {
      town: TownResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.town.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TownUpdateComponent,
    resolve: {
      town: TownResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'accessApp.town.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
