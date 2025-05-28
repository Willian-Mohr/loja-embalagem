package com.manoel.embalagem.domain.model;

public class Caixa {
    private final String caixaId;
    private final Dimensao dimensao;

    public Caixa(String caixaId, Dimensao dimensao) {
        this.caixaId = caixaId;
        this.dimensao = dimensao;
    }

    public String getCaixaId() {
        return caixaId;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }
}