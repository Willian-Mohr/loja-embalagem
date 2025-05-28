package com.manoel.embalagem.api.mapper;

import com.manoel.embalagem.api.dto.*;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.domain.model.Dimensao;
import com.manoel.embalagem.domain.model.Pedido;
import com.manoel.embalagem.domain.model.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public List<Pedido> toDomainList(List<PedidoRequestDTO> dtos) {
        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Pedido toDomain(PedidoRequestDTO dto) {
        return new Pedido(dto.pedido_id, dto.produtos.stream()
                .map(this::toProduto)
                .collect(Collectors.toList()));
    }

    private Produto toProduto(ProdutoRequestDTO dto) {
        DimensaoDTO d = dto.dimensoes;
        return new Produto(dto.produto_id, new Dimensao(d.altura, d.largura, d.comprimento));
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido, List<ResultadoEmpacotamento> resultado) {
        List<CaixaResponseDTO> caixas = resultado.stream()
                .map(r -> new CaixaResponseDTO(r.getCaixaId(), r.getProdutos(), r.getObservacao()))
                .collect(Collectors.toList());
        return new PedidoResponseDTO(pedido.getPedidoId(), caixas);
    }
}
