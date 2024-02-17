import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { PaymentEntity } from 'src/app/entities/PaymentEntity';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'payments-table',
  templateUrl: './payments-table.component.html'
})
export class PaymentsTableComponent {
  @Input() payments$!: Observable<PaymentEntity[]>

  constructor(
    private utilsService: UtilsService,
    private router: Router 
  ){}


  formatPaymentValue(value: number){
    return this.utilsService.formatCentsNumberToCurrency(value)
  }

  formatDate(date: number[] | null){
    if(date == null){
      return " - "
    }
    return this.utilsService.formatApiDate(date).toLocaleDateString()
  }

  formatStatus(status: string){
    if(status === "COMPLETED") {
      return "Pago"
    }
    if(status === "PROCESSING") {
      return "Aguardando Pagamento"
    }
    if(status === "PENDING") {
      return "Pendente"
    }
    return ""
  }

  goToPaymentPage(id:string, status: string){
    if(status != 'COMPLETED'){
      this.router.navigateByUrl(`/payment/${id}`)
    }
  }

}
