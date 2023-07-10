package pl.shopmatelist.shopmatelist.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Filip Politowski",
                        email = "fil.politowski@gmail.com",
                        url = "https://github.com/Filip-Politowski/ShopeMateList"
                ),
                description = "Dokumentacja do programu ShopMateList",
                title = "ShopMateList",
                version = "2.2"
        )
        ,
        servers =  {
        @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )},
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }


)
        @SecurityScheme(
                name = "bearerAuth",
                description = "JWT auth",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER
        )



public class OpenApiConfig {
}
// kolekcje
// lambdy
// wzorce projektowe
//wstrzykiwanie tylko przez konstruktor
// co to są poziomy dojrzałości REST API
// jak działają fizycznie bazy danych
// jakie są scopy bean w spring
//wielowątkowość poczytać deadlock livelock , problem konsumenta
// hash mapa
// web sockety
// docker
