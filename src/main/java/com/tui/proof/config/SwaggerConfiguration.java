package com.tui.proof.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Tui test",
        version = "1.0.0",
        description = "API. Pilotes of the great Miquel Montoro is a simple API that let users"
        + " create/update or search with customer's data the relative orders",
        contact = @Contact(
                name = "Stavros Samaras",
                email = "stavsamaras@gmai.com"
        )) )
@SecurityScheme(
        name = "user",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {return new OpenAPI();}
}
