import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Importando CommonModule

@Component({
  selector: 'app-listar-funcionarios',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './listar-funcionarios.component.html',
  styleUrl: './listar-funcionarios.component.scss'
})
export class ListarFuncionariosComponent {

   funcionarios: any[] = [];
      funcionariosFiltrados: any[] = [];
      filtroNome: string = '';
      userRole: string | null = localStorage.getItem('userRole');
      constructor(private http: HttpClient,private router: Router) {}

      ngOnInit() {
        this.carregarFuncionarios();
      }

      carregarFuncionarios() {
        this.http.get<any[]>('/api/v1/funcionarios').subscribe(response => {
          this.funcionarios = response;
          this.funcionariosFiltrados = response; // Inicializa a lista filtrada
        });
      }

      filtrarFuncionarios() {
        this.funcionariosFiltrados = this.funcionarios.filter(funcionario =>
          funcionario.nome.toLowerCase().includes(this.filtroNome.toLowerCase())
        );
      }

      editarFuncionario(funcionarioId: number) {
        alert(`Editar funcionario ID: ${funcionarioId}`);
        // Aqui você pode navegar para uma página de edição
      }

      deletarFuncionario(funcionarioId: number) {
        if (confirm('Tem certeza que deseja excluir este funcionario?')) {
          this.http.delete(`/api/v1/funcionarios/${funcionarioId}`).subscribe(() => {
            alert('Cliente excluído com sucesso!');
            this.carregarFuncionarios(); // Atualiza a lista
          });
        }
      }
      voltarParaHomepage(): void {
        this.router.navigate(['/']); // Altere a rota conforme sua configuração
      }


}
