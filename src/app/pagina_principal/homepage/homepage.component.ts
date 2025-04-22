import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [RouterModule,FormsModule,CommonModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {
  username: string | null = null;
  password: string | null = null;
  errorMessage: string = '';


  constructor(private authService: AuthService, private router: Router) {}

  openLogin() {
    const modal = document.querySelector('.modal-login') as HTMLDivElement;

    // Limpa os valores dos inputs ao abrir o modal
    const usernameInput = document.querySelector('#username') as HTMLInputElement;
    const passwordInput = document.querySelector('#password') as HTMLInputElement;

    if (usernameInput) usernameInput.value = '';
    if (passwordInput) passwordInput.value = ''; // Limpa explicitamente o campo de senha

    modal?.classList.add('active');
  }


  closeLogin() {
    const modal = document.querySelector('.modal-login');
    modal?.classList.remove('active');
  }
  onLogin() {
    this.authService.login(this.username!, this.password!).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token); // Salva o token JWT
        this.username = this.authService.getUsername(); // Atualiza o username
        this.router.navigate(['/']); // Redireciona para a rota inicial
        this.closeLogin(); // Fecha o modal após o login bem-sucedido
      },
      error: () => {
        this.errorMessage = 'Username ou senha inválidos!';
        this.username = null; // Reseta o username
        this.password = null; // Adiciona a lógica para limpar o campo de senha
        console.error('Falha no login. Campos resetados.');
      },
    });
  }

  ngOnInit() {
    this.username = this.authService.getUsername(); // Carrega sub (ou username, dependendo do ajuste acima)
    console.log('Username carregado no componente:', this.username);
  }

  logout() {
    this.authService.logout(); // Limpa os dados de autenticação
    this.username = null; // Reseta o nome do usuário exibido
    console.log('Logout realizado. Username resetado.');
  }







  }







