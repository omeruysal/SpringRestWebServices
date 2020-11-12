package com.example.demo.restcontroller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Movie;
import com.example.demo.repository.TemporaryDatabase;

@RestController
public class MovieRestController {
	
	@Autowired
	private TemporaryDatabase temp;
	
	@RequestMapping(method= RequestMethod.GET,value="/movies")
	public ResponseEntity<List<Movie>> getAll(){
		
		
		return ResponseEntity.ok(temp.findAll());
	}
	
	@RequestMapping(method= RequestMethod.GET,value="/movies/{id}")
	public ResponseEntity<Movie> getById(@PathVariable("id") Integer id){
		
		try {
			Movie mv = temp.findById(id);
			return ResponseEntity.ok(mv);
			
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
		
		
	@RequestMapping(method=RequestMethod.GET, value="/moviesbycategory")
	public ResponseEntity<List<Movie>> getMovies(@RequestParam("cat") String cat){
		try {
		List<Movie> movies = temp.findByCategory(cat);
		return ResponseEntity.ok(movies);
		}
		catch (Exception e) {
		return ResponseEntity.notFound().build();
				
		}
	}

	@RequestMapping(method=RequestMethod.DELETE,value="/movies/{id}")
	public ResponseEntity<URI> deleteMovies(@PathVariable("id") Integer id){
		temp.delete(id);
		URI uri = null;
		
		try {
			uri = new URI("http://localhost:8080/movies");
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/movies")
	public ResponseEntity<URI> createMovie(@RequestBody Movie m){
		try {
		temp.create(m);
		Integer id = m.getId();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/movies/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable("id") Integer id,@RequestBody Movie m){
		try {
			Movie m2 = temp.findById(id);
			m2.setName(m.getName());
			m2.setCategory(m.getCategory());
			temp.update(m2);
			System.out.println("m1 "+m);
			System.out.println("m2 "+m2);
			return ResponseEntity.ok().build();
		
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	

}
