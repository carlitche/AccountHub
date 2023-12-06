package com.accounthub.banktransaction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info (
                title = "Bank Transaction Api",
                description = "REST Api Documentation for Bank Transaction Api",
                version = "1.0"
        ),
        servers = {
                @Server (
                        description = "local ENV",
                        url = "http://localhost:8100"
                )
        }
)
public class OpenApiConfig {
}
