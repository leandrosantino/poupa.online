import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription, catchError, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PaymentEntity } from '../entities/PaymentEntity';
import { UserEntity } from '../entities/UserEntity';
import { PaymentService } from '../services/payment.service';
import { UserService } from '../services/user.service';

const CPF_PATTERN = new RegExp(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)

@Component({
  templateUrl: './new-payment.component.html'
})
export class NewPaymentComponent implements OnInit, OnDestroy{

  paymentsSearchForm!: FormGroup
  userData?: UserEntity
  
  userRequestSubscription!: Subscription

  payments$!: Observable<PaymentEntity[]>

  userRequestIsError: boolean = false

  constructor (
    private userService: UserService,
    private paymentService: PaymentService
  ) {}

  ngOnInit() {
    this.paymentsSearchForm = new FormGroup({
      userCpf: new FormControl(environment.production ? '' : '112.911.774-', [
        Validators.required,
        Validators.pattern(CPF_PATTERN),
      ])
    })
  }

  ngOnDestroy() {
    this.userRequestSubscription.unsubscribe()
  }

  get userCpf() {
    return this.paymentsSearchForm.get('userCpf')!
  }

  formatCpf () {
    const cpf = this.userCpf.value.replace(/[^\d]/g, "");
    this.paymentsSearchForm.setValue({
      'userCpf':  cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4")
    })
  }

  submit(){
    this.userRequestIsError= false
    this.userData = undefined
    
    if(this.paymentsSearchForm.invalid) return;
    this.userRequestSubscription = this.userService.getByCpf(this.userCpf.value).pipe(
      catchError(() =>{
        this.userRequestIsError= true
        return of()
      })
    ).subscribe(({data: user}) => {
      this.userData = user
      this.payments$ = this.paymentService.getByUserId(user.id)
    })

  }

}
