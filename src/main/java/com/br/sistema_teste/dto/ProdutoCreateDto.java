package com.br.sistema_teste.dto;

public class ProdutoCreateDto {
    private String nome;
    private String descricao;
    private String ncm;
    private double preco;

    public ProdutoCreateDto() {
    }

    public ProdutoCreateDto(String nome, String descricao, String ncm, double preco) {
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ProdutoCreateRepository{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ncm='" + ncm + '\'' +
                ", preco=" + preco +
                '}';
    }
}
