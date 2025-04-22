import { Component } from '@angular/core';
import { Clientes } from '../clientes';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-cadastro-clientes',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './cadastro-clientes.component.html',
  styleUrl: './cadastro-clientes.component.scss'
})
export class CadastroClientesComponent {
  cliente = { id: null, nome: '', email: '', idade: 0, cpf: '' };
  editando = false;
  apiUrl = '/api/v1/clientes';


  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id'); // Pega o ID da URL
      if (id) {
        this.carregarCliente(parseInt(id));
      }
    });
  }

  carregarCliente(id: number) {
    this.http.get<any>(`${this.apiUrl}/${id}`).subscribe(cliente => {
      this.cliente = cliente;
      this.editando = true;
    });
  }

  salvarCliente() {
    if (this.editando) {
      this.http.put(`${this.apiUrl}/${this.cliente.id}`, this.cliente).subscribe(() => {
        alert('Cliente atualizado com sucesso!');
        this.router.navigate(['/']);
      });
    } else {
      this.http.post(this.apiUrl, this.cliente).subscribe(() => {
        alert('Cadastro realizado com sucesso!');
        this.router.navigate(['/']);
      });
    }
  }

  cancelar() {
    this.router.navigate(['/']);
  }


}
