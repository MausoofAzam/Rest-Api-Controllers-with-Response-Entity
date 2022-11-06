package com.snort.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.snort.app.entity.Todos;

@Repository
public interface TodosRepository extends CrudRepository<Todos, Long> {

}
