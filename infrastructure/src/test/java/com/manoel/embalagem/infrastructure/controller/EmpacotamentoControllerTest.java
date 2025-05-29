package com.manoel.embalagem.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manoel.embalagem.api.generated.model.DimensaoDTO;
import com.manoel.embalagem.api.generated.model.PedidoRequestDTO;
import com.manoel.embalagem.api.generated.model.PedidoWrapperRequestDTO;
import com.manoel.embalagem.api.generated.model.ProdutoRequestDTO;
import com.manoel.embalagem.infrastructure.utils.IntegrationTest;
import com.manoel.embalagem.infrastructure.utils.WithMockJwtAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class EmpacotamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockJwtAuth(username = "admin", roles = {"USER"})
    @Test
    @DisplayName("Integração real com MockMvc - deve empacotar corretamente")
    void testEmpacotamentoCompleto() throws Exception {
        DimensaoDTO dimensao = new DimensaoDTO().altura(5).largura(5).comprimento(5);
        ProdutoRequestDTO produto = new ProdutoRequestDTO().produtoId("Mouse").dimensoes(dimensao);
        PedidoRequestDTO pedido = new PedidoRequestDTO().pedidoId(1).produtos(List.of(produto));
        PedidoWrapperRequestDTO wrapper = new PedidoWrapperRequestDTO().pedidos(List.of(pedido));

        mockMvc.perform(post("/api/empacotar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrapper)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos[0].pedido_id").value(1))
                .andExpect(jsonPath("$.pedidos[0].caixas[0].produtos[0]").value("Mouse"))
                .andExpect(jsonPath("$.pedidos[0].caixas[0].caixa_id").value("Caixa 1"));
    }
}
