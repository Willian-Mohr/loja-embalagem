package com.manoel.embalagem.api.config;

import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.application.usecase.EmpacotarPedidoUseCaseImpl;
import com.manoel.embalagem.domain.port.CaixaSelecaoServicePort;
import com.manoel.embalagem.infrastructure.config.CaixasProperties;
import com.manoel.embalagem.infrastructure.service.DefaultCaixaSelecaoService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CaixasProperties.class)
public class ApplicationConfig {

    @Bean
    public CaixaSelecaoServicePort caixaSelecaoService(CaixasProperties properties) {
        return new DefaultCaixaSelecaoService(properties);
    }

    @Bean
    public EmpacotarPedidoUseCase empacotarPedidoUseCase(CaixaSelecaoServicePort service) {
        return new EmpacotarPedidoUseCaseImpl(service);
    }
}