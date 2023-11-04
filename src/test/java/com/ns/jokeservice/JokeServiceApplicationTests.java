package com.ns.jokeservice;

import com.ns.jokeservice.exception.ApiErrorException;
import com.ns.jokeservice.model.ErrorResponse;
import com.ns.jokeservice.model.JokeReply;
import com.ns.jokeservice.service.JokeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JokeServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JokeService jokeService;

	@Test()
	void contextLoads() {
	}

	@Test
	public void getShouldReturnOneJoke() {
		JokeReply response = restTemplate.getForObject("http://localhost:" + port + "/api/random-joke/", JokeReply.class);
		assertThat(response.getRandomJoke(), is(notNullValue()));
	}

	@Test
	public void givenWrongRequest_whenCallingExternalApi_thenThrowApiErrorException() {
		List<String> pathVariables = Arrays.asList("Dark","Pun","WrongCategory");
		Map<String, String> queryParams = Map.ofEntries(Map.entry("amount", "10"));

		Assertions.assertThrows(ApiErrorException.class, () -> jokeService.getRandomJokeGivenParameters(pathVariables, queryParams));
	}

	@Test
	public void givenValidRequest_whenCallingApi_thenReturnJoke() {
		String pathVariables = "Dark,Pun,Spooky";
		String queryParams = "amount=10";

		JokeReply jokeReply = restTemplate.getForObject(String.format("http://localhost:%d/api/specify-joke/%s?%s", port, pathVariables, queryParams), JokeReply.class);

		assertThat(jokeReply.getId(), not(0));
	}

	@Test
	public void givenWrongRequest_whenCallingApi_callExceptionHandler() {
		String pathVariables = "Dark,Pun,WrongCategory";
		String queryParams = "amount=10";

		ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(String.format("http://localhost:%d/api/specify-joke/%s?%s", port, pathVariables, queryParams), ErrorResponse.class);

		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		assertThat(Objects.requireNonNull(response.getBody()).isError(), is(true));
	}

}
