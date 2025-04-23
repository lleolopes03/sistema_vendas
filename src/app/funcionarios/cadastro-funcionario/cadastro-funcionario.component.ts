import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-cadastro-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule ],
  templateUrl: './cadastro-funcionario.component.html',
  styleUrl: './cadastro-funcionario.component.scss'
})
export class CadastroFuncionarioComponent {
  funcionario={id:null, nome: '', email: '', telefone: ''};
  editando = false;
  apiUrl='/api/v1/funcionarios';


  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

    ngOnInit() {
      this.route.paramMap.subscribe(params => {
        const id = params.get('id'); // Pega o ID da URL
        if (id) {
          this.carregarFuncionario(parseInt(id));
        }
      });
    }

    carregarFuncionario(id: number) {
      this.http.get<any>(`${this.apiUrl}/${id}`).subscribe(funcionario => {
        this.funcionario = funcionario;
        this.editando = true;
      });
    }

    salvarFuncionario() {
      if (this.editando) {
        this.http.put(`${this.apiUrl}/${this.funcionario.id}`, this.funcionario).subscribe(() => {
          alert('Funcionario atualizado com sucesso!');
          this.router.navigate(['/']);
        });
      } else {
        this.http.post(this.apiUrl, this.funcionario).subscribe(() => {
          alert('Cadastro realizado com sucesso!');
          this.router.navigate(['/']);
        });
      }
    }

    cancelar() {
      this.router.navigate(['/']);
    }


}
