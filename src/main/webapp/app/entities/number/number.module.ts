import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { NumberComponent } from './number.component';
import { NumberDetailComponent } from './number-detail.component';
import { NumberUpdateComponent } from './number-update.component';
import { NumberDeleteDialogComponent } from './number-delete-dialog.component';
import { numberRoute } from './number.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(numberRoute)],
  declarations: [NumberComponent, NumberDetailComponent, NumberUpdateComponent, NumberDeleteDialogComponent],
  entryComponents: [NumberDeleteDialogComponent],
})
export class AccessAppNumberModule {}
