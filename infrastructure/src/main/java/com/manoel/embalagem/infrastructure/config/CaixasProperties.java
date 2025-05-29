package com.manoel.embalagem.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "caixas")
public class CaixasProperties {

    private List<CaixaConfig> disponiveis;

    public List<CaixaConfig> getDisponiveis() {
        return disponiveis;
    }

    public void setDisponiveis(List<CaixaConfig> disponiveis) {
        this.disponiveis = disponiveis;
    }

    public static class CaixaConfig {
        private String id;
        private DimensaoConfig dimensao;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DimensaoConfig getDimensao() {
            return dimensao;
        }

        public void setDimensao(DimensaoConfig dimensao) {
            this.dimensao = dimensao;
        }
    }

    public static class DimensaoConfig {
        private int altura;
        private int largura;
        private int comprimento;

        public int getAltura() {
            return altura;
        }

        public void setAltura(int altura) {
            this.altura = altura;
        }

        public int getLargura() {
            return largura;
        }

        public void setLargura(int largura) {
            this.largura = largura;
        }

        public int getComprimento() {
            return comprimento;
        }

        public void setComprimento(int comprimento) {
            this.comprimento = comprimento;
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("Caixas carregadas:");
        disponiveis.forEach(c ->
                System.out.println(" - " + c.getId() + ": " + c.getDimensao().getAltura() + "x" +
                        c.getDimensao().getLargura() + "x" + c.getDimensao().getComprimento()));
    }
}
