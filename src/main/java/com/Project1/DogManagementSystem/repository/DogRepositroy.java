package com.Project1.DogManagementSystem.repository;

//@author Vydehi Ramineni
import org.springframework.data.repository.CrudRepository;

import com.Project1.DogManagementSystem.Models.Dog;

public interface DogRepositroy extends CrudRepository<Dog, Integer>{
	

}
