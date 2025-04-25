package com.br.sistema_teste.controller;

import com.br.sistema_teste.dto.ClienteCreateDto;
import com.br.sistema_teste.dto.ClienteResponseDto;
import com.br.sistema_teste.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Clientes",description = "Criar,Editar,Deletar clientes")
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Cadastro de clientes",description = "Cadastro de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "cadastrado com sucesso")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto>create(@RequestBody  @Valid  ClienteCreateDto createDto){
        ClienteResponseDto responseDto=clienteService.salvar(createDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @Operation(summary = "Busca de cliente",description = "Busca de Cliente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Busca do usuario com sucesso")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<ClienteResponseDto>getById(@PathVariable Long id){
        ClienteResponseDto responseDto=clienteService.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "Lista de  clientes",description = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de clientes ok")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<List<ClienteResponseDto>>getAll(){
        List<ClienteResponseDto>responseDtos=clienteService.buscarTodos();
        return ResponseEntity.ok(responseDtos);
    }
    @Operation(summary = "Deletar usuario",description = "Deletar usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Editar cliente",description = "Editar cliente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Cliente editado com sucesso")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto>editarCliente(@PathVariable Long id,@RequestBody ClienteCreateDto createDto){
        ClienteResponseDto responseDto=clienteService.editarCliente(id, createDto);
        return ResponseEntity.ok(responseDto);
    }

}
