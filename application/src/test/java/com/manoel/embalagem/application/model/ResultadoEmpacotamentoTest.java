package com.manoel.embalagem.application.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResultadoEmpacotamentoTest {

    @Test
    @DisplayName("Deve criar corretamente o objeto com todos os campos via construtor")
    void testConstrutorCompleto() {
        List<String> produtos = Arrays.asList("Mouse", "Teclado");
        ResultadoEmpacotamento resultado = new ResultadoEmpacotamento("Caixa 1", produtos, "Nenhuma observação");

        assertEquals("Caixa 1", resultado.getCaixaId());
        assertEquals(produtos, resultado.getProdutos());
        assertEquals("Nenhuma observação", resultado.getObservacao());
    }

    @Test
    @DisplayName("Deve criar corretamente o objeto com caixa via método estático")
    void testComCaixa() {
        List<String> produtos = Arrays.asList("Notebook", "Monitor");
        ResultadoEmpacotamento resultado = ResultadoEmpacotamento.comCaixa("Caixa 3", produtos);

        assertEquals("Caixa 3", resultado.getCaixaId());
        assertEquals(produtos, resultado.getProdutos());
        assertNull(resultado.getObservacao());
    }

    @Test
    @DisplayName("Deve criar corretamente o objeto sem caixa via método estático")
    void testSemCaixa() {
        List<String> produtos = Arrays.asList("Cadeira Gamer");
        ResultadoEmpacotamento resultado = ResultadoEmpacotamento.semCaixa(produtos, "Produto não cabe em nenhuma caixa");

        assertNull(resultado.getCaixaId());
        assertEquals(produtos, resultado.getProdutos());
        assertEquals("Produto não cabe em nenhuma caixa", resultado.getObservacao());
    }
}
