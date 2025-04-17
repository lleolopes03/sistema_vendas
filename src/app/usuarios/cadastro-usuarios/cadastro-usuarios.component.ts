import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup,ReactiveFormsModule,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-cadastro-usuarios',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './cadastro-usuarios.component.html',
  styleUrl: './cadastro-usuarios.component.scss'
})
export class CadastroUsuariosComponent {
  userForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      age: ['', [Validators.required, Validators.min(18)]],
      password: ['', Validators.required],
      role: ['ROLE_CAIXA', Validators.required], // Default como ROLE_CAIXA
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      this.http.post('/api/v1/usuarios', this.userForm.value).subscribe({
        next: () => {
          alert('Usuário cadastrado com sucesso!');
          this.router.navigate(['/']); // Redireciona para a homepage
        },
        error: err => {
          console.error(err);
          const errorMessage = err?.error?.message || 'Ocorreu um erro desconhecido';
          alert(`Erro ao cadastrar usuário: ${errorMessage}`);
        },
      });
    } else {
      alert('Por favor, preencha todos os campos corretamente.');
    }
  }
  onCancel() {
    // Redireciona para a homepage
    this.router.navigate(['/']);
  }

}



