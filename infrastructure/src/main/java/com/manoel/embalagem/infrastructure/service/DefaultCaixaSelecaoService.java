package com.manoel.embalagem.infrastructure.service;

import com.manoel.embalagem.domain.model.Caixa;
import com.manoel.embalagem.domain.model.Dimensao;
import com.manoel.embalagem.domain.model.Pedido;
import com.manoel.embalagem.domain.model.Produto;
import com.manoel.embalagem.domain.port.CaixaSelecaoServicePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultCaixaSelecaoService implements CaixaSelecaoServicePort {

    private final List<Caixa> caixasDisponiveis;

    public DefaultCaixaSelecaoService() {
        caixasDisponiveis = List.of(
                new Caixa("Caixa 1", new Dimensao(30, 40, 80)),
                new Caixa("Caixa 2", new Dimensao(80, 50, 40)),
                new Caixa("Caixa 3", new Dimensao(50, 80, 60))
        );
    }

    @Override
    public List<Map<String, List<String>>> empacotarPedido(Pedido pedido) {
        List<Produto> produtosRestantes = new ArrayList<>(pedido.getProdutos());
        List<Map<String, List<String>>> caixasUsadas = new ArrayList<>();

        // Ordena produtos por volume decrescente (first-fit-decreasing)
        produtosRestantes.sort((p1, p2) -> Integer.compare(
                p2.getDimensao().getVolume(), p1.getDimensao().getVolume()));

        while (!produtosRestantes.isEmpty()) {
            boolean algumProdutoEmpacotado = false;

            for (Caixa caixa : caixasDisponiveis) {
                List<Produto> encaixados = new ArrayList<>();
                int volumeLivre = caixa.getDimensao().getVolume();

                for (Produto produto : produtosRestantes) {
                    if (produto.getDimensao().cabeDentro(caixa.getDimensao())
                            && produto.getDimensao().getVolume() <= volumeLivre) {
                        encaixados.add(produto);
                        volumeLivre -= produto.getDimensao().getVolume();
                    }
                }

                if (!encaixados.isEmpty()) {
                    List<String> produtosIds = encaixados.stream()
                            .map(Produto::getProdutoId)
                            .collect(Collectors.toList());

                    Map<String, List<String>> caixaMap = new HashMap<>();
                    caixaMap.put(caixa.getCaixaId(), produtosIds);
                    caixasUsadas.add(caixaMap);

                    produtosRestantes.removeAll(encaixados);
                    algumProdutoEmpacotado = true;
                    break; // tenta próximo loop com os restantes
                }
            }

            if (!algumProdutoEmpacotado) {
                // Nenhuma caixa pode conter os produtos restantes individualmente
                Produto naoEmpacotavel = produtosRestantes.remove(0);
                Map<String, List<String>> erroMap = new HashMap<>();
                erroMap.put(null, List.of(naoEmpacotavel.getProdutoId()));
                caixasUsadas.add(erroMap);
            }
        }

        return caixasUsadas;
    }
}