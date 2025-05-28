package com.manoel.embalagem.infrastructure.service;

import com.manoel.embalagem.domain.model.Caixa;
import com.manoel.embalagem.domain.model.Dimensao;
import com.manoel.embalagem.domain.model.Pedido;
import com.manoel.embalagem.domain.model.Produto;
import com.manoel.embalagem.domain.port.CaixaSelecaoServicePort;
import com.manoel.embalagem.infrastructure.config.CaixasProperties;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultCaixaSelecaoService implements CaixaSelecaoServicePort {

    private final List<Caixa> caixasDisponiveis;

    public DefaultCaixaSelecaoService(CaixasProperties config) {
        this.caixasDisponiveis = config.getDisponiveis().stream()
                .map(c -> new Caixa(
                        c.getId(),
                        new Dimensao(
                                c.getDimensao().getAltura(),
                                c.getDimensao().getLargura(),
                                c.getDimensao().getComprimento()
                        )))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, List<String>>> empacotarPedido(Pedido pedido) {
        return processarPedido(pedido.getProdutos());
    }

    private List<Map<String, List<String>>> processarPedido(List<Produto> produtos) {
        List<Produto> produtosRestantes = ordenarPorVolume(produtos);
        List<Map<String, List<String>>> caixasUsadas = new ArrayList<>();

        while (!produtosRestantes.isEmpty()) {
            boolean empacotado = tentarEmpacotar(produtosRestantes, caixasUsadas);
            if (!empacotado) {
                marcarNaoEmpacotavel(produtosRestantes, caixasUsadas);
            }
        }

        return caixasUsadas;
    }

    private List<Produto> ordenarPorVolume(List<Produto> produtos) {
        return produtos.stream()
                .sorted(Comparator.comparingInt((Produto p) -> p.getDimensao().getVolume()).reversed())
                .collect(Collectors.toList());
    }

    private boolean tentarEmpacotar(List<Produto> produtosRestantes, List<Map<String, List<String>>> caixasUsadas) {
        for (Caixa caixa : caixasDisponiveis) {
            List<Produto> encaixados = encontrarProdutosQueCabem(produtosRestantes, caixa);

            if (!encaixados.isEmpty()) {
                adicionarProdutosNaCaixa(encaixados, caixa, caixasUsadas);
                produtosRestantes.removeAll(encaixados);
                return true;
            }
        }
        return false;
    }

    private List<Produto> encontrarProdutosQueCabem(List<Produto> produtos, Caixa caixa) {
        List<Produto> encaixados = new ArrayList<>();
        int volumeDisponivel = caixa.getDimensao().getVolume();

        for (Produto produto : produtos) {
            if (produto.getDimensao().cabeDentro(caixa.getDimensao())
                    && produto.getDimensao().getVolume() <= volumeDisponivel) {
                encaixados.add(produto);
                volumeDisponivel -= produto.getDimensao().getVolume();
            }
        }

        return encaixados;
    }

    private void adicionarProdutosNaCaixa(List<Produto> produtos, Caixa caixa, List<Map<String, List<String>>> caixasUsadas) {
        List<String> ids = produtos.stream()
                .map(Produto::getProdutoId)
                .collect(Collectors.toList());

        Map<String, List<String>> caixaMap = new HashMap<>();
        caixaMap.put(caixa.getCaixaId(), ids);
        caixasUsadas.add(caixaMap);
    }

    private void marcarNaoEmpacotavel(List<Produto> produtosRestantes, List<Map<String, List<String>>> caixasUsadas) {
        Produto naoEmpacotavel = produtosRestantes.remove(0);

        Map<String, List<String>> erroMap = new HashMap<>();
        erroMap.put(null, Collections.singletonList(naoEmpacotavel.getProdutoId()));

        caixasUsadas.add(erroMap);
    }
}