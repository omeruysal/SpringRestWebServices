package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Movie;
@Repository
public class TemporaryDatabase implements MovieRepository {
	private Map<Integer, Movie> moviesMap = new HashMap<>();
	public TemporaryDatabase() {
		Movie m1 = new Movie();
		m1.setId(1);
		m1.setName("Lord Of The Rings");
		m1.setTime("230");
		m1.setCategory("Action");
		m1.setPublishDate(new Date(1999, 12, 02));
		
		Movie m2 = new Movie();
		m2.setId(2);
		m2.setName("Batman");
		m2.setTime("120");
		m2.setCategory("Action");
		m2.setPublishDate(new Date(1978, 10, 12));
		
		Movie m3 = new Movie();
		m3.setId(3);
		m3.setName("Rocky");
		m3.setTime("116");
		m3.setCategory("Action");
		m3.setPublishDate(new Date(1981, 9, 01));
		
		Movie m4 = new Movie();
		m4.setId(4);
		m4.setName("Fight Clup");
		m4.setTime("90");
		m4.setCategory("Action");
		m4.setPublishDate(new Date(2000, 12, 02));
		
		Movie m5 = new Movie();
		m5.setId(5);
		m5.setName("The Big Short");
		m5.setTime("115");
		m5.setCategory("Dram");
		m5.setPublishDate(new Date(2007, 10, 20));
		moviesMap.put(m1.getId(), m1);
		moviesMap.put(m2.getId(), m2);
		moviesMap.put(m3.getId(), m3);
		moviesMap.put(m4.getId(), m4);
		moviesMap.put(m5.getId(), m5);
	}

	@Override
	public List<Movie> findAll() {
		return new ArrayList<Movie>(moviesMap.values());
	}

	@Override
	public Movie findById(Integer id) {
		
		return moviesMap.get(id);
	}

	@Override
	public List<Movie> findByCategory(String category) {
		return moviesMap.values().stream().filter(m->m.getCategory().equals(category)).collect(Collectors.toList());
	}

	@Override
	public void create(Movie m) {
		moviesMap.put(m.getId(), m);
	}

	@Override
	public Movie update(Movie m) {
	moviesMap.replace(m.getId(), m);
		return m;
	}

	@Override
	public void delete(Integer id) {
		moviesMap.remove(id);
		
	}

}
