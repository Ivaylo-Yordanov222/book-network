package com.ivo.book_network.openapi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server


@OpenAPIDefinition(
    info = Info(
        contact = Contact(
            name = "Ivaylo",
            email = "ivayloyordanov222@gmail.com",
            url = "https://ivo.com"
        ),
        description = "OpenApi documentation for Spring Security",
        title = "OpenApi specification - Ivaylo",
        version = "1.0",
        license = License(name = "Licence name", url = "https://some-url.com"),
        termsOfService = "Terms of service"
    ),
    servers = [Server(description = "Local ENV", url = "http://localhost:8080/api/v1"), Server(
        description = "PROD ENV",
        url = "https://ivo.com"
    )],
    security = [SecurityRequirement(name = "bearerAuth")]
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
//    type = SecuritySchemeType.OAUTH2,
//    flows = OAuthFlows(clientCredentials = OAuthFlow(authorizationUrl = "http://localhost:9090/realms/book-social-network/protocol/openid-connect/auth")),
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenApiConfig