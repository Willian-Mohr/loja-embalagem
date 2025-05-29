package com.manoel.embalagem.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensaoTest {

    @Test
    @DisplayName("Deve calcular corretamente o volume da dimensão")
    void testGetVolume() {
        Dimensao dimensao = new Dimensao(2, 3, 4);
        assertEquals(24, dimensao.getVolume());
    }

    @Test
    @DisplayName("Deve retornar false quando a dimensão não cabe dentro da outra")
    void testCabeDentro_quandoNaoCabe() {
        Dimensao maior = new Dimensao(4, 2, 2);
        Dimensao menor = new Dimensao(3, 3, 3);
        assertFalse(maior.cabeDentro(menor));
    }

    @Test
    @DisplayName("Deve retornar true quando todas as dimensões forem menores")
    void testCabeDentro_todasMenores() {
        Dimensao d1 = new Dimensao(1, 1, 1);
        Dimensao d2 = new Dimensao(2, 2, 2);
        assertTrue(d1.cabeDentro(d2));
    }

    @Test
    @DisplayName("Deve retornar true quando todas as dimensões forem iguais")
    void testCabeDentro_todasIguais() {
        Dimensao d1 = new Dimensao(2, 2, 2);
        Dimensao d2 = new Dimensao(2, 2, 2);
        assertTrue(d1.cabeDentro(d2));
    }

    @Test
    @DisplayName("Deve retornar false quando altura for maior")
    void testCabeDentro_alturaMaior() {
        Dimensao d1 = new Dimensao(3, 2, 2);
        Dimensao d2 = new Dimensao(2, 2, 2);
        assertFalse(d1.cabeDentro(d2));
    }

    @Test
    @DisplayName("Deve retornar false quando largura for maior")
    void testCabeDentro_larguraMaior() {
        Dimensao d1 = new Dimensao(2, 3, 2);
        Dimensao d2 = new Dimensao(2, 2, 2);
        assertFalse(d1.cabeDentro(d2));
    }

    @Test
    @DisplayName("Deve retornar false quando comprimento for maior")
    void testCabeDentro_comprimentoMaior() {
        Dimensao d1 = new Dimensao(2, 2, 3);
        Dimensao d2 = new Dimensao(2, 2, 2);
        assertFalse(d1.cabeDentro(d2));
    }

    @Test
    @DisplayName("Deve retornar false quando todas as dimensões forem maiores")
    void testCabeDentro_todasMaiores() {
        Dimensao d1 = new Dimensao(4, 4, 4);
        Dimensao d2 = new Dimensao(3, 3, 3);
        assertFalse(d1.cabeDentro(d2));
    }
}
