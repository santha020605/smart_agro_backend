package com.agro.SmartAgroMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro.SmartAgroMarket.models.Products;
import com.agro.SmartAgroMarket.service.ProductsService;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductsController {
	
	@Autowired
	private ProductsService productService;
	
	@PostMapping("/addProduct")
	public Products addProduct(@RequestBody Products product) {
		return productService.addProduct(product);
	}
	
	@GetMapping("/allProducts")
	public List<Products> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/getMyProducts/{farmerId}")
	public List<Products> getMyProducts(@PathVariable Long farmerId){
		return productService.getMyProducts(farmerId);
	}
	
	@GetMapping("/search")
	public List<Products> searchProducts(@RequestParam String name){
		return productService.searchProducts(name);
	}
	
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}

}
