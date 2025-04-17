import { Routes } from "@angular/router";
import { CadastroUsuariosComponent } from "./cadastro-usuarios/cadastro-usuarios.component";
import { ListaUsuariosComponent } from "./lista-usuarios/lista-usuarios.component";

export const usuariosRoutes:Routes=[
  {path:'usuarios/cadastro',component:CadastroUsuariosComponent},
    {path:'usuarios/listar',component:ListaUsuariosComponent}
]
