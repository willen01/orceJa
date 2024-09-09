package com.willen.OrceJa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "OrceJa API service",
                description = "Endpoints da aplicação OrceJa",
                version = "1.0",
                contact = @Contact(
                        name = "Willen dos Santos",
                        email = "willen.sesantos@gmail.com",
                        url = "https://www.linkedin.com/in/willen-dos-santos/"
                )

        )
)
public class OpenAPIConfiguration {
}
