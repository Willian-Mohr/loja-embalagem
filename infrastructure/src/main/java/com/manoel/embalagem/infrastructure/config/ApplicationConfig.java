package com.manoel.embalagem.infrastructure.config;

import com.manoel.embalagem.application.usercase.EmpacotarPedidoUseCase;
import com.manoel.embalagem.core.port.CaixaSelecaoServicePort;
import com.manoel.embalagem.infrastructure.service.DefaultCaixaSelecaoService;
import com.manoel.embalagem.usecase.service.EmpacotarPedidoUseCaseImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CaixasProperties.class, SwaggerProperties.class})
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