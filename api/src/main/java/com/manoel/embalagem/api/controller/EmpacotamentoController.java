package com.manoel.embalagem.api.controller;

import com.manoel.embalagem.api.dto.CaixaResponseDTO;
import com.manoel.embalagem.api.dto.PedidoRequestDTO;
import com.manoel.embalagem.api.dto.PedidoResponseDTO;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.domain.model.Dimensao;
import com.manoel.embalagem.domain.model.Pedido;
import com.manoel.embalagem.domain.model.Produto;
import com.manoel.embalagem.infrastructure.service.DefaultCaixaSelecaoService;
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

    public EmpacotamentoController() {
        this.useCase = new EmpacotarPedidoUseCase(new DefaultCaixaSelecaoService());
    }

    @PostMapping
    public List<PedidoResponseDTO> empacotar(@RequestBody List<PedidoRequestDTO> pedidosRequest) {
        return pedidosRequest.stream().map(dto -> {
            Pedido pedido = new Pedido(dto.pedido_id, dto.produtos.stream()
                    .map(p -> new Produto(p.produto_id,
                            new Dimensao(p.dimensoes.altura, p.dimensoes.largura, p.dimensoes.comprimento)))
                    .collect(Collectors.toList()));

            List<ResultadoEmpacotamento> resultado = useCase.executar(pedido);

            List<CaixaResponseDTO> caixas = resultado.stream()
                    .map(r -> new CaixaResponseDTO(r.getCaixaId(), r.getProdutos(), r.getObservacao()))
                    .collect(Collectors.toList());

            return new PedidoResponseDTO(dto.pedido_id, caixas);
        }).collect(Collectors.toList());
    }
}
