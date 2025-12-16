package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "productID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productID;

	@Column(name = "productName")
	private String productName;

	@Column(name = "productDescription")
	private String productDescription;

	@Column(name = "productPrice")
	private double productPrice;

	@Column(name = "categoryID")
	private Integer categoryID;

	public Product() {
		this.productID = null;
		this.productName = "";
		this.productDescription = "";
		this.productPrice = 0.0;
		this.categoryID = null;

	}

	public Product(Integer id, String name, String description, double price, Integer categoryID) {
		super();
		this.productID = id;
		this.productName = name;
		this.productDescription = description;
		this.productPrice = price;
		this.categoryID = categoryID;
	}

	public Integer getId() {
		return productID;
	}

	public void setId(Integer id) {
		this.productID = id;
	}

	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
	}

	public String getDescription() {
		return productDescription;
	}

	public void setDescription(String description) {
		this.productDescription = description;
	}

	public double getPrice() {
		return productPrice;
	}

	public void setPrice(double price) {
		this.productPrice = price;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	@Override
	public String toString() {
		return "Product [id=" + productID + ", name=" + productName + ", description=" + productDescription + ", price="
				+ productPrice + ", categoryID=" + categoryID + "]";
	}
}
