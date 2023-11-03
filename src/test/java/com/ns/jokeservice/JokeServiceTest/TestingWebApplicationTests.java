package com.ns.jokeservice.JokeServiceTest;

import com.ns.jokeservice.controller.JokeController;
import com.ns.jokeservice.exception.ClientErrorException;
import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingWebApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private JokeController jokeController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JokeService jokeService;

    //SmokeTest
    @Test()
    void contextLoads() {
        assertThat(jokeController, is(notNullValue()));
    }

    @Test
    public void getShouldReturnOneSafeJoke() {
        JokeReply response = restTemplate.getForObject("http://localhost:" + port + "/api/random-joke/", JokeReply.class);
        assertThat(response.getRandomJoke(), is(notNullValue()));
    }

    @Test
    public void test() {
        Assertions.assertThrows(ClientErrorException.class, () -> jokeService.getRandomJoke());
    }
}
