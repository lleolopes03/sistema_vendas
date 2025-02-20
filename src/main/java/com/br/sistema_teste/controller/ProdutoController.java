package com.br.sistema_teste.controller;

import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.service.NcmService;
import com.br.sistema_teste.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{id}")
public class ProdutoController {
    @Autowired
    public ProdutoService produtoService;
    @Autowired
    private NcmService ncmService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto>create(@RequestBody ProdutoCreateDto createDto){
        ProdutoResponseDto responseDto=produtoService.salvar(createDto);
        return  new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto>getById(@PathVariable Long id){
        ProdutoResponseDto responseDto=produtoService.buscarProId(id);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>>getAll(){
        List<ProdutoResponseDto>responseDtos=produtoService.buscarTodos();
        return ResponseEntity.ok(responseDtos);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto>editarProduto(@PathVariable Long id,@RequestBody ProdutoCreateDto createDto){
        ProdutoResponseDto responseDto=produtoService.editarProduto(id,createDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/produtos/{id}/atualizar-ncm")
    public ProdutoResponseDto atualizarNcm(@PathVariable Long id,@RequestParam String descricaoProduto) {
        return ncmService.atualizarNcm(id, descricaoProduto);
    }


}
