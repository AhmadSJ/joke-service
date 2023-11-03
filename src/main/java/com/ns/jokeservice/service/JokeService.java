package com.ns.jokeservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.jokeservice.exception.ClientErrorException;
import com.ns.jokeservice.exception.ServerErrorException;
import com.ns.jokeservice.model.ErrorResponse;
import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.model.JokesFromApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JokeService {
    private final WebClient webClient;
    private final JokeServiceHelper jokeServiceHelper;

    private ObjectMapper objectMapper;

    @Autowired
    public JokeService(WebClient webClient, JokeServiceHelper jokeServiceHelper, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.jokeServiceHelper = jokeServiceHelper;
        this.objectMapper = objectMapper;
    }

    public JokeReply getRandomJokeGivenParameters(List<String> categories, Map<String, String> queryParams) {
        UriComponents uriComponents = buildUriWithQueryParameters(categories, queryParams);

        String response = webClient.get()
                .uri(uriComponents.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Object responseObject = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            if(jsonNode.has("error") && jsonNode.get("error").asText().equals("true")){
                ErrorResponse errorResponse = objectMapper.readValue(response, ErrorResponse.class);
                jokeServiceHelper.processError(errorResponse);
                return new JokeReply(0, "An error has occured");
            } else {
                JokesFromApiResponse jokeResponse = objectMapper.readValue(response, JokesFromApiResponse.class);
                return jokeServiceHelper.processJokes(jokeResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException while processing json string occured");
            return new JokeReply(0, "IOException while processing json string occured");
        }
    }

    public JokeReply getRandomJoke() {
        // Fetch jokes from the external API
        JokesFromApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("Any")
                        .queryParam("type", "single")
                        .queryParam("amount", 16)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxError(clientResponse))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxError(clientResponse))
                .bodyToMono(JokesFromApiResponse.class)
                .block();
        // Process the jokes and find the shortest safe joke
        return jokeServiceHelper.processJokes(response);
    }

    private UriComponents buildUriWithQueryParameters(List<String> categories, Map<String, String> queryParams) {
        String path = String.join(",", categories);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path);

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }

    private Mono<? extends Throwable> handle4xxError(ClientResponse clientResponse) {
        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
        return errorMessage.flatMap((message) -> {
            log.error(String.format("Error Response Code is %s and the message is %s", clientResponse.rawStatusCode(), message));
            throw new ClientErrorException(message);
        });
    }
    private Mono<? extends Throwable> handle5xxError(ClientResponse clientResponse) {
        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
        return errorMessage.flatMap((message) -> {
            log.error(String.format("Error Response Code is %s and the message is %s", clientResponse.rawStatusCode(), message));
            throw new ServerErrorException(message);
        });
    }

}

