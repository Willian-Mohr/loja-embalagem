package com.manoel.embalagem.application.usecase;

import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.domain.model.Pedido;
import com.manoel.embalagem.domain.port.CaixaSelecaoServicePort;

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
        List<Map<String, List<String>>> resultado = caixaSelecaoServicePort.empacotarPedido(pedido);

        return resultado.stream()
                .flatMap(map -> map.entrySet().stream()
                        .map(entry -> {
                            String caixaId = entry.getKey();
                            List<String> produtos = entry.getValue();
                            if (caixaId == null) {
                                return ResultadoEmpacotamento.semCaixa(produtos, "Produto não cabe em nenhuma caixa disponível.");
                            } else {
                                return ResultadoEmpacotamento.comCaixa(caixaId, produtos);
                            }
                        }))
                .collect(Collectors.toList());
    }
}