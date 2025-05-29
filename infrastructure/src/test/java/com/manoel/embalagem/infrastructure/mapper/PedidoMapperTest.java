package com.manoel.embalagem.infrastructure.mapper;

import com.manoel.embalagem.api.generated.model.*;
import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.core.domain.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PedidoMapperTest {

    private final PedidoMapper mapper = new PedidoMapper();

    @Test
    @DisplayName("Deve converter PedidoRequestDTO para Pedido (domínio)")
    void testToDomain() {
        DimensaoDTO dimensao = new DimensaoDTO().altura(5).largura(5).comprimento(5);
        ProdutoRequestDTO produto = new ProdutoRequestDTO().produtoId("Mouse").dimensoes(dimensao);
        PedidoRequestDTO dto = new PedidoRequestDTO().pedidoId(1).produtos(List.of(produto));

        Pedido pedido = mapper.toDomain(dto);

        assertEquals(1, pedido.getPedidoId());
        assertEquals(1, pedido.getProdutos().size());
        assertEquals("Mouse", pedido.getProdutos().get(0).getProdutoId());
    }

    @Test
    @DisplayName("Deve converter ResultadoEmpacotamento para PedidoResponseDTO")
    void testToResponse() {
        Pedido pedido = new Pedido(10, Collections.emptyList());
        ResultadoEmpacotamento r1 = ResultadoEmpacotamento.comCaixa("Caixa A", List.of("Mouse", "Teclado"));

        PedidoResponseDTO response = mapper.toResponse(pedido, List.of(r1));

        assertEquals(10, response.getPedidoId());
        assertEquals(1, response.getCaixas().size());
        assertEquals("Caixa A", response.getCaixas().get(0).getCaixaId());
        assertEquals(List.of("Mouse", "Teclado"), response.getCaixas().get(0).getProdutos());
    }

    @Test
    @DisplayName("Deve agrupar múltiplos pedidos e respostas com toResponseWrapper")
    void testToResponseWrapper() {
        Pedido p1 = new Pedido(1, Collections.emptyList());
        Pedido p2 = new Pedido(2, Collections.emptyList());

        ResultadoEmpacotamento r1 = ResultadoEmpacotamento.comCaixa("Caixa 1", List.of("Mouse"));
        ResultadoEmpacotamento r2 = ResultadoEmpacotamento.semCaixa(List.of("Monitor"), "Produto não cabe");

        PedidoResponseWrapperDTO wrapper = mapper.toResponseWrapper(
                List.of(p1, p2),
                List.of(List.of(r1), List.of(r2))
        );

        assertEquals(2, wrapper.getPedidos().size());

        // Pedido 1
        PedidoResponseDTO pedido1 = wrapper.getPedidos().get(0);
        assertEquals(1, pedido1.getPedidoId());
        assertEquals("Caixa 1", pedido1.getCaixas().get(0).getCaixaId());
        assertEquals(List.of("Mouse"), pedido1.getCaixas().get(0).getProdutos());
        assertNull(pedido1.getCaixas().get(0).getObservacao());

        // Pedido 2
        PedidoResponseDTO pedido2 = wrapper.getPedidos().get(1);
        assertEquals(2, pedido2.getPedidoId());
        assertNull(pedido2.getCaixas().get(0).getCaixaId());
        assertEquals(List.of("Monitor"), pedido2.getCaixas().get(0).getProdutos());
        assertEquals("Produto não cabe", pedido2.getCaixas().get(0).getObservacao());
    }
}
