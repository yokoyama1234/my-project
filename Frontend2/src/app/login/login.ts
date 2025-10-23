import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpResponse } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

interface LoginResponse {
  status: number;
  message: string;
  userId?: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule]
})
export class LoginComponent {
  userId: string = '';
  password: string = '';
  message: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onLogin() {
    this.message = '';

    this.http.post<LoginResponse>(
      'http://localhost:8080/api/login',
      { userId: this.userId, password: this.password },
      { observe: 'response', withCredentials: true }
    ).subscribe({
      next: (res: HttpResponse<LoginResponse>) => {
        const body = res.body;
        if (body && body.status === 200) {
          this.router.navigate(['/welcome'], { state: { userId: body.userId } });
        } else {
          this.message = body?.message || '予期せぬレスポンスです';
        }
      },
      error: (err) => {
        const body: LoginResponse = err.error;
        this.message = body?.message || 'サーバ通信エラーが発生しました';
      }
    });
  }
}
