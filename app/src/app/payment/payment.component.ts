import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription, catchError, of } from 'rxjs';
import { MakePaymenteDataEntity } from '../entities/MakePaymenteDataEntity';
import { PaymentService } from '../services/payment.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html'
})
export class PaymentComponent implements OnInit, OnDestroy{

  paymentId!: string

  makePaymentDataSubscription! : Subscription
  makePaymentData!: MakePaymenteDataEntity

  copyIconName: string = "copy"

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService
  ){}

  copyToClipBoard(){
    this.copyIconName = "copyCheck"
    navigator.clipboard.writeText(this.makePaymentData.qrCode)
    setTimeout(() => {
      this.copyIconName = "copy"
    }, 1500)
  }
  

  ngOnDestroy(): void {
    this.makePaymentDataSubscription.unsubscribe()
  }

  ngOnInit(): void {
    this.paymentId = this.route.snapshot.paramMap.get('id') as string
    console.log(this.paymentId)

    this.makePaymentDataSubscription = this.paymentService.getMakePaymentData(this.paymentId)
      .pipe(
        catchError((data) =>{
          console.log(data)
          return of()
        })
      )
      .subscribe(({data}) => {
        console.log(data)
        this.makePaymentData = data
      })

  }

  getDateOfExpiration(){
    const [year, month, day, hour, minute] = this.makePaymentData.dateOfExpiration
    return new Date(year, month - 1, day, hour, minute).toLocaleString()
  }

}
