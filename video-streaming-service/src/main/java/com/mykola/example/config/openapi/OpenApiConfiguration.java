package com.mykola.example.config.openapi;


import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Walkthrough API v1", version = "v1"),
        servers = {@Server(url = "/", description = "Default Server URL")})
public class OpenApiConfiguration {

}
