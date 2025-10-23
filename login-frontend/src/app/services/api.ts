import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api'; // バックエンド URL

  constructor(private http: HttpClient) {}

  login(userId: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, { userId, password }, { withCredentials: true });
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/products`, { withCredentials: true });
  }
}
