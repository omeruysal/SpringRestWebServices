package com.example.demo.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Movie;

public class MovieRestControllerTests {

	private RestTemplate restTemplate;

	@Before
	public void setUp() { // setup methodlari her test calismadan once calisir.
		restTemplate = new RestTemplate();

	}

	@Test
	public void testCreateMovie() {
		Movie m1 = new Movie();
		m1.setCategory("Dram");
		m1.setId(10);
		m1.setName("It");
		m1.setTime("100");
		m1.setPublishDate(new Date(1981, 9, 01));
		URI location = restTemplate.postForLocation("http://localhost:8080/movies", m1);
		System.out.println(location);

		Movie m2 = restTemplate.getForObject(location, Movie.class);
		MatcherAssert.assertThat(m2.getName(), Matchers.equalTo(m1.getName()));
	}

	@Test
	public void testUpdateMovie() {
		Movie m = restTemplate.getForObject("http://localhost:8080/movies/1", Movie.class);
		MatcherAssert.assertThat(m.getName(), Matchers.equalTo("Lord Of The Rings"));
		m.setName("Ghost Rider");
		restTemplate.put("http://localhost:8080/movies/1", m);
		m = restTemplate.getForObject("http://localhost:8080/movies/1", Movie.class);
		MatcherAssert.assertThat(m.getName(), Matchers.equalTo("Ghost Rider"));

	}

	@Test
	public void testGetMovieById() {
		ResponseEntity<Movie> response = restTemplate.getForEntity("http://localhost:8080/movies/1", Movie.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getName(), Matchers.equalTo("Lord Of The Rings"));

	}

	@Test
	public void testGetMoviesByCategory() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/moviesbycategory?cat=Action",
				List.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();

		List<String> movieNames = body.stream().map(e -> e.get("name")).collect(Collectors.toList());

		MatcherAssert.assertThat(movieNames,
				Matchers.containsInAnyOrder("Lord Of The Rings", "Batman", "Rocky", "Fight Clup"));

	}

	@Test
	public void testGetAllMovies() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/movies", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();
		List<String> movieNames = body.stream().map(e -> e.get("name")).collect(Collectors.toList());

		MatcherAssert.assertThat(movieNames,
				Matchers.containsInAnyOrder("Lord Of The Rings", "Batman", "Rocky", "Fight Clup", "The Big Short"));

	}

}
