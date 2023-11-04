package com.ns.jokeservice.controller;

import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JokeController {

    private final JokeService jokeService;

    @Autowired
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }
    @GetMapping("/random-joke/")
    public ResponseEntity<JokeReply> getRandomJoke() {
        /*
        This api-endpoint can be called to return a random joke that safe, not sexist and not explicit.
         */
        return new ResponseEntity<>(jokeService.getRandomJoke(), HttpStatus.FOUND);
    }

    @GetMapping("/specify-joke/{categories}")
    public JokeReply getSpecifiedJoke(@PathVariable List<String> categories, @RequestParam Map<String, String> queryParams) {
        /*
        This api-endpoint can be called to return a random joke that is safe, not sexist and not explicit. The user can
        provide categories and query parameters to specify the types of jokes being retrieved for selection.
         */
        return jokeService.getRandomJokeGivenParameters(categories, queryParams);
    }

}
