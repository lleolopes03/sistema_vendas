import { Routes } from '@angular/router';
import { CadastroFuncionarioComponent } from './cadastro-funcionario/cadastro-funcionario.component';
import { ListarFuncionariosComponent } from './listar-funcionarios/listar-funcionarios.component';


export const funcionariosRoutes: Routes = [
  { path: 'funcionarios/cadastro', component: CadastroFuncionarioComponent },
  { path: 'funcionarios/listar', component:ListarFuncionariosComponent },
   { path: 'funcionarios/cadastro/:id', component:CadastroFuncionarioComponent }
];
