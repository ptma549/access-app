import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AccessAppSharedModule } from 'app/shared/shared.module';
import { AccessAppCoreModule } from 'app/core/core.module';
import { AccessAppAppRoutingModule } from './app-routing.module';
import { AccessAppHomeModule } from './home/home.module';
import { AccessAppEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AccessAppSharedModule,
    AccessAppCoreModule,
    AccessAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AccessAppEntityModule,
    AccessAppAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class AccessAppAppModule {}
