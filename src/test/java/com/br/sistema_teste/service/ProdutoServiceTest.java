package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Cliente;
import com.br.sistema_teste.domain.Produtos;
import com.br.sistema_teste.dto.ClienteCreateDto;
import com.br.sistema_teste.dto.ClienteResponseDto;
import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.dto.mapper.ProdutoMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.ProdutoRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {
    @InjectMocks
    ProdutoService produtoService;
    @Mock
    ProdutoRepository produtoRepository;



    @Test
    @DisplayName("Teste salvar produto")
    public void teste_salvar_produto(){
        ProdutoCreateDto createDto= new ProdutoCreateDto("uva","uva","21354",5.99);
        Produtos produtos=new Produtos(1L,"uva","uva","21354",new BigDecimal("5.99"));
        ProdutoResponseDto responseDto=new ProdutoResponseDto(1L,"uva","uva","21354",5.99);
        assertNotNull(createDto);
        when(produtoRepository.save(any(Produtos.class))).thenReturn(produtos);
        ProdutoResponseDto resultado=produtoService.salvar(createDto);
        assertEquals(responseDto.getId(), resultado.getId());
        assertEquals(responseDto.getNome(), resultado.getNome());
        assertEquals(responseDto.getDescricao(), resultado.getDescricao());
        assertEquals(responseDto.getNcm(), resultado.getNcm());
        assertEquals(responseDto.getPreco(), resultado.getPreco());
    }
    @Test
    @DisplayName("Teste buscar produto por id")
    public void testar_produto_por_Id(){
        Produtos produtos=new Produtos(1L,"uva","uva","21354",new BigDecimal("5.99"));
        ProdutoResponseDto responseDto=new ProdutoResponseDto(1L,"uva","uva","21354",5.99);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtos));
        ProdutoResponseDto resultado=produtoService.buscarProId(1L);
        assertNotNull(resultado);
        assertEquals(responseDto.getId(), resultado.getId());
        assertEquals(responseDto.getNome(), resultado.getNome());
        assertEquals(responseDto.getDescricao(), resultado.getDescricao());
        assertEquals(responseDto.getNcm(), resultado.getNcm());
        assertEquals(responseDto.getPreco(), resultado.getPreco());

    }
        @Test
        @DisplayName("Teste busca por produto por id nao encontrado")
    void testBuscarProIdNotFound() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.buscarProId(id));
    }
    @Test
    @DisplayName("Teste buscar todos os produtos")
    public void teste_buscar_todos_produtos() {

        List<Produtos> produtos = Arrays.asList(
                new Produtos(1L, "uva", "uva", "31254", new BigDecimal("5.99")),
                new Produtos(2L, "pera", "pera", "31254", new BigDecimal("6.99"))
        );


        List<ProdutoResponseDto> responseDtosEsperados = Arrays.asList(
                new ProdutoResponseDto(1L, "uva", "uva", "31254", 5.99),
                new ProdutoResponseDto(2L, "pera", "pera", "31254", 6.99)
        );


        when(produtoRepository.findAll()).thenReturn(produtos);


        List<ProdutoResponseDto> resultados = produtoService.buscarTodos();


        assertNotNull(resultados);


        assertEquals(responseDtosEsperados.size(), resultados.size());
        for (int i = 0; i < responseDtosEsperados.size(); i++) {
            assertEquals(responseDtosEsperados.get(i).getId(), resultados.get(i).getId());
            assertEquals(responseDtosEsperados.get(i).getNome(), resultados.get(i).getNome());
            assertEquals(responseDtosEsperados.get(i).getDescricao(), resultados.get(i).getDescricao());
            assertEquals(responseDtosEsperados.get(i).getNcm(), resultados.get(i).getNcm());
            assertEquals(responseDtosEsperados.get(i).getPreco(), resultados.get(i).getPreco());
        }
    }

    @Test
    @DisplayName("Teste deletar produto")
    void testDeletar() {
        Long id = 1L;

        doNothing().when(produtoRepository).deleteById(id);

        produtoService.deletar(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Teste editar  produto nao encontrado")
    void testEditarProdutoNotFound() {
        Long id = 1L;
        ProdutoCreateDto createDto = new ProdutoCreateDto();

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.editarProduto(id, createDto));
    }
    @Test
    @DisplayName("Teste editar produto")
    public void teste_editar_produto() {

        Produtos produtosExistentes = new Produtos(1L, "uva", "uva", "31254", new BigDecimal("5.99"));

        // DTO de entrada para edição
        ProdutoCreateDto createDto = new ProdutoCreateDto("uva atualizado", "uva", "31254", 5.99);

        // Mock do cliente atualizado a ser salvo pelo repositório
        Produtos produtosAtualizados = new Produtos(1L, "uva atualizado", "uva", "31254", new BigDecimal("5.99"));

        // Mock do DTO de resposta esperado
        ProdutoResponseDto responseDtoEsperado = new ProdutoResponseDto(1L, "uva atualizado", "uva", "31254", 5.99);

        // Simula o comportamento do repositório para encontrar e salvar o cliente
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtosExistentes));
        when(produtoRepository.save(any(Produtos.class))).thenReturn(produtosAtualizados);

        // Chama o método que está sendo testado
        ProdutoResponseDto resultado = produtoService.editarProduto(1L, createDto);

        // Verifica se a resposta não é nula
        assertNotNull(resultado);

        // Compara os valores esperados com os valores reais
        assertEquals(responseDtoEsperado.getId(), resultado.getId());
        assertEquals(responseDtoEsperado.getNome(), resultado.getNome());
        assertEquals(responseDtoEsperado.getDescricao(), resultado.getDescricao());
        assertEquals(responseDtoEsperado.getNcm(), resultado.getNcm());
        assertEquals(responseDtoEsperado.getPreco(), resultado.getPreco());
    }

}