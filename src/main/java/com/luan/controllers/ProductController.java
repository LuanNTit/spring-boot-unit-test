package com.luan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luan.models.Product;
import com.luan.models.ResponseObject;
import com.luan.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Products")
public class ProductController {

	@Autowired
	private ProductRepository repository;

	@GetMapping("")
	public List<Product> getProducts() {
		return repository.findAll();
	}

	@PostMapping("/insert")
	// insert new Product with POST method
	// Postman : Raw, JSON
	ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
		// 2 products must not have the same name !
		List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
		if (foundProducts.size() > 0) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", "Product name already taken", ""));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Insert Product successfully", repository.save(newProduct)));
	}

	@GetMapping("/{id}")
	// Let's return an object with: data, message, status
	ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
		Optional<Product> foundProduct = repository.findById(id);
		return (foundProduct.isPresent())
				? ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("ok", "Query product successfully", foundProduct))
				: ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("failed", "Cannot find product with id = " + id, ""));
	}
	// update, upsert = update if found, otherwise insert
	@PutMapping("/{id}")
	ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
		Product updatedProduct = repository.findById(id)
			.map(product -> {
			product.setProductName(newProduct.getProductName());
			product.setYear(newProduct.getYear());
			product.setPrice(newProduct.getPrice());
			return repository.save(product);
		}).orElseGet(() -> {
			newProduct.setId(id);
			return repository.save(newProduct);
		});
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseObject("ok", "Update Product successfully", updatedProduct)
		);
	}
	
}
