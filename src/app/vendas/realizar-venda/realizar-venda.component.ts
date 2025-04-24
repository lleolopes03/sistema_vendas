import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VendaService } from '../../services/venda.service';
import { Router,RouterModule } from '@angular/router';

@Component({
  selector: 'app-realizar-venda',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './realizar-venda.component.html',
  styleUrl: './realizar-venda.component.scss'
})
export class RealizarVendaComponent {
  vendaRequest = {
    funcionarioId: null,
    clienteId: null,
    produtoIds: [] as number[],
    quantidades: [] as number[],
    total: 0 // Adicionando a propriedade total


  };

  funcionarioNome: string = '';
  clienteNome: string = '';
  produtos: { id: number; nome: string; preco: number }[] = [];
  total: number = 0;

  constructor(private vendaService: VendaService,private router: Router) {}

  buscarFuncionario() {
    if (this.vendaRequest.funcionarioId) {
      this.vendaService.obterFuncionarioPorId(this.vendaRequest.funcionarioId).subscribe(
        (funcionario) => {
          this.funcionarioNome = funcionario.nome;
          console.log('Funcionário encontrado:', this.funcionarioNome);
        },
        (error) => console.error('Erro ao buscar funcionário:', error)
      );
    }
  }

  buscarCliente() {
    if (this.vendaRequest.clienteId) {
      this.vendaService.obterClientePorId(this.vendaRequest.clienteId).subscribe(
        (cliente) => {
          this.clienteNome = cliente.nome;
          console.log('Cliente encontrado:', this.clienteNome);
        },
        (error) => console.error('Erro ao buscar cliente:', error)
      );
    }
  }

  buscarProduto(produtoInput: HTMLInputElement) {
    const produtoId = Number(produtoInput.value);
    if (!isNaN(produtoId)) {
      this.vendaService.obterProdutoPorId(produtoId).subscribe(
        (produto) => {
          this.produtos.push({ id: produtoId, nome: produto.nome, preco: produto.preco });
          this.vendaRequest.quantidades.push(1); // Define quantidade inicial como 1
          this.calcularTotal();
        },
        (error) => console.error('Erro ao buscar produto:', error)
      );
    } else {
      console.error('ID do produto inválido');
    }
  }

  calcularTotal() {
    this.total = this.produtos.reduce((acc, produto, index) => {
      const quantidade = Number(this.vendaRequest.quantidades[index]) || 1; // Garantindo conversão para número
      return acc + produto.preco * quantidade;
    }, 0);
  }

  realizarVenda() {
    this.calcularTotal();
    this.vendaRequest.total = this.total;

    if (!this.vendaRequest.funcionarioId || !this.vendaRequest.clienteId) {
      alert('É necessário selecionar um funcionário e um cliente antes de realizar a venda.');
      return;
    }

    console.log('Dados enviados para API:', JSON.stringify(this.vendaRequest, null, 2));

    this.vendaService.criarVenda(this.vendaRequest).subscribe(
      (response) => {
        console.log('Venda realizada!', response);
        alert('Venda realizada com sucesso!');
        this.resetarVenda(); // 🔥 Agora limpa os campos após finalizar a venda!
      },
      (error) => {
        console.error('Erro ao realizar venda', error);
        alert('Erro ao realizar venda.');
      }
    );
  }
  moverParaProximoCampo(nomeCampo: string) {
    const proximoCampo = document.getElementsByName(nomeCampo)[0] as HTMLInputElement;
    if (proximoCampo) {
      proximoCampo.focus();
    }
  }
  novaQuantidade: number = 1; // Quantidade inicial

  adicionarProduto(produtoId: string, quantidade: number) {
    const id = Number(produtoId);
    if (!isNaN(id) && quantidade > 0) {
      this.vendaService.obterProdutoPorId(id).subscribe(
        (produto) => {
          this.produtos.push({ id, nome: produto.nome, preco: produto.preco });

          this.vendaRequest.produtoIds.push(id);
          this.vendaRequest.quantidades.push(quantidade);

          this.calcularTotal();
          console.log('Produto adicionado:', this.produtos);
          console.log('vendaRequest atualizado:', this.vendaRequest);
        },
        (error) => console.error('Erro ao buscar produto:', error)
      );
    } else {
      console.error('ID do produto ou quantidade inválida');
    }
  }
valorPago: number = 0;
troco: number | null = null; // Inicializa troco como null para evitar exibição prematura

calcularTroco() {
  if (this.valorPago >= this.total) {
    this.troco = Math.round((this.valorPago - this.total) * 100) / 100; // Mantém duas casas decimais
  } else {
    this.troco = 0; // Se o valor pago for menor, troco será zero
  }
  console.log('Valor Pago:', this.valorPago, 'Total:', this.total, 'Troco:', this.troco);
}
formatarValorPago() {
  this.valorPago = Math.round(this.valorPago * 100) / 100; // Mantém duas casas decimais sem erros de conversão
}
resetarVenda() {
  this.vendaRequest = {
    funcionarioId: null,
    clienteId: null,
    produtoIds: [],
    quantidades: [],
    total: 0
  };

  this.funcionarioNome = '';
  this.clienteNome = '';
  this.produtos = [];
  this.total = 0;
  this.valorPago = 0;
  this.troco = null;
  this.novaQuantidade = 1; // Resetando quantidade inicial
}
voltarParaHomepage(): void {
  this.router.navigate(['/']); // Altere a rota conforme sua configuração
}




  }




