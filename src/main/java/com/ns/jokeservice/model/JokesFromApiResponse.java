package com.ns.jokeservice.model;

import lombok.Data;

import java.util.List;

@Data
public class JokesFromApiResponse {
    private boolean error;
    private int amount;
    private List<Joke> jokes;
}
