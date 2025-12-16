package controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.Product;
import services.ProductRepo;;

@RestController
public class ProductController {

	@Autowired
	private ProductRepo productRepo;

	@RequestMapping("/products")
	public ArrayList<Product> getAllProducts() {

		ArrayList<Product> all = new ArrayList<>();

		for (Product i : productRepo.findAll()) {
			all.add(i);
		}

		return all;

	}

	@RequestMapping("/products/{id}")
	public Optional<Product> getProduct(@PathVariable String id) {

		return productRepo.findById(new Integer(id));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/products")
	public Product addProudct(@RequestBody Product product) {

		if (productRepo.findById(product.getId()) == null)
			return productRepo.save(product);
		else {
			product.setId(null);
			return productRepo.save(product);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
	public boolean DeleteProudct(@PathVariable String id) {

		productRepo.deleteById(new Integer(id));

		return true;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
	public Product updateProudct(@RequestBody Product product, @PathVariable String id) {

		Optional<Product> p = productRepo.findById(new Integer(id));

		if (p != null && product != null) {

			product.setId(new Integer(id));

			return productRepo.save(product);
		}

		return null;

	}

	@RequestMapping("/categories/{id}/products")
	public ArrayList<Product> listProductsByCategory(@PathVariable String id) {

		ArrayList<Product> productCategory = new ArrayList<>();

		for (Product i : productRepo.findAll()) {

			if (i.getCategoryID().equals(id)) {

				productCategory.add(i);

			}
		}
		return productCategory;
	}

}
