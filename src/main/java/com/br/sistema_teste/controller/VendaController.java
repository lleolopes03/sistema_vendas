package com.br.sistema_teste.controller;

import com.br.sistema_teste.domain.Venda;
import com.br.sistema_teste.domain.VendaRequest;
import com.br.sistema_teste.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Vendas",description = "Realiza a venda,busca venda por Id e busca todas as vendas")
@RestController
@RequestMapping("api/v1/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;



    @Operation(summary = "Realiza uma venda",description = "Realiza uma vanda no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Venda realizada com sucesso")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CAIXA')")
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequest vendaRequest) {
        Venda venda = vendaService.criarVenda(vendaRequest);
        return ResponseEntity.ok(venda);
    }

    @Operation(summary = "Busca a venda por Id",description = "Busca venda por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Busca da venda com sucesso")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venda> consultarVendaPorId(@PathVariable Long id) {
        Venda venda = vendaService.consultarVendaPorId(id);
        return ResponseEntity.ok(venda);
    }

    @Operation(summary = "Lista todas a vendas",description = "Lista todas as vendas realizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de vendas ")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Venda>> consultarTodasVendas() {
        List<Venda> vendas = vendaService.consultarTodasVendas();
        return ResponseEntity.ok(vendas);
    }

}
