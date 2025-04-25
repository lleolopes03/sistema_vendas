package com.br.sistema_teste.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

public class VendaRequest {
    private LocalDateTime data;
    private Long funcionarioId; // Alterado para ID
    private Long clienteId; // Alterado para ID
    private List<Long> produtoIds;
    private List<BigDecimal> quantidades;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getProdutoIds() {
        return produtoIds;
    }

    public void setProdutoIds(List<Long> produtoIds) {
        this.produtoIds = produtoIds;
    }

    public List<BigDecimal> getQuantidades() {
        return quantidades;
    }

    public void setQuantidades(List<BigDecimal> quantidades) {
        this.quantidades = quantidades;
    }
}
