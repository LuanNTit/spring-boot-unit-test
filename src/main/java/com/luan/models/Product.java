package com.luan.models;

import java.util.Calendar;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// POJO = Plain Object Java Object
@Entity
@Table(name="tblProduct")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Product {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)// auto-increment
	// you can also use "sequence"
	@SequenceGenerator(
		name = "product_sequence",
		sequenceName = "product_sequence",
		allocationSize = 1 //increment by 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "product_sequence"
	)
    private Long id;
	// validate = constraint
	@Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;
	// default constructor
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	// calculated field = transient, not exist in mysql
	@Transient
	private int age;// age is calculated from "year"
	public int getAge() {
		return Calendar.getInstance().get(Calendar.YEAR) - year;
	}
	public Product(String productName, int year, Double price, String url) {
		super();
		this.productName = productName;
		this.year = year;
		this.price = price;
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", year=" + year + ", price=" + price + ", url="
				+ url + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(age, id, price, productName, url, year);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return age == other.age && Objects.equals(id, other.id) && Objects.equals(price, other.price)
				&& Objects.equals(productName, other.productName) && Objects.equals(url, other.url)
				&& year == other.year;
	}
    
}

