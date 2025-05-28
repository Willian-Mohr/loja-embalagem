package com.manoel.embalagem.application.usecase;

import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.domain.model.Pedido;

import java.util.List;

public interface EmpacotarPedidoUseCase {
    List<ResultadoEmpacotamento> executar(Pedido pedido);
}