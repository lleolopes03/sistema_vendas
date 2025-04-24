export interface Funcionario {
  id: number;
  nome: string;
  email: string;
  telefone: string;
}

export interface Cliente {
  id: number;
  nome: string;
  email: string;
  idade: number;
  cpf: string;
}

export interface ProdutoVendido {
  id: number;
  produto: {
    id: number;
    nome: string;
    descricao: string;
    ncm: string;
    preco: number;
  };
  quantidade: number;
}

export interface Vendas {
  id: number;
  data: string;
  funcionarioId: number;
  clienteId: number;
  funcionario?: Funcionario; // Adicionando funcionário como objeto opcional
  cliente?: Cliente; // Adicionando cliente como objeto opcional
  vendaProdutos: ProdutoVendido[];
  total: number;
}
