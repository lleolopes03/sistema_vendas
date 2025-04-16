import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Injeta o serviço de autenticação
  const router = inject(Router); // Injeta o roteador para redirecionamento

  const token = authService.getToken(); // Obtém o token do localStorage

  if (token) {
    return true; // Usuário autenticado, permite acesso
  }

  router.navigate(['/login']); // Redireciona para login se não estiver autenticado
  return false; // Bloqueia acesso à rota
};

