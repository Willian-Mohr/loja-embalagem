package com.manoel.embalagem.core.port;

import com.manoel.embalagem.core.domain.Pedido;

import java.util.List;
import java.util.Map;

public interface CaixaSelecaoServicePort {
    List<Map<String, List<String>>> empacotarPedido(Pedido pedido);
}