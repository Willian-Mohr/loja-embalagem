package com.manoel.embalagem.api.controller;

import com.manoel.embalagem.api.dto.PedidoResponseDTO;
import com.manoel.embalagem.api.dto.PedidoWrapperRequestDTO;
import com.manoel.embalagem.api.mapper.PedidoMapper;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.domain.model.Pedido;
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
    public List<PedidoResponseDTO> empacotar(@RequestBody PedidoWrapperRequestDTO wrapper) {

        List<Pedido> pedidos = pedidoMapper.toDomainList(wrapper.pedidos);

        return pedidos.stream()
                .map(pedido -> {
                    List<ResultadoEmpacotamento> resultado = useCase.executar(pedido);
                    return pedidoMapper.toResponseDTO(pedido, resultado);
                }).collect(Collectors.toList());
    }
}
