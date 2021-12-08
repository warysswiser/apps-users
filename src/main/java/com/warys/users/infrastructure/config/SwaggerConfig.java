package com.warys.users.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.doc.title}")
    private String title;
    @Value("${app.doc.description}")
    private String description;
    @Value("${app.doc.version}")
    private String version;
    @Value("${app.doc.url}")
    private String url;
    @Value("${app.doc.contact.name}")
    private String contactName;
    @Value("${app.doc.license.name}")
    private String license;
    @Value("${app.doc.license.url}")
    private String licenseUrl;
    @Value("${app.doc.contact.email}")
    private String contactEmail;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(title)
                        .description(description)
                        .version(version)
                        .license(new License().name(license).url(licenseUrl))
                        .contact(new Contact().name(contactName).url(url).email(contactEmail)))
                .externalDocs(new ExternalDocumentation()
                        .description(description)
                        .url(url));
    }
}
