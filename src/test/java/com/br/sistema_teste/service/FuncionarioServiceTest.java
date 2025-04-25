package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Funcionario;
import com.br.sistema_teste.domain.Produtos;
import com.br.sistema_teste.dto.FuncionarioCreateDto;
import com.br.sistema_teste.dto.FuncionarioResponseDto;
import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.FuncionarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {
    @InjectMocks
    private FuncionarioService funcionarioService;
    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Test
    @DisplayName("Teste salvar funcionario")
    public void test_salvar_funcionario(){
        FuncionarioCreateDto createDto=new FuncionarioCreateDto("leandro","leandro@email.com","31313131");
        Funcionario funcionario=new Funcionario(1L,"leandro","leandro@email.com","31313131");
        FuncionarioResponseDto responseDto=new FuncionarioResponseDto(1L,"leandro","leandro@email.com","31313131");
        assertNotNull(createDto);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);
        FuncionarioResponseDto resultado=funcionarioService.salvar(createDto);
        assertEquals(responseDto.getId(),resultado.getId());
        assertEquals(responseDto.getNome(),resultado.getNome());
        assertEquals(responseDto.getEmail(),resultado.getEmail());
        assertEquals(responseDto.getTelefone(),resultado.getTelefone());


    }
    @Test
    @DisplayName("Teste buscar funcionario por ID")
    public void teste_buscar_funcionario_PorID(){
        Funcionario funcionario=new Funcionario(1L,"leandro","leandro@email.com","31313131");
        FuncionarioResponseDto responseDto=new FuncionarioResponseDto(1L,"leandro","leandro@email.com","31313131");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        FuncionarioResponseDto resultado=funcionarioService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(responseDto.getId(),resultado.getId());
        assertEquals(responseDto.getNome(),resultado.getNome());
        assertEquals(responseDto.getEmail(),resultado.getEmail());
        assertEquals(responseDto.getTelefone(),resultado.getTelefone());

    }
    @Test
    @DisplayName("Teste buscar funcionario por id não encontrado")
    void testBuscarPorIdNotFound() {
        Long id = 1L;

        when(funcionarioRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> funcionarioService.buscarPorId(id));
        assertEquals("Funcionario com id: " + id + " não encontrado", thrown.getMessage());
    }


    @Test
    @DisplayName("Teste listar todos os  funcionario")
    public void teste_buscar_todos_funcionarios(){
        List<Funcionario>funcionarios= Arrays.asList(
                new Funcionario(1L,"leandro","leandro@email.com","31313131"),
                new Funcionario(2L,"gustavo","gustavo@email.com","31313131")

        );
        List<FuncionarioResponseDto>responseDtosEsperados=Arrays.asList(
                new FuncionarioResponseDto(1L,"leandro","leandro@email.com","31313131"),
                new FuncionarioResponseDto(2L,"gustavo","gustavo@email.com","31313131")

        );
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);


        List<FuncionarioResponseDto> resultados = funcionarioService.toListDto();


        assertNotNull(resultados);


        assertEquals(responseDtosEsperados.size(), resultados.size());
        for (int i = 0; i < responseDtosEsperados.size(); i++) {
            assertEquals(responseDtosEsperados.get(i).getId(), resultados.get(i).getId());
            assertEquals(responseDtosEsperados.get(i).getNome(), resultados.get(i).getNome());
            assertEquals(responseDtosEsperados.get(i).getEmail(), resultados.get(i).getEmail());
            assertEquals(responseDtosEsperados.get(i).getTelefone(), resultados.get(i).getTelefone());

        }
    }
    @Test
    @DisplayName("Teste deletar funcionario")
    void testDeletar() {
        Long id = 1L;

        doNothing().when(funcionarioRepository).deleteById(id);

        funcionarioService.deletar(id);

        verify(funcionarioRepository, times(1)).deleteById(id);
    }
    @Test
    @DisplayName("Teste editar funcionario não encontrado")
    void testEditarFuncionarioNotFound() {
        Long id = 1L;
        FuncionarioCreateDto createDto = new FuncionarioCreateDto();

        when(funcionarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> funcionarioService.editarFuncionario(id, createDto));
    }
    @Test
    @DisplayName("Teste editar funcionario")
    public void teste_editar_funcionario() {

        Funcionario funcionarioExistentes = new Funcionario(1L, "leandro", "leandro@email.com", "31313131");

        // DTO de entrada para edição
        FuncionarioCreateDto createDto = new FuncionarioCreateDto("leandro atualizado", "leandro@email.com", "31313131");

        // Mock do cliente atualizado a ser salvo pelo repositório
        Funcionario funcionariosAtualizados = new Funcionario(1L, "leandro atualizado", "leandro@email.com", "31313131");

        // Mock do DTO de resposta esperado
        FuncionarioResponseDto responseDtoEsperado = new FuncionarioResponseDto(1L, "leandro atualizado", "leandro@email.com", "31313131");

        // Simula o comportamento do repositório para encontrar e salvar o cliente
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioExistentes));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionariosAtualizados);

        // Chama o método que está sendo testado
        FuncionarioResponseDto resultado = funcionarioService.editarFuncionario(1L, createDto);

        // Verifica se a resposta não é nula
        assertNotNull(resultado);

        // Compara os valores esperados com os valores reais
        assertEquals(responseDtoEsperado.getId(), resultado.getId());
        assertEquals(responseDtoEsperado.getNome(), resultado.getNome());
        assertEquals(responseDtoEsperado.getEmail(), resultado.getEmail());
        assertEquals(responseDtoEsperado.getTelefone(), resultado.getTelefone());

    }


}

