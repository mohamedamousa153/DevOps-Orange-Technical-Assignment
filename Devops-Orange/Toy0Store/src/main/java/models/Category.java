package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "categoryID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryID;

	@Column(name = "categoryName")
	private String categoryName;

	public Category() {
		categoryID = null;
		categoryName = "";
	}

	public Category(Integer id, String name) {
		super();
		this.categoryID = id;
		this.categoryName = name;
	}

	public Integer getId() {
		return categoryID;
	}

	public void setId(Integer id) {
		this.categoryID = id;
	}

	public String getName() {
		return categoryName;
	}

	public void setName(String name) {
		this.categoryName = name;
	}

	@Override
	public String toString() {
		return "Category [categoryID=" + categoryID + ", categoryName=" + categoryName + "]";
	}

}
