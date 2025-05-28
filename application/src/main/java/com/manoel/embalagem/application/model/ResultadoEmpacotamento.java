package com.manoel.embalagem.application.model;

import java.util.List;

public class ResultadoEmpacotamento {

    private final String caixaId;
    private final List<String> produtos;
    private final String observacao;

    public ResultadoEmpacotamento(String caixaId, List<String> produtos, String observacao) {
        this.caixaId = caixaId;
        this.produtos = produtos;
        this.observacao = observacao;
    }

    public String getCaixaId() {
        return caixaId;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public String getObservacao() {
        return observacao;
    }

    public static ResultadoEmpacotamento comCaixa(String caixaId, List<String> produtos) {
        return new ResultadoEmpacotamento(caixaId, produtos, null);
    }

    public static ResultadoEmpacotamento semCaixa(List<String> produtos, String observacao) {
        return new ResultadoEmpacotamento(null, produtos, observacao);
    }
}