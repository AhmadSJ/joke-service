package com.ns.jokeservice.JokeServiceTest;


import com.ns.jokeservice.model.Joke;
import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeServiceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class JokeServiceHelperTest {

    @Autowired
    private JokeServiceHelper jokeServiceHelper;
    @Test
    public void givenMultipleJokes_whenFilteringShortest_ThenReturnShortest() {

        //Given
        List<Joke> safeJokes = new ArrayList<>();
        Joke joke1 = Joke.builder()
                .joke("Shortest joke")
                .id(1)
                .safe(true)
                .build();
        Joke joke2 = Joke.builder()
                .joke("This is the longest joke")
                .id(2)
                .safe(true)
                .build();

        safeJokes.add(joke1);
        safeJokes.add(joke2);

        //When
        JokeReply result = jokeServiceHelper.filterShortestJoke(safeJokes);

        //then
        assertThat(result.getId(), equalTo(1));
        assertThat(result.getRandomJoke(), equalTo("Shortest joke"));
    }

    @Test
    public void givenSafeAndUnsafeJoke_whenCheckingIfSafe_ThenReturnTrueAndFalse() {

        Joke safeJoke = Joke.builder()
                .joke("Safe joke")
                .id(1)
                .safe(true)
                .flagsWithBooleans(false, false)
                .build();
        Joke nonSafeJoke = Joke.builder()
                .joke("Explicit joke")
                .id(2)
                .safe(true)
                .flagsWithBooleans(true, true)
                .build();

        assertThat(jokeServiceHelper.isJokeSafe(safeJoke), equalTo(true));
        assertThat(jokeServiceHelper.isJokeSafe(nonSafeJoke), equalTo(false));

    }
}
