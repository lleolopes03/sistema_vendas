package com.br.sistema_teste.controller;

import com.br.sistema_teste.dto.FuncionarioCreateDto;
import com.br.sistema_teste.dto.FuncionarioResponseDto;
import com.br.sistema_teste.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Funcionarios",description = "Criar,Editar,Deletar,Buscar Funcionarios")
@RestController
@RequestMapping("api/v1/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(summary = "Cadastro de funcionario",description = "Cadastro de funcionarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "cadastrado com sucesso")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponseDto>create(@RequestBody FuncionarioCreateDto createDto){
        FuncionarioResponseDto responseDto=funcionarioService.salvar(createDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @Operation(summary = "Busca de funciongario",description = "Busca de funcionario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Busca do funcionario  com sucesso")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<FuncionarioResponseDto>buscarPorId(@PathVariable Long id){
        FuncionarioResponseDto responseDto=funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "Lista de  funcionarios",description = "Listar todos os funcionarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de funcionarios ok")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<List<FuncionarioResponseDto>>listarTodos(){
        List<FuncionarioResponseDto>responseDtos=funcionarioService.toListDto();
        return ResponseEntity.ok(responseDtos);
    }
    @Operation(summary = "Deletar funcionario",description = "Deletar funcionario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deletado com sucesso")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Editar funcionario",description = "Editar funcionario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Funcionario editado com sucesso")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponseDto>editarFuncionario(@PathVariable Long id,@RequestBody FuncionarioCreateDto createDto){
        FuncionarioResponseDto responseDto=funcionarioService.editarFuncionario(id,createDto);
        return ResponseEntity.ok(responseDto);
    }
}
