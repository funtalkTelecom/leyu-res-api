package com.leyu.config;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ServletConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            container.addErrorPages(error404Page);
        };
    }
}