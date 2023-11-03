package com.ns.jokeservice.service;

import com.ns.jokeservice.exception.ApiErrorException;
import com.ns.jokeservice.model.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JokeServiceHelper {
    public JokeReply processJokes(JokesFromApiResponse response) {
        List<Joke> safeJokes = filterSafeJokes(response);
        return filterShortestJoke(safeJokes);
    }

    public List<Joke> filterSafeJokes(JokesFromApiResponse response) {
        return response.getJokes().stream()
                .filter(this::isJokeSafe)
                .collect(Collectors.toList());
    }

    public boolean isJokeSafe(Joke joke) {
        Flags flags = joke.getFlags();
        return joke.isSafe() && !flags.isSexist() && !flags.isExplicit();
    }

    public JokeReply filterShortestJoke(List<Joke> safeJokes) {
        Joke shortestJoke = safeJokes.stream()
                .min(Comparator.comparing(joke -> joke.getJoke().length()))
                .orElse(null);

        return (shortestJoke != null)
                ? new JokeReply(shortestJoke.getId(), shortestJoke.getJoke())
                : new JokeReply(0, "No safe jokes found");
    }

    public void processError(ErrorResponse response) {
        throw new ApiErrorException("An error has occured", response);
    }
}

