import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { StreetComponent } from './street.component';
import { StreetDetailComponent } from './street-detail.component';
import { StreetUpdateComponent } from './street-update.component';
import { StreetDeleteDialogComponent } from './street-delete-dialog.component';
import { streetRoute } from './street.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(streetRoute)],
  declarations: [StreetComponent, StreetDetailComponent, StreetUpdateComponent, StreetDeleteDialogComponent],
  entryComponents: [StreetDeleteDialogComponent],
})
export class AccessAppStreetModule {}
