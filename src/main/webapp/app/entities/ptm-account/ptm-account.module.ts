import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { PtmAccountComponent } from './ptm-account.component';
import { PtmAccountDetailComponent } from './ptm-account-detail.component';
import { PtmAccountUpdateComponent } from './ptm-account-update.component';
import { PtmAccountDeleteDialogComponent } from './ptm-account-delete-dialog.component';
import { ptmAccountRoute } from './ptm-account.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(ptmAccountRoute)],
  declarations: [PtmAccountComponent, PtmAccountDetailComponent, PtmAccountUpdateComponent, PtmAccountDeleteDialogComponent],
  entryComponents: [PtmAccountDeleteDialogComponent],
})
export class AccessAppPtmAccountModule {}
