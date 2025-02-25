package com.br.sistema_teste.controller;

import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.service.ProdutoService;
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
@Tag(name = "Produtos",description = "Criar,Editar,Deletar Produtos")
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {
    @Autowired
    public ProdutoService produtoService;


    @Operation(summary = "Cadastro de produtos",description = "Cadastro de produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "cadastrado com sucesso")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDto>create(@RequestBody ProdutoCreateDto createDto){
        ProdutoResponseDto responseDto=produtoService.salvar(createDto);
        return  new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @Operation(summary = "Busca de produtos",description = "Busca de Produto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Busca do produto com sucesso")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<ProdutoResponseDto>getById(@PathVariable Long id){
        ProdutoResponseDto responseDto=produtoService.buscarProId(id);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "Lista de  produtos",description = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de produtos ok")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<List<ProdutoResponseDto>>getAll(){
        List<ProdutoResponseDto>responseDtos=produtoService.buscarTodos();
        return ResponseEntity.ok(responseDtos);
    }
    @Operation(summary = "Deletar produto",description = "Deletar produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();

    }
    @Operation(summary = "Editar produto",description = "Editar produto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Produto editado com sucesso")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDto>editarProduto(@PathVariable Long id,@RequestBody ProdutoCreateDto createDto){
        ProdutoResponseDto responseDto=produtoService.editarProduto(id,createDto);
        return ResponseEntity.ok(responseDto);
    }


    }
