package com.manoel.embalagem.infrastructure.controller;

import com.manoel.embalagem.api.generated.model.PedidoResponseWrapperDTO;
import com.manoel.embalagem.api.generated.model.PedidoWrapperRequestDTO;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.application.usercase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.core.domain.Pedido;
import com.manoel.embalagem.infrastructure.mapper.PedidoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empacotar")
public class EmpacotamentoController {

    private final EmpacotarPedidoUseCase useCase;
    private final PedidoMapper pedidoMapper;

    public EmpacotamentoController(EmpacotarPedidoUseCase useCase, PedidoMapper pedidoMapper) {
        this.useCase = useCase;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    public PedidoResponseWrapperDTO empacotar(@RequestBody PedidoWrapperRequestDTO wrapper) {

        List<Pedido> pedidos = pedidoMapper.toDomainList(wrapper.getPedidos());

        List<List<ResultadoEmpacotamento>> resultados = pedidos.stream()
                .map(useCase::executar)
                .collect(Collectors.toList());

        return pedidoMapper.toResponseWrapper(pedidos, resultados);
    }
}
