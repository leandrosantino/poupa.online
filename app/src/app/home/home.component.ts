import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GoalEntity } from '../entities/GoalEntity';
import { PaymentEntity } from '../entities/PaymentEntity';
import { GoalService } from '../services/goal.service';
import { PaymentService } from '../services/payment.service';
import { UtilsService } from '../services/utils.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit{

  constructor (
    private goalService: GoalService,
    private paymentService: PaymentService,
    private utilsService: UtilsService
  ){}

  currentGoal? : GoalEntity = undefined
  amountCollected: number = 0
  payments$!: Observable<PaymentEntity[]>

  formateGoalValue(value: number){
    return this.utilsService.formatCentsNumberToCurrency(value)
  }
  
  ngOnInit(): void {
    this.goalService.getAll().subscribe((goals) => {
      this.currentGoal = goals[0]

      this.paymentService.getTotalOfPayedPayments(this.currentGoal.id).subscribe(({data})=>{
        this.amountCollected = data
      })

      this.payments$ = this.paymentService.getLastPayedPayments(this.currentGoal.id)

    })
  }

}
