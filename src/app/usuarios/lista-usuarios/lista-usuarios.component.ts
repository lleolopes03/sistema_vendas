import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.scss'
})
export class ListaUsuariosComponent {
  usuarios: any[] = [];
  usuariosFiltrados: any[] = []; // Lista filtrada para exibição
  pesquisaTermo: string = ''; // Termo de pesquisa
  mostrarModal: boolean = false;
  usuarioSelecionadoId: number | null = null;
  senhaAtual: string = '';
  novaSenha: string = '';
  confirmaSenha: string = '';
  isAdmin: boolean = false; // Propriedade para verificar se o usuário é admin
  userId: number | null = null; // ID do usuário logado
  userRole: string | null = localStorage.getItem('userRole');




  constructor(private http: HttpClient,private router: Router) {}

  ngOnInit(): void {
    this.verificarSeAdmin(); // Verifica o cargo do usuário logado
    this.carregarUsuarios();
  }
  verificarSeAdmin(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1])); // Decodifica o payload do JWT
      this.isAdmin = payload.role === 'ADMIN'; // Verifica se o usuário é admin
    }
  }
  carregarUsuarios(): void {
    const headers = { Authorization: `Bearer ${localStorage.getItem('token')}` };

    this.http.get<any[]>('/api/v1/usuarios', { headers }).subscribe({
      next: data => {
        this.usuarios = data;
        this.usuariosFiltrados = data; // Inicializa os resultados filtrados
      },
      error: err => console.error('Erro ao carregar usuários:', err),
    });
  }


  filtrarUsuarios(): void {
    const termo = this.pesquisaTermo.toLowerCase();
    this.usuariosFiltrados = this.usuarios.filter(user =>
      user.username.toLowerCase().includes(termo) || user.email.toLowerCase().includes(termo)
    );
  }



  abrirModal(id: number): void {
    this.usuarioSelecionadoId = id;
    this.mostrarModal = true; // Exibe o modal
  }

  fecharModal(): void {
    this.mostrarModal = false; // Oculta o modal
    this.usuarioSelecionadoId = null;
    this.senhaAtual = '';
    this.novaSenha = '';
    this.confirmaSenha = '';
  }

  confirmarEdicaoSenha(): void {
    const payload = {
      senhaAtual: this.senhaAtual,
      novaSenha: this.novaSenha,
      confirmaSenha: this.confirmaSenha,
    };

    const headers = {
      Authorization: `Bearer ${localStorage.getItem('token')}`, // Envia o token JWT
    };

    this.http.put(`/api/v1/usuarios/${this.usuarioSelecionadoId}/password`, payload, { headers }).subscribe({
      next: () => {
        alert('Senha atualizada com sucesso!');
        this.fecharModal();
      },
      error: err => {
        if (err.status === 401) {
          alert('Erro de autenticação. Por favor, faça login novamente.');
        } else {
          alert('Erro ao atualizar senha. Verifique as informações.');
        }
        console.error('Erro ao atualizar senha:', err);
      },
    });
  }

  deletarUsuario(id: number): void {
    if (confirm('Tem certeza que deseja deletar este usuário?')) {
      const headers = { Authorization: `Bearer ${localStorage.getItem('token')}` };

      this.http.delete(`/api/v1/usuarios/${id}`, { headers }).subscribe({
        next: () => {
          alert('Usuário deletado com sucesso!');
          this.carregarUsuarios(); // Recarrega a lista após deletar
        },
        error: err => console.error('Erro ao deletar usuário:', err),
      });
    }
  }
  voltarParaHomepage(): void {
    this.router.navigate(['/']); // Altere a rota conforme sua configuração
  }


}
