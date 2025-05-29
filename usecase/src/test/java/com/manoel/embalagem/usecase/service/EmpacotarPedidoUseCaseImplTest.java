package com.manoel.embalagem.usecase.service;

import com.manoel.embalagem.application.model.ResultadoEmpacotamento;
import com.manoel.embalagem.core.domain.Pedido;
import com.manoel.embalagem.core.port.CaixaSelecaoServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpacotarPedidoUseCaseImplTest {

    @Mock
    private Pedido pedido;

    @Mock
    private CaixaSelecaoServicePort caixaSelecaoServicePort;

    @InjectMocks
    private EmpacotarPedidoUseCaseImpl useCase;

    @Test
    @DisplayName("Deve retornar empacotamento com e sem caixa corretamente")
    void testExecutarComResultadosDiversos() {

        Map<String, List<String>> comCaixa = new HashMap<>();
        comCaixa.put("Caixa 1", Arrays.asList("Mouse", "Teclado"));

        Map<String, List<String>> semCaixa = new HashMap<>();
        semCaixa.put(null, Collections.singletonList("Cadeira Gamer"));

        when(caixaSelecaoServicePort.empacotarPedido(pedido))
                .thenReturn(Arrays.asList(comCaixa, semCaixa));

        List<ResultadoEmpacotamento> resultados = useCase.executar(pedido);

        assertEquals(2, resultados.size());

        ResultadoEmpacotamento r1 = resultados.get(0);
        assertEquals("Caixa 1", r1.getCaixaId());
        assertEquals(Arrays.asList("Mouse", "Teclado"), r1.getProdutos());
        assertNull(r1.getObservacao());

        ResultadoEmpacotamento r2 = resultados.get(1);
        assertNull(r2.getCaixaId());
        assertEquals(Collections.singletonList("Cadeira Gamer"), r2.getProdutos());
        assertEquals("Produto não cabe em nenhuma caixa disponível.", r2.getObservacao());
    }
}
