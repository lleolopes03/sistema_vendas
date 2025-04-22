import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Importando CommonModule
import { Router, RouterModule } from '@angular/router';




@Component({
  selector: 'app-lista-clientes',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule ],
  templateUrl: './lista-clientes.component.html',
  styleUrl: './lista-clientes.component.scss'
})
export class ListaClientesComponent {
    clientes: any[] = [];
    clientesFiltrados: any[] = [];
    filtroNome: string = '';
    userRole: string | null = localStorage.getItem('userRole');
    constructor(private http: HttpClient,private router: Router) {}

    ngOnInit() {
      this.carregarClientes();
    }

    carregarClientes() {
      this.http.get<any[]>('/api/v1/clientes').subscribe(response => {
        this.clientes = response;
        this.clientesFiltrados = response; // Inicializa a lista filtrada
      });
    }

    filtrarClientes() {
      this.clientesFiltrados = this.clientes.filter(cliente =>
        cliente.nome.toLowerCase().includes(this.filtroNome.toLowerCase())
      );
    }

    editarCliente(clienteId: number) {
      alert(`Editar cliente ID: ${clienteId}`);
      // Aqui você pode navegar para uma página de edição
    }

    deletarCliente(clienteId: number) {
      if (confirm('Tem certeza que deseja excluir este cliente?')) {
        this.http.delete(`/api/v1/clientes/${clienteId}`).subscribe(() => {
          alert('Cliente excluído com sucesso!');
          this.carregarClientes(); // Atualiza a lista
        });
      }
    }
    voltarParaHomepage(): void {
      this.router.navigate(['/']); // Altere a rota conforme sua configuração
    }


}
