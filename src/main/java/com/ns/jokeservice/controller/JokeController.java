package com.ns.jokeservice.controller;

import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JokeController {

    private final JokeService jokeService;

    @Autowired
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }
    @GetMapping("/random-joke/")
    public JokeReply getRandomJokes(@RequestParam int amount) {
        return jokeService.getRandomJoke(amount);
    }

}
