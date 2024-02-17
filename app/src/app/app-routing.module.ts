import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { NewPaymentComponent } from './new-payment/new-payment.component';
import { PaymentComponent } from './payment/payment.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'newpayment', component: NewPaymentComponent },
  {path: 'payment/:id', component: PaymentComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
