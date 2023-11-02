package com.ns.jokeservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class JokeReply {
    private int id;
    private String randomJoke;

    public JokeReply(int id, String randomJoke) {
        this.id = id;
        this.randomJoke = randomJoke;
    }
}
