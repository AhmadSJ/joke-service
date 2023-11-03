package com.ns.jokeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    private static final String BASE_API_URL = "https://v2.jokeapi.dev/joke/"; //Any?type=single&amount=16";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

