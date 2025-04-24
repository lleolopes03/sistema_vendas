import { Routes } from "@angular/router";
import { RealizarVendaComponent } from "./realizar-venda/realizar-venda.component";
import { ListaVendasComponent } from "./lista-vendas/lista-vendas.component";

export const vendaRoutes:Routes=[
  {path:'venda/realizar',component:RealizarVendaComponent},
  {path:'venda/lista',component:ListaVendasComponent},
  {path:'vendas/realizar/:id',component:RealizarVendaComponent}
]

