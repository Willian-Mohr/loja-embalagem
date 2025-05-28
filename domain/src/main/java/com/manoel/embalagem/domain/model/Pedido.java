package com.manoel.embalagem.domain.model;

import java.util.List;

public class Pedido {
    private final int pedidoId;
    private final List<Produto> produtos;

    public Pedido(int pedidoId, List<Produto> produtos) {
        this.pedidoId = pedidoId;
        this.produtos = produtos;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
