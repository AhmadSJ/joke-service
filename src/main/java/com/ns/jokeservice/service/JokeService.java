package com.ns.jokeservice.service;

import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.model.JokesFromApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JokeService {
    private final WebClient webClient;
    private final JokeServiceHelper jokeServiceHelper;

    @Autowired
    public JokeService(WebClient webClient, JokeServiceHelper jokeServiceHelper) {
        this.webClient = webClient;
        this.jokeServiceHelper = jokeServiceHelper;
    }

    public JokeReply getRandomJoke(int amount) {
        // Fetch jokes from the external API
        JokesFromApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("Any")
                        .queryParam("type", "single")
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .bodyToMono(JokesFromApiResponse.class)
                .block();
        // Process the jokes and find the shortest safe joke
        return jokeServiceHelper.processJokes(response);
    }
}

