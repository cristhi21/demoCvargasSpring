package com.cvargas.demo.rest;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvargas.demo.dao.PersonDao;
import com.cvargas.demo.entitys.Person;



@RestController
@RequestMapping("personas")
public class PersonaController {
	
	@Autowired
	private PersonDao personDAO;
	
	
	@GetMapping
	public ResponseEntity<List<Person>> getPerson(){
		List<Person> persons = personDAO.findAll();
		return ResponseEntity.ok(persons);

		/*Person person = new Person();
		person.setId(1L);
		person.setName("Cristhian");
		person.setLastName("Vargas");
		return ResponseEntity.ok(person);*/
		//return null;
	}
	
	@RequestMapping(value="{id}") 
	public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id){
		//Optional nos protege de un valor null
		Optional<Person> optionalPerson = personDAO.findById(id);
		if(optionalPerson.isPresent()) {
			return ResponseEntity.ok(optionalPerson.get());
		}else {
			return ResponseEntity.noContent().build();
		}
		//return ResponseEntity.ok(person);
		//return null;
	}
	
	/*@PostMapping
	public ResponseEntity<Person> createPerson(@RequestBody Person person){
		Person newPerson = personDAO.save(person);
		return ResponseEntity.ok(newPerson);
	}*/
	
	//@PostMapping("/tutorials")
	@PostMapping
	public ResponseEntity<Person> createPerson2(@RequestBody Person person) {
		try {
			System.out.println("Vamo aqui ------");
			System.out.println(person);
			
			Person _person = personDAO
					.save(person);
			return new ResponseEntity<>(_person, HttpStatus.CREATED);
			//return ResponseEntity.ok(_person);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="{id}") 
	public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id){
		personDAO.deleteById(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<Person> updatePerson(@RequestBody Person person){
		Optional<Person> optionalPerson = personDAO.findById(person.getId());
		if(optionalPerson.isPresent()) {
			Person updatePerson = optionalPerson.get();
			updatePerson.setName(person.getName());
			updatePerson.setLastName(person.getLastName());
			personDAO.save(updatePerson);
			return ResponseEntity.ok(updatePerson);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	//@GetMapping //localhost:8080
	//@RequestMapping(value="hello", method=RequestMethod.GET) //localhost:8080/hello
	public String hello() {
		return "cvargas cuero";
	}
}
