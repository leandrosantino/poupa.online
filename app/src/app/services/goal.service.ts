import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { GoalEntity } from '../entities/GoalEntity';

@Injectable({
  providedIn: 'root'
})
export class GoalService {

  private url = `${environment.API_URL}/goal` 

  constructor(private httpClient: HttpClient) { }

  getAll() : Observable<GoalEntity[]> {
    return this.httpClient.get<GoalEntity[]>(this.url)
  }

}
