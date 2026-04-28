package com.agro.SmartAgroMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.SmartAgroMarket.Repository.FarmerRepository;
import com.agro.SmartAgroMarket.Repository.ProductsRepository;
import com.agro.SmartAgroMarket.dto.BuyRequestDTO;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.dto.ProductWithFarmerDTO;
import com.agro.SmartAgroMarket.dto.PurchasedProductDTO;
import com.agro.SmartAgroMarket.models.Consumer;
import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.Products;
import com.agro.SmartAgroMarket.service.ConsumerService;
import com.agro.SmartAgroMarket.service.ProductsService;

@RestController
@RequestMapping("/consumer")
@CrossOrigin
public class ConsumerController {
	
	@Autowired
	private ConsumerService consumerService;
	private ProductsService productsService;
	private FarmerRepository farmerRepo;
	
	public ConsumerController(ConsumerService consumerService, ProductsService productsService, FarmerRepository farmerRepo) {
		this.consumerService = consumerService;
		this.productsService = productsService;
		this.farmerRepo = farmerRepo;
	}
	
	
	@PostMapping("/register")
	public Consumer register(@RequestBody Consumer consumer) {
		return consumerService.registerConsumer(consumer);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Consumer consumer) {
		Consumer c = consumerService.login(consumer.getPhone(), consumer.getPassword());
		
		if(c == null) {
			return ResponseEntity.badRequest().body("Invalid credentials");
		}
		return ResponseEntity.ok(c);
	}
	
	@GetMapping("/purchased/{buyerId}")
	public List<PurchasedProductDTO> getPurchasedProducts(@PathVariable Long buyerId) {
	    return productsService.getPurchasedProducts(buyerId);
	}
	
	@PostMapping("/buyNow")
	public BuyResponseDTO buyNow(@RequestBody BuyRequestDTO request) {
		return productsService.buyAvailableProduct(request);
	}
	
//	@GetMapping("/products-with-farmer")
//	public List<ProductWithFarmerDTO> getProductsWithFarmer() {
//	    List<Products> products = productsRepo.findByStatus("IN PROGRESS");
//
//	    return products.stream().map(p -> {
//	        Farmer farmer = farmerRepo.findById(p.getFarmerId()).orElse(null);
//	        return new ProductWithFarmerDTO(p, farmer);
//	    }).toList();
//	}
}
