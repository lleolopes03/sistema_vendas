import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vendas } from '../vendas/vendas';
import { Clientes } from '../clientes/clientes';
import { Funcionarios } from '../funcionarios/model/funcionarios';
import { Produtos } from '../produtos/model/produtos';

@Injectable({
  providedIn: 'root'
})
export class VendaService {
  private apiUrl = '/api/v1/vendas'; // Verifique a URL correta

  constructor(private http: HttpClient) {}

  criarVenda(vendaRequest: any): Observable<Vendas> {
    return this.http.post<Vendas>(this.apiUrl, vendaRequest);
  }

  consultarVendaPorId(id: number): Observable<Vendas> {
    return this.http.get<Vendas>(`${this.apiUrl}/${id}`);
  }

  consultarTodasVendas(): Observable<Vendas[]> {
    return this.http.get<Vendas[]>(this.apiUrl);
  }
  obterFuncionarioPorId(id: number): Observable<Funcionarios> {
    return this.http.get<Funcionarios>(`/api/v1/funcionarios/${id}`);
  }

  obterClientePorId(id: number): Observable<Clientes> {
    return this.http.get<Clientes>(`/api/v1/clientes/${id}`);
  }

  obterProdutoPorId(id: number): Observable<Produtos> {
    return this.http.get<Produtos>(`/api/v1/produtos/${id}`);
  }




}
