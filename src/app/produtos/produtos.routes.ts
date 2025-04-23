
import { Routes } from "@angular/router";
import { CadastroProdutosComponent } from "./cadastro-produtos/cadastro-produtos.component";
import { ListaProdutosComponent } from "./lista-produtos/lista-produtos.component";

export const produtosRoutes:Routes =[
  {path:'produtos/cadastro', component:CadastroProdutosComponent},
  {path:'produtos/lista',component:ListaProdutosComponent},
  {path:'produtos/cadastro/:id',component:CadastroProdutosComponent}

]
