package com.jota.hub.challenge.infra.springdoc;

import com.jota.hub.challenge.controllers.AnswerController;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    static {
        SpringDocUtils.getConfig().addRestControllers(AnswerController.class);
    }

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                ).info(new Info()
                .title("Forum Hub API")
                .description("API Rest da aplicação Fórum HUB, sendo o challenge final do bootcamp Oracle Next Education (ONE).")
                .contact(new Contact()
                        .name("João Pedro dos Santos Medeiros")
                        .email("jopesame@gmail.com"))
                );
    }
}
