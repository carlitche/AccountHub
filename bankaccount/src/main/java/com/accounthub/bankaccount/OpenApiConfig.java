package com.accounthub.bankaccount;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info (
                title = "Bank Account Api",
                description = "REST Api Documentation for Bank Account Api",
                version = "1.0"
        ),
        servers = {
                @Server (
                        description = "local ENV",
                        url = "http://localhost:8200"
                )
        }
)
public class OpenApiConfig {
}
