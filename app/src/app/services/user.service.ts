
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpResponseEntity } from '../entities/HttpResponseEntity';
import { UserEntity } from '../entities/UserEntity';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private url = `${environment.API_URL}/user` 
 
  constructor(private httpClient: HttpClient) { }

  getAll() : Observable<UserEntity[]> {
    return this.httpClient.get<UserEntity[]>(this.url)
  }

  getByCpf(cpf: string) : Observable<HttpResponseEntity<UserEntity>> { 
    return this.httpClient.get<HttpResponseEntity<UserEntity>>(`${this.url}/${cpf}`)
  }

}
 