package com.l2code.embalagem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Embalagem - Loja do Seu Manoel")
                        .version("1.0")
                        .description("API para automatizar o processo de embalagem de pedidos, calculando a melhor forma de alocar produtos em caixas de papelão disponíveis.")
                        .contact(new Contact()
                                .name("L2Code")
                                .url("https://l2code.com.br")
                                .email("contato@l2code.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}