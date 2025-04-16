import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';



@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.http.post(`${environment.apiBaseUrl}/v1/auth`, body);
  }
  saveToken(token: string): void {
    localStorage.setItem('token', token);

    // Decodifica o payload do token JWT
    const payload = JSON.parse(atob(token.split('.')[1]));
    console.log('Payload JWT:', payload); // Log para depuração

    if (payload.sub) { // Usa sub como identificador
      localStorage.setItem('username', payload.sub); // Salva o sub no localStorage como username
      console.log('Username salvo no localStorage:', payload.sub);
    } else {
      console.error('O campo "sub" está ausente no token JWT.');
    }
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  logout(): void {
    // Remove os dados do localStorage ao realizar logout
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    console.log('Logout realizado. Dados de autenticação foram limpos.');
  }


  // Obtém o token armazenado


  // Remove o token (logout)
  clearToken(): void {
    localStorage.removeItem('token');
  }



}
