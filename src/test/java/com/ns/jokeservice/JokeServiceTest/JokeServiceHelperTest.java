package com.ns.jokeservice.JokeServiceTest;


import com.ns.jokeservice.model.Joke;
import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeServiceHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JokeServiceHelperTest {

    @Autowired
    private JokeServiceHelper jokeServiceHelper;
    @Test
    public void givenMultipleJokesThenReturnShortest() {

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
    public void givenSafeAndUnsafeJokeThenReturnTrueAndFalse() {

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
