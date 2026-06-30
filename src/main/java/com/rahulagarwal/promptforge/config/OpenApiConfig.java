package com.rahulagarwal.promptforge.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI promptForgeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PromptForge AI API")
                        .description("Production-grade AI Software Generation Platform")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Rahul Agarwal")
                                .email("rahulagarwal544@gmai.com"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/Rahu0909/promptforge-ai"));
    }
}