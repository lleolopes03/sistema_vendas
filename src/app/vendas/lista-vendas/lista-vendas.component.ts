import { Component, OnInit } from '@angular/core';
import { VendaService } from '../../services/venda.service';
import { CommonModule } from '@angular/common';
import { Router,RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProdutoVendido } from '../model/vendas';



@Component({
  selector: 'app-lista-vendas',
  standalone: true,
  imports: [CommonModule,RouterModule,FormsModule],
  templateUrl: './lista-vendas.component.html',
  styleUrl: './lista-vendas.component.scss'
})
export class ListaVendasComponent implements OnInit {
  vendasRealizadas: any[] = [];
  filtroBusca: string = '';


  // Injeção do serviço VendaService
  constructor(private vendaService: VendaService,private router: Router) {}

  ngOnInit() {
    this.buscarVendas();
  }
  buscarVendas() {
    this.vendaService.consultarTodasVendas().subscribe(
      (vendas) => {
        console.log('Estrutura das vendas retornadas:', JSON.stringify(vendas, null, 2));

        this.vendasRealizadas = vendas.map(venda => ({
          id: venda.id,
          dataHora: venda.data ? new Date(venda.data) : null, // Convertendo a string para Date
          funcionarioNome: venda.funcionario?.nome || 'Não identificado',
          clienteNome: venda.cliente?.nome || 'Não identificado',
          produtos: venda.vendaProdutos.map((item: ProdutoVendido) => ({
            nome: item.produto?.nome || 'Produto não encontrado',
            quantidade: item.quantidade
          })) || [],
          total: venda.total || 0
        }));

        console.log('Vendas carregadas:', this.vendasRealizadas);
      },
      (error) => console.error('Erro ao buscar vendas:', error)
    );
  }
  get vendasFiltradas() {
    return this.vendasRealizadas.filter(venda =>
      venda.id.toString().includes(this.filtroBusca) ||
      venda.clienteNome?.toLowerCase().includes(this.filtroBusca.toLowerCase()) ||
      venda.funcionarioNome?.toLowerCase().includes(this.filtroBusca.toLowerCase())
    );
  }

  voltarParaHomepage(): void {
    this.router.navigate(['/']); // Altere a rota conforme sua configuração
  }
}
