package com.manoel.embalagem.infrastructure.mapper;

import com.manoel.embalagem.api.generated.model.*;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.core.domain.Dimensao;
import com.manoel.embalagem.core.domain.Pedido;
import com.manoel.embalagem.core.domain.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PedidoMapper {

    public List<Pedido> toDomainList(List<PedidoRequestDTO> dtos) {
        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Pedido toDomain(PedidoRequestDTO dto) {
        return new Pedido(dto.getPedidoId(), dto.getProdutos().stream()
                .map(this::toProduto)
                .collect(Collectors.toList()));
    }

    private Produto toProduto(ProdutoRequestDTO dto) {
        DimensaoDTO d = dto.getDimensoes();
        return new Produto(dto.getProdutoId(), new Dimensao(d.getAltura(), d.getLargura(), d.getComprimento()));
    }

    public PedidoResponseDTO toResponse(Pedido pedido, List<ResultadoEmpacotamento> resultado) {
        List<CaixaResponseDTO> caixas = resultado.stream()
                .map(r -> new CaixaResponseDTO(r.getCaixaId(), r.getProdutos(), r.getObservacao()))
                .collect(Collectors.toList());
        return new PedidoResponseDTO(pedido.getPedidoId(), caixas);
    }

    public PedidoResponseWrapperDTO toResponseWrapper(List<Pedido> pedidos, List<List<ResultadoEmpacotamento>> resultados) {
        List<PedidoResponseDTO> responses = IntStream.range(0, pedidos.size())
                .mapToObj(i -> toResponse(pedidos.get(i), resultados.get(i)))
                .collect(Collectors.toList());

        return new PedidoResponseWrapperDTO(responses);
    }
}
