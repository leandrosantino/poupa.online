import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpResponseEntity } from '../entities/HttpResponseEntity';
import { MakePaymenteDataEntity } from '../entities/MakePaymenteDataEntity';
import { PaymentEntity } from '../entities/PaymentEntity';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private url = `${environment.API_URL}/payment` 

  constructor(private httpClient: HttpClient) {}

  getByUserId(userId: string) : Observable<PaymentEntity[]> {
    return this.httpClient.get<PaymentEntity[]>(`${this.url}/user/${userId}`)
  }

  getMakePaymentData(paymentId: string) : Observable< HttpResponseEntity<MakePaymenteDataEntity> >{
    return this.httpClient.get<HttpResponseEntity<MakePaymenteDataEntity>>(`${this.url}/${paymentId}`)
  }

  getTotalOfPayedPayments(goalId: string) : Observable< HttpResponseEntity<number> >{
    return this.httpClient.get< HttpResponseEntity<number> >(`${this.url}/total/${goalId}`)
  }

  getLastPayedPayments(goalId: string) : Observable<PaymentEntity[]>{
    return this.httpClient.get<PaymentEntity[]>(`${this.url}/payed/${goalId}`)
  }


}
