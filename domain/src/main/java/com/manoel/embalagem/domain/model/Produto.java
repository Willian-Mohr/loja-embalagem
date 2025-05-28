package com.manoel.embalagem.domain.model;

public class Produto {
    private final String produtoId;
    private final Dimensao dimensao;

    public Produto(String produtoId, Dimensao dimensao) {
        this.produtoId = produtoId;
        this.dimensao = dimensao;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }
}