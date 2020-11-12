package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Movie;

public interface MovieRepository {
	List<Movie> findAll();
	Movie findById(Integer id);
	List<Movie> findByCategory(String category);
	void create(Movie m);
	Movie update(Movie m);
	void delete(Integer id);

}
