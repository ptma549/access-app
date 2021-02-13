import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class AccessAppHomeModule {}
