import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ptm-account',
        loadChildren: () => import('./ptm-account/ptm-account.module').then(m => m.AccessAppPtmAccountModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.AccessAppClientModule),
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.AccessAppCompanyModule),
      },
      {
        path: 'county',
        loadChildren: () => import('./county/county.module').then(m => m.AccessAppCountyModule),
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.AccessAppJobModule),
      },
      {
        path: 'job-comment',
        loadChildren: () => import('./job-comment/job-comment.module').then(m => m.AccessAppJobCommentModule),
      },
      {
        path: 'job-line',
        loadChildren: () => import('./job-line/job-line.module').then(m => m.AccessAppJobLineModule),
      },
      {
        path: 'job-visit',
        loadChildren: () => import('./job-visit/job-visit.module').then(m => m.AccessAppJobVisitModule),
      },
      {
        path: 'number',
        loadChildren: () => import('./number/number.module').then(m => m.AccessAppNumberModule),
      },
      {
        path: 'position',
        loadChildren: () => import('./position/position.module').then(m => m.AccessAppPositionModule),
      },
      {
        path: 'priority',
        loadChildren: () => import('./priority/priority.module').then(m => m.AccessAppPriorityModule),
      },
      {
        path: 'street',
        loadChildren: () => import('./street/street.module').then(m => m.AccessAppStreetModule),
      },
      {
        path: 'town',
        loadChildren: () => import('./town/town.module').then(m => m.AccessAppTownModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AccessAppEntityModule {}
