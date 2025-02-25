package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.*;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.ClienteRepository;
import com.br.sistema_teste.repositories.FuncionarioRepository;
import com.br.sistema_teste.repositories.ProdutoRepository;
import com.br.sistema_teste.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;



    @Transactional
    public Venda criarVenda(VendaRequest vendaRequest) {
        Venda venda = new Venda();
        LocalDateTime agora = LocalDateTime.now();
        venda.setData(agora);

        // Buscar Funcionario e Cliente pelo ID
        Funcionario funcionario = funcionarioRepository.findById(vendaRequest.getFuncionarioId())
                .orElseThrow(() -> new BusinessException("Funcionario não encontrado"));

        Cliente cliente = clienteRepository.findById(vendaRequest.getClienteId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        venda.setFuncionario(funcionario);
        venda.setCliente(cliente);

        List<VendaProduto> vendaProdutos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < vendaRequest.getProdutoIds().size(); i++) {
            Long produtoId = vendaRequest.getProdutoIds().get(i);
            BigDecimal quantidade = vendaRequest.getQuantidades().get(i);

            Produtos produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new BusinessException(String.format("Produto com Id: %s não encontrado", produtoId)));

            VendaProduto vendaProduto = new VendaProduto();
            vendaProduto.setVenda(venda);
            vendaProduto.setProduto(produto);
            vendaProduto.setQuantidade(quantidade);

            vendaProdutos.add(vendaProduto);
            total = total.add(produto.getPreco().multiply(quantidade));
        }

        venda.setVendaProdutos(vendaProdutos);
        venda.setTotal(total);
        vendaRepository.save(venda);

        return venda;
    }


    @Transactional
    public Venda consultarVendaPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Venda com Id: " + id + " não encontrada"));
    }

    @Transactional
    public List<Venda> consultarTodasVendas() {
        return vendaRepository.findAll();
    }
}
