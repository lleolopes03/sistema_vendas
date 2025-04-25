package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.*;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.ClienteRepository;
import com.br.sistema_teste.repositories.FuncionarioRepository;
import com.br.sistema_teste.repositories.ProdutoRepository;
import com.br.sistema_teste.repositories.VendaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class VendaServiceTest {
    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    @DisplayName("Teste salvar uma venda")
    void testCriarVendaSuccess() {
        VendaRequest vendaRequest = new VendaRequest();
        vendaRequest.setFuncionarioId(1L);
        vendaRequest.setClienteId(1L);
        vendaRequest.setProdutoIds(Arrays.asList(1L, 2L));
        vendaRequest.setQuantidades(Arrays.asList(BigDecimal.ONE, BigDecimal.TEN));

        Funcionario funcionario = new Funcionario();
        Cliente cliente = new Cliente();
        Produtos produto1 = new Produtos(1L, "Produto 1","descricao 1","11111111", new BigDecimal("5.00"));
        Produtos produto2 = new Produtos(2L, "Produto 2","descricao 2","22222222", new BigDecimal("10.00"));

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto2));

        Venda venda = vendaService.criarVenda(vendaRequest);

        assertNotNull(venda);
        assertEquals(LocalDateTime.now().getDayOfMonth(), venda.getData().getDayOfMonth());
        assertEquals(funcionario, venda.getFuncionario());
        assertEquals(cliente, venda.getCliente());
        assertEquals(2, venda.getVendaProdutos().size());
        assertEquals(new BigDecimal("105.00"), venda.getTotal());

        verify(vendaRepository, times(1)).save(venda);
    }

    @Test
    @DisplayName("Teste criar venda funcionario nao encontrado")
    void testCriarVendaFuncionarioNotFound() {
        VendaRequest vendaRequest = new VendaRequest();
        vendaRequest.setFuncionarioId(1L);
        vendaRequest.setClienteId(1L);
        vendaRequest.setProdutoIds(Arrays.asList(1L));
        vendaRequest.setQuantidades(Arrays.asList(BigDecimal.ONE));

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> vendaService.criarVenda(vendaRequest));
        assertEquals("Funcionario não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Teste criar venda cliente nao encontrado")
    void testCriarVendaClienteNotFound() {
        VendaRequest vendaRequest = new VendaRequest();
        vendaRequest.setFuncionarioId(1L);
        vendaRequest.setClienteId(1L);
        vendaRequest.setProdutoIds(Arrays.asList(1L));
        vendaRequest.setQuantidades(Arrays.asList(BigDecimal.ONE));

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(new Funcionario()));
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> vendaService.criarVenda(vendaRequest));
        assertEquals("Cliente não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Teste criar venda produtoc nao encontrado")
    void testCriarVendaProdutoNotFound() {
        VendaRequest vendaRequest = new VendaRequest();
        vendaRequest.setFuncionarioId(1L);
        vendaRequest.setClienteId(1L);
        vendaRequest.setProdutoIds(Arrays.asList(1L));
        vendaRequest.setQuantidades(Arrays.asList(BigDecimal.ONE));

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(new Funcionario()));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(new Cliente()));
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> vendaService.criarVenda(vendaRequest));
        assertEquals("Produto com Id: 1 não encontrado", thrown.getMessage());
    }
    @Test
    @DisplayName("Teste consultar venda por Id")
    void testConsultarVendaPorIdSuccess() {
        Long id = 1L;
        Venda venda = new Venda();
        venda.setId(id);

        when(vendaRepository.findById(id)).thenReturn(Optional.of(venda));

        Venda result = vendaService.consultarVendaPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Teste de consultar id de uma venda nao encontrado")
    void testConsultarVendaPorIdNotFound() {
        Long id = 1L;

        when(vendaRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> vendaService.consultarVendaPorId(id));
        assertEquals("Venda com Id: " + id + " não encontrada", thrown.getMessage());
    }

    @Test
    @DisplayName("Teste criar venda funcionario nao encontrado")
    void testConsultarTodasVendasSuccess() {
        Venda venda1 = new Venda();
        Venda venda2 = new Venda();

        when(vendaRepository.findAll()).thenReturn(Arrays.asList(venda1, venda2));

        List<Venda> vendas = vendaService.consultarTodasVendas();

        assertNotNull(vendas);
        assertEquals(2, vendas.size());
        assertTrue(vendas.contains(venda1));
        assertTrue(vendas.contains(venda2));
    }

}