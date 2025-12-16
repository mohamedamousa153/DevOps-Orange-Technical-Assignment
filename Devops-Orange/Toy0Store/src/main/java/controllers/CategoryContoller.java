package controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import models.Category;
import services.CategoryRepo;

@RestController
public class CategoryContoller {

	@Autowired
	private CategoryRepo categoryRepo;

	@RequestMapping("/categories")
	public ArrayList<Category> getAllCategories() {

		ArrayList<Category> all = new ArrayList<>();

		for (Category i : categoryRepo.findAll()) {
			all.add(i);
		}

		return all;
	}

	@RequestMapping("/categories/{id}")
	public Optional<Category> getCategory(@PathVariable String id) {

		return categoryRepo.findById(new Integer(id));

	}

	@RequestMapping(method = RequestMethod.POST, value = "/categories")
	public Category addCategory(@RequestBody Category category) {

		if (categoryRepo.findById(category.getId()) == null)
			return categoryRepo.save(category);
		else {
			category.setId(null);
			return categoryRepo.save(category);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/categories/{id}")
	public Category updateCategory(@RequestBody Category category, @PathVariable String id) {

		Optional<Category> c = categoryRepo.findById(new Integer(id));

		if (c != null && category != null) {

			c.get().setName(category.getName());

			return categoryRepo.save(c.get());
		}

		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/categories/{id}")
	public boolean DeleteCategory(@PathVariable String id) {

		categoryRepo.deleteById(new Integer(id));
		return true;
	}
}
