import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Copy, CopyCheck, LucideAngularModule } from 'lucide-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonComponent } from './components/button/button.component';
import { LayoutComponent } from './components/layout/layout.component';
import { PaymentsTableComponent } from './components/payments-table/payments-table.component';
import { HomeComponent } from './home/home.component';
import { NewPaymentComponent } from './new-payment/new-payment.component';
import { PaymentComponent } from './payment/payment.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LayoutComponent,
    PaymentsTableComponent,
    ButtonComponent,
    NewPaymentComponent,
    PaymentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    LucideAngularModule.pick({Copy, CopyCheck})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
