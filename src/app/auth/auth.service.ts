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

    try {
      // Decodifica o payload do token JWT
      const payload = JSON.parse(atob(token.split('.')[1]));

      console.log('Payload JWT:', payload); // Log para depuração

      if (payload.sub) {
        localStorage.setItem('username', payload.sub);
      } else {
        console.error('O campo "sub" está ausente no token JWT.');
      }

      if (payload.id) {
        localStorage.setItem('userId', payload.id.toString()); // Salva o ID
      }

      if (payload.role) {
        localStorage.setItem('userRole', payload.role); // Salva o papel do usuário
      }
    } catch (error) {
      console.error('Erro ao decodificar o JWT:', error);
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
