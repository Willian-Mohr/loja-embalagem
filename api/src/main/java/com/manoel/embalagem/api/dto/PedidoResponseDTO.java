package com.manoel.embalagem.api.dto;

import java.util.List;

public class PedidoResponseDTO {
    public int pedido_id;
    public List<CaixaResponseDTO> caixas;

    public PedidoResponseDTO(int pedidoId, List<CaixaResponseDTO> caixas) {
        this.pedido_id = pedidoId;
        this.caixas = caixas;
    }
}
