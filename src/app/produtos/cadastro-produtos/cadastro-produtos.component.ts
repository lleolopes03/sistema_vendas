import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cadastro-produtos',
  standalone: true,
  imports: [CommonModule,FormsModule ],
  templateUrl: './cadastro-produtos.component.html',
  styleUrl: './cadastro-produtos.component.scss'
})
export class CadastroProdutosComponent {
   produto={id:null, nome: '', descricao: '', ncm: '', preco: ''};
    editando = false;
    apiUrl='/api/v1/produtos';


    constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

      ngOnInit() {
        this.route.paramMap.subscribe(params => {
          const id = params.get('id'); // Pega o ID da URL
          if (id) {
            this.carregarProduto(parseInt(id));
          }
        });
      }

      carregarProduto(id: number) {
        this.http.get<any>(`${this.apiUrl}/${id}`).subscribe(produto => {
          this.produto = produto;
          this.editando = true;
        });
      }

      salvarProduto() {

        if (this.editando) {
          this.http.put(`${this.apiUrl}/${this.produto.id}`, this.produto).subscribe(() => {
            alert('Produto atualizado com sucesso!');
            this.router.navigate(['/']);
          });
        } else {
          this.http.post(this.apiUrl, this.produto).subscribe(() => {
            alert('Cadastro realizado com sucesso!');
            this.router.navigate(['/']);
          });
        }
      }

      cancelar() {
        this.router.navigate(['/']);
      }



}
