package com.manoel.embalagem.core.domain;

public class Dimensao {
    private final int altura;
    private final int largura;
    private final int comprimento;

    public Dimensao(int altura, int largura, int comprimento) {
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public int getComprimento() {
        return comprimento;
    }

    public int getVolume() {
        return altura * largura * comprimento;
    }

    public boolean cabeDentro(Dimensao outra) {
        return this.altura <= outra.altura &&
                this.largura <= outra.largura &&
                this.comprimento <= outra.comprimento;
    }
}