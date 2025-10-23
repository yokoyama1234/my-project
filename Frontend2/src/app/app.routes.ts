import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { WelcomeComponent } from './welcome/welcome';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'welcome', component: WelcomeComponent },
];
