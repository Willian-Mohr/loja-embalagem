package com.manoel.embalagem.usecase.service;

import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.application.usercase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.core.domain.Pedido;
import com.manoel.embalagem.core.port.CaixaSelecaoServicePort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmpacotarPedidoUseCaseImpl implements EmpacotarPedidoUseCase {

    private final CaixaSelecaoServicePort caixaSelecaoServicePort;

    public EmpacotarPedidoUseCaseImpl(CaixaSelecaoServicePort caixaSelecaoServicePort) {
        this.caixaSelecaoServicePort = caixaSelecaoServicePort;
    }

    @Override
    public List<ResultadoEmpacotamento> executar(Pedido pedido) {
        List<Map<String, List<String>>> resultadoBruto = caixaSelecaoServicePort.empacotarPedido(pedido);
        return transformarResultados(resultadoBruto);
    }

    private List<ResultadoEmpacotamento> transformarResultados(List<Map<String, List<String>>> resultadoBruto) {
        return resultadoBruto.stream()
                .flatMap(map -> map.entrySet().stream()
                        .map(this::mapearResultado))
                .collect(Collectors.toList());
    }

    private ResultadoEmpacotamento mapearResultado(Map.Entry<String, List<String>> entry) {
        String caixaId = entry.getKey();
        List<String> produtos = entry.getValue();

        if (caixaId == null) {
            return ResultadoEmpacotamento.semCaixa(produtos, "Produto não cabe em nenhuma caixa disponível.");
        }

        return ResultadoEmpacotamento.comCaixa(caixaId, produtos);
    }
}