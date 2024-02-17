import { Injectable } from '@angular/core';

const CENTS_MULTIPLE = 100

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor() { }

  formatCentsNumberToCurrency(cents: number): string{
    const fractionalValue = cents / CENTS_MULTIPLE
    const BRLFormat = new Intl.NumberFormat('pt-br', {
      style: 'currency',
      currency: 'BRL'
    })
    return BRLFormat.format(fractionalValue)
  }

  formatApiDate(apiDate : number[]){
    const [year, month, day, hours, minutes, seconds] = apiDate
    return new Date(year, month - 1, day, hours, minutes, seconds)
  }
}
