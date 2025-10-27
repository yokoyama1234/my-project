import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

interface Product {
  id: number;
  name: string;
  price: number;
}

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.html',
  styleUrls: ['./welcome.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule]
})
export class WelcomeComponent implements OnInit {
  name: string = '';
  products: Product[] = [];
  error = '';

  constructor(private router: Router, private http: HttpClient) {
    const navigation = this.router.getCurrentNavigation();
    this.name = navigation?.extras.state?.['name'] || 'ゲスト';
  }

  ngOnInit() {
    // MyBatis経由でバックエンドのDBデータを取得
    this.http.get<Product[]>('http://localhost:8080/api/products', { withCredentials: true })
      .subscribe({
        next: data => this.products = data,
        error: err => this.error = '商品データの取得に失敗しました'
      });
  }

  logout() {
    // ログアウト後にログイン画面へ
    this.router.navigate(['/']);
  }
}
