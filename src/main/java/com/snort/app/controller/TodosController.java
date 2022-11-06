package com.snort.app.controller;

import java.util.Date;
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

import com.snort.app.entity.Todos;
import com.snort.app.repository.TodosRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("todos")
public class TodosController {

	@Autowired
	TodosRepository todosRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createTask(@RequestBody Todos todos) {
		log.info("Todos Rest API : createTask() executed");

		todos.setAssignedDate(new Date());
		Todos newTodos = todosRepository.save(todos);

		if (newTodos != null) {
			return new ResponseEntity(newTodos, HttpStatus.CREATED);
		} else {
			return new ResponseEntity("Failed To create", HttpStatus.BAD_REQUEST);
		}

	}

//	@PostMapping("/create")
//	public ResponseEntity<?> createTask(@RequestBody Todos todos) {
//		log.info("Todos Rest API : createTask() executed");
//
//		todos.setAssignedDate(new Date());
//		if (todos != null) {
//			Todos newtodos = todosRepository.save(todos);
//			if (newtodos.getTaskId() != null) {
//				return new ResponseEntity(newtodos, HttpStatus.CREATED);
//			}
//		} else {
//			return new ResponseEntity("Failed to create", HttpStatus.BAD_REQUEST);
//
//		}
//		return null;
//	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		log.info("Todos Rest API : findAll() executed");
		List<Todos> list = (List<Todos>) todosRepository.findAll();

		if (list != null) {
			return new ResponseEntity(list, HttpStatus.OK);
		} else {
			return new ResponseEntity("Resource is empty", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findOneTodos(@PathVariable Long id) {
		log.info("Todos Rest API : findOneTodos() executed");
		

		if (todosRepository.existsById(id)) {
			Optional<Todos> todos = todosRepository.findById(id);
			return new ResponseEntity(todos.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity("Id Not Found", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOneTodos(@PathVariable Long id){
		log.info("Todos Rest API : findAll() executed");
		
		
		try {
			todosRepository.deleteById(id);
			return new ResponseEntity("Todos with id->"+id+"Successfully Deleted",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Given Id not found",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatetodos(@PathVariable Long id, @RequestBody Todos newtodos){
		log.info("Todos Rest API : updatetodos() executed");
		newtodos.setTaskId(id);
		newtodos.setAssignedDate(new Date());
		if(todosRepository.existsById(id)) {
			Todos todos =todosRepository.save(newtodos);
			//return new ResponseEntity(todosRepository.save(newtodos),HttpStatus.OK);
			return new ResponseEntity(todos,HttpStatus.OK);
		}else {
			return new ResponseEntity("failed to update ",HttpStatus.NOT_FOUND);
		}
	}
}
