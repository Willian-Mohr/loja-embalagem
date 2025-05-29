package com.manoel.embalagem.application.usercase;

import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.core.domain.Pedido;

import java.util.List;

public interface EmpacotarPedidoUseCase {
    List<ResultadoEmpacotamento> executar(Pedido pedido);
}