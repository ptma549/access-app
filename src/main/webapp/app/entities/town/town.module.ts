import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { TownComponent } from './town.component';
import { TownDetailComponent } from './town-detail.component';
import { TownUpdateComponent } from './town-update.component';
import { TownDeleteDialogComponent } from './town-delete-dialog.component';
import { townRoute } from './town.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(townRoute)],
  declarations: [TownComponent, TownDetailComponent, TownUpdateComponent, TownDeleteDialogComponent],
  entryComponents: [TownDeleteDialogComponent],
})
export class AccessAppTownModule {}
