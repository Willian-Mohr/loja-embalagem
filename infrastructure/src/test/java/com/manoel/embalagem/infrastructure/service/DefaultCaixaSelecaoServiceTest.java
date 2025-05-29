package com.manoel.embalagem.infrastructure.service;

import com.manoel.embalagem.core.domain.Dimensao;
import com.manoel.embalagem.core.domain.Pedido;
import com.manoel.embalagem.core.domain.Produto;
import com.manoel.embalagem.infrastructure.config.CaixasProperties;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultCaixaSelecaoServiceTest {

    @Test
    void deveEmpacotarProdutoQueCabeNaCaixa() {
        Produto produto = new Produto("Mouse", new Dimensao(5, 5, 5));
        Pedido pedido = new Pedido(1, List.of(produto));

        DefaultCaixaSelecaoService service = createServiceWithCaixas(Map.of("Caixa 1", new Dimensao(10, 10, 10)));

        List<Map<String, List<String>>> resultado = service.empacotarPedido(pedido);

        assertEquals(1, resultado.size());
        assertEquals(List.of("Mouse"), resultado.get(0).get("Caixa 1"));
    }

    @Test
    void deveSepararProdutoQueNaoCabeEmNenhumaCaixa() {
        Produto produto = new Produto("Monitor", new Dimensao(15, 15, 15));
        Pedido pedido = new Pedido(2, List.of(produto));

        DefaultCaixaSelecaoService service = createServiceWithCaixas(Map.of("Caixa 1", new Dimensao(10, 10, 10)));

        List<Map<String, List<String>>> resultado = service.empacotarPedido(pedido);

        assertEquals(1, resultado.size());
        assertEquals(Arrays.asList("Monitor"), resultado.get(0).get(null));
    }

    @Test
    void deveEmpacotarProdutosEmCaixasDiferentes() {

        // Produtos
        Produto mouse = new Produto("Mouse", new Dimensao(5, 5, 5));
        Produto teclado = new Produto("Teclado", new Dimensao(10, 10, 2));
        Produto webcam = new Produto("Webcam", new Dimensao(5, 5, 5));
        Pedido pedido = new Pedido(3, Arrays.asList(mouse, teclado, webcam));

        // Caixas disponíveis
        Map<String, Dimensao> caixas = new LinkedHashMap<>();
        caixas.put("Caixa A", new Dimensao(6, 6, 7));
        caixas.put("Caixa B", new Dimensao(10, 10, 10));

        DefaultCaixaSelecaoService service = createServiceWithCaixas(caixas);

        List<Map<String, List<String>>> resultado = service.empacotarPedido(pedido);

        // Deve retornar duas caixas distintas
        assertEquals(2, resultado.size());

        Map<String, List<String>> mapB = resultado.stream()
                .filter(m -> m.containsKey("Caixa A"))
                .findFirst()
                .orElseThrow();
        assertEquals(List.of("Mouse", "Webcam"), mapB.get("Caixa A"));

        // Verificar conteúdo por caixa
        Map<String, List<String>> mapA = resultado.stream()
                .filter(m -> m.containsKey("Caixa B"))
                .findFirst()
                .orElseThrow();
        assertEquals(List.of("Teclado"), mapA.get("Caixa B"));
    }

    private DefaultCaixaSelecaoService createServiceWithCaixas(Map<String, Dimensao> caixas) {
        List<CaixasProperties.CaixaConfig> configList = caixas.entrySet().stream().map(entry -> {
            CaixasProperties.DimensaoConfig d = new CaixasProperties.DimensaoConfig();
            d.setAltura(entry.getValue().getAltura());
            d.setLargura(entry.getValue().getLargura());
            d.setComprimento(entry.getValue().getComprimento());

            CaixasProperties.CaixaConfig c = new CaixasProperties.CaixaConfig();
            c.setId(entry.getKey());
            c.setDimensao(d);
            return c;
        }).toList();

        CaixasProperties props = new CaixasProperties();
        props.setDisponiveis(configList);

        return new DefaultCaixaSelecaoService(props);
    }
}
