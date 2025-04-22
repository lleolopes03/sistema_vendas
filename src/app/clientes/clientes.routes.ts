import { Routes } from "@angular/router";
import { CadastroClientesComponent } from "./cadastro-clientes/cadastro-clientes.component";
import { ListaClientesComponent } from "./lista-clientes/lista-clientes.component";

export const clientesRoutes:Routes=[
  {path:'clientes/cadastro',component:CadastroClientesComponent},
  {path:'clientes/lista',component:ListaClientesComponent},
  { path: 'clientes/cadastro/:id', component:CadastroClientesComponent } // Cadastro
]
