import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Importando CommonModule

@Component({
  selector: 'app-lista-produtos',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule ],
  templateUrl: './lista-produtos.component.html',
  styleUrl: './lista-produtos.component.scss'
})
export class ListaProdutosComponent {


     produtos: any[] = [];
        produtosFiltrados: any[] = [];
        filtroNome: string = '';
        userRole: string | null = localStorage.getItem('userRole');
        constructor(private http: HttpClient,private router: Router) {}

        ngOnInit() {
          this.carregarProdutos();
        }

        carregarProdutos() {
          this.http.get<any[]>('/api/v1/produtos').subscribe(response => {
            this.produtos = response;
            this.produtosFiltrados = response; // Inicializa a lista filtrada
          });
        }

        filtrarProdutos() {
          this.produtosFiltrados = this.produtos.filter(produtos =>
            produtos.nome.toLowerCase().includes(this.filtroNome.toLowerCase())
          );
        }

        editarProdutos(produtoId: number) {
          alert(`Editar produto ID: ${produtoId}`);
          // Aqui você pode navegar para uma página de edição
        }

        deletarProduto(produtoId: number) {
          if (confirm('Tem certeza que deseja excluir este produto?')) {
            this.http.delete(`/api/v1/produtos/${produtoId}`).subscribe(() => {
              alert('Produto excluído com sucesso!');
              this.carregarProdutos(); // Atualiza a lista
            });
          }
        }
        voltarParaHomepage(): void {
          this.router.navigate(['/']); // Altere a rota conforme sua configuração
        }


}
