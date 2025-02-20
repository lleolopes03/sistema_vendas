package com.br.sistema_teste.dto;

public class ProdutoResponseDto {
    private Long id;
    private String nome;
    private String descricao;
    private String ncm;
    private double preco;

    public ProdutoResponseDto() {
    }

    public ProdutoResponseDto(Long id, String nome, String descricao, String ncm, double preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ncm = ncm;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "ProdutoResponseDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ncm='" + ncm + '\'' +
                ", preco=" + preco +
                '}';
    }
}
