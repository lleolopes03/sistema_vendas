package com.br.sistema_teste.dto;

import java.math.BigDecimal;

public class ProdutoCreateDto {
    private String nome;
    private String descricao;
    private String ncm;
    private BigDecimal preco;

    public ProdutoCreateDto() {
    }

    public ProdutoCreateDto(String nome, String descricao, String ncm, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.ncm = ncm;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ProdutoCreateDto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ncm='" + ncm + '\'' +
                ", preco=" + preco +
                '}';
    }
}
