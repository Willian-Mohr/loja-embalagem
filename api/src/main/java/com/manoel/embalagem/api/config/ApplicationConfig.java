package com.manoel.embalagem.api.config;

import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCaseImpl;
import com.manoel.embalagem.domain.port.CaixaSelecaoServicePort;
import com.manoel.embalagem.infrastructure.service.DefaultCaixaSelecaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CaixaSelecaoServicePort caixaSelecaoService() {
        return new DefaultCaixaSelecaoService();
    }

    @Bean
    public EmpacotarPedidoUseCase empacotarPedidoUseCase(CaixaSelecaoServicePort service) {
        return new EmpacotarPedidoUseCaseImpl(service);
    }
}