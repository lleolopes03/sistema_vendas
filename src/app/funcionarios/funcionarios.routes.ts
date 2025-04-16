import { Routes } from '@angular/router';
import { CadastroFuncionarioComponent } from './cadastro-funcionario/cadastro-funcionario.component';
import { EditarFuncionarioComponent } from './editar-funcionario/editar-funcionario.component';
import { ListarFuncionariosComponent } from './listar-funcionarios/listar-funcionarios.component';


export const funcionariosRoutes: Routes = [
  { path: 'funcionarios/cadastro', component: CadastroFuncionarioComponent },
  { path: 'funcionarios/editar', component: EditarFuncionarioComponent },
  { path: 'funcionarios/listar', component:ListarFuncionariosComponent },
];
