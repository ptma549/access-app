import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { CountyComponent } from './county.component';
import { CountyDetailComponent } from './county-detail.component';
import { CountyUpdateComponent } from './county-update.component';
import { CountyDeleteDialogComponent } from './county-delete-dialog.component';
import { countyRoute } from './county.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(countyRoute)],
  declarations: [CountyComponent, CountyDetailComponent, CountyUpdateComponent, CountyDeleteDialogComponent],
  entryComponents: [CountyDeleteDialogComponent],
})
export class AccessAppCountyModule {}
