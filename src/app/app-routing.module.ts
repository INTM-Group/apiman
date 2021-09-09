import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {MarketplaceApiDetailsComponent} from "./components/marketplace-api-details/marketplace-api-details.component";
import {MarketplaceSignupStepperComponent} from "./components/marketplace-signup-stepper/marketplace-signup-stepper.component";
import {MarketplaceComponent} from "./components/marketplace/marketplace.component";
import {AccountComponent} from './components/account/account.component';
import {MyAppsComponent} from './components/my-apps/my-apps.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'marketplace', component: MarketplaceComponent},
  {path: 'api-details/:orgId/:apiId', component: MarketplaceApiDetailsComponent},
  {path: 'api-signup', component: MarketplaceSignupStepperComponent},
  {path: 'account', component: AccountComponent},
  {path: 'applications', component: MyAppsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
