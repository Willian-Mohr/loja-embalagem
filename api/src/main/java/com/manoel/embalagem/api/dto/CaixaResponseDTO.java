package com.manoel.embalagem.api.dto;

import java.util.List;

public class CaixaResponseDTO {
    public String caixa_id;
    public List<String> produtos;
    public String observacao;

    public CaixaResponseDTO(String caixaId, List<String> produtos, String observacao) {
        this.caixa_id = caixaId;
        this.produtos = produtos;
        this.observacao = observacao;
    }
}