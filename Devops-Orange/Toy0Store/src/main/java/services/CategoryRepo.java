package services;

import org.springframework.data.repository.CrudRepository;

import models.Category;

public interface CategoryRepo extends CrudRepository<Category, Integer>{

}
