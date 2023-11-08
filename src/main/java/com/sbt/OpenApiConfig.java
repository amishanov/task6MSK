package com.sbt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Counter System Api",
                description = "Система ведения счётчиков", version = "0.0.1",
                contact = @Contact(
                        name = "Mishanov Aleksey",
                        email = "ABoMishanov@sberbank.ru"
                )
        )
)
public class OpenApiConfig {
}
