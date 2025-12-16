package services;

import org.springframework.data.repository.CrudRepository;

import models.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {

}
