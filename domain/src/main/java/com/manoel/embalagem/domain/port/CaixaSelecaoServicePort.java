package com.manoel.embalagem.domain.port;

import com.manoel.embalagem.domain.model.Pedido;

import java.util.List;
import java.util.Map;

public interface CaixaSelecaoServicePort {
    List<Map<String, List<String>>> empacotarPedido(Pedido pedido);
}