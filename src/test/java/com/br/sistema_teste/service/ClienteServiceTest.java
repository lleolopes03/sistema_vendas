package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Cliente;
import com.br.sistema_teste.domain.Usuario;
import com.br.sistema_teste.dto.ClienteCreateDto;
import com.br.sistema_teste.dto.ClienteResponseDto;
import com.br.sistema_teste.repositories.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @InjectMocks
    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("Teste criar clientes")
    public void teste_criar_clientes() {

        ClienteCreateDto createDto = new ClienteCreateDto("leandro", "leandro@email.com", 30, "999999999");


        Cliente clienteSalvar = new Cliente(1L, "leandro", "leandro@email.com", 30, "999999999");


        ClienteResponseDto responseDtoEsperado = new ClienteResponseDto(1L, "leandro", "leandro@email.com", 30, "999999999");

        assertNotNull(createDto);


        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvar);


        ClienteResponseDto resultado = clienteService.salvar(createDto);


        assertNotNull(resultado);


        assertEquals(responseDtoEsperado.getId(), resultado.getId());
        assertEquals(responseDtoEsperado.getNome(), resultado.getNome());
        assertEquals(responseDtoEsperado.getEmail(), resultado.getEmail());
        assertEquals(responseDtoEsperado.getIdade(), resultado.getIdade());
        assertEquals(responseDtoEsperado.getCpf(), resultado.getCpf());
    }

    @Test
    @DisplayName("Teste buscar cliente por ID")
    public void teste_buscar_cliente_por_id() {
        // Mock do cliente a ser retornado pelo repositório
        Cliente cliente = new Cliente(1L, "leandro", "leandro@email.com", 30, "999999999");

        // Mock do DTO de resposta esperado
        ClienteResponseDto responseDtoEsperado = new ClienteResponseDto(1L, "leandro", "leandro@email.com", 30, "999999999");

        // Simula o comportamento do repositório para encontrar o cliente por ID
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Chama o método que está sendo testado
        ClienteResponseDto resultado = clienteService.buscarPorId(1L);

        // Verifica se a resposta não é nula
        assertNotNull(resultado);

        // Compara os valores esperados com os valores reais
        assertEquals(responseDtoEsperado.getId(), resultado.getId());
        assertEquals(responseDtoEsperado.getNome(), resultado.getNome());
        assertEquals(responseDtoEsperado.getEmail(), resultado.getEmail());
        assertEquals(responseDtoEsperado.getIdade(), resultado.getIdade());
        assertEquals(responseDtoEsperado.getCpf(), resultado.getCpf());


    }
    @Test
    @DisplayName("Teste buscar todos os clientes")
    public void teste_buscar_todos_clientes() {
        // Mock da lista de clientes a ser retornada pelo repositório
        List<Cliente> clientes = Arrays.asList(
                new Cliente(1L, "leandro", "leandro@email.com", 30, "999999999"),
                new Cliente(2L, "maria", "maria@email.com", 25, "888888888")
        );

        // Mock da lista de DTOs de resposta esperados
        List<ClienteResponseDto> responseDtosEsperados = Arrays.asList(
                new ClienteResponseDto(1L, "leandro", "leandro@email.com", 30, "999999999"),
                new ClienteResponseDto(2L, "maria", "maria@email.com", 25, "888888888")
        );

        // Simula o comportamento do repositório para encontrar todos os clientes
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Chama o método que está sendo testado
        List<ClienteResponseDto> resultados = clienteService.buscarTodos();

        // Verifica se a lista de resultados não é nula
        assertNotNull(resultados);

        // Compara os valores esperados com os valores reais
        assertEquals(responseDtosEsperados.size(), resultados.size());
        for (int i = 0; i < responseDtosEsperados.size(); i++) {
            assertEquals(responseDtosEsperados.get(i).getId(), resultados.get(i).getId());
            assertEquals(responseDtosEsperados.get(i).getNome(), resultados.get(i).getNome());
            assertEquals(responseDtosEsperados.get(i).getEmail(), resultados.get(i).getEmail());
            assertEquals(responseDtosEsperados.get(i).getIdade(), resultados.get(i).getIdade());
            assertEquals(responseDtosEsperados.get(i).getCpf(), resultados.get(i).getCpf());
        }
    }
    @Test
    @DisplayName("Teste editar cliente")
    public void teste_editar_cliente() {
        // Mock do cliente existente
        Cliente clienteExistente = new Cliente(1L, "leandro", "leandro@email.com", 30, "999999999");

        // DTO de entrada para edição
        ClienteCreateDto createDto = new ClienteCreateDto("leandro atualizado", "leandro.atualizado@email.com", 30, "777777777");

        // Mock do cliente atualizado a ser salvo pelo repositório
        Cliente clienteAtualizado = new Cliente(1L, "leandro atualizado", "leandro.atualizado@email.com", 30, "777777777");

        // Mock do DTO de resposta esperado
        ClienteResponseDto responseDtoEsperado = new ClienteResponseDto(1L, "leandro atualizado", "leandro.atualizado@email.com", 30, "777777777");

        // Simula o comportamento do repositório para encontrar e salvar o cliente
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        // Chama o método que está sendo testado
        ClienteResponseDto resultado = clienteService.editarCliente(1L, createDto);

        // Verifica se a resposta não é nula
        assertNotNull(resultado);

        // Compara os valores esperados com os valores reais
        assertEquals(responseDtoEsperado.getId(), resultado.getId());
        assertEquals(responseDtoEsperado.getNome(), resultado.getNome());
        assertEquals(responseDtoEsperado.getEmail(), resultado.getEmail());
        assertEquals(responseDtoEsperado.getIdade(), resultado.getIdade());
        assertEquals(responseDtoEsperado.getCpf(), resultado.getCpf());
    }
}