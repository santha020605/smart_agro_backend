package com.agro.SmartAgroMarket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.SmartAgroMarket.Repository.ConsumerRepo;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.dto.ProductDTO;
import com.agro.SmartAgroMarket.dto.SoldProductDTO;
import com.agro.SmartAgroMarket.models.Consumer;
import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.Products;
import com.agro.SmartAgroMarket.service.FarmerService;
import com.agro.SmartAgroMarket.service.ProductsService;

@RestController
@RequestMapping("/farmer")
@CrossOrigin("*")
public class FarmerController {

	private FarmerService farmerService;
	private ProductsService productsService;
	private ConsumerRepo consumerRepo;
	
	public FarmerController(FarmerService farmerService, ProductsService productsService, ConsumerRepo consumerRepo) {
		
		this.farmerService = farmerService;
		this.productsService = productsService;
		this.consumerRepo = consumerRepo;
	}
	
	@PostMapping("/register")
	public Farmer registerFarmer(@RequestBody Farmer farmer) {
		return farmerService.registerFarmer(farmer);
		
	}
	
	@PostMapping("/login")
	public Farmer loginFarmer(@RequestBody Farmer farmer) {
		return farmerService.login(farmer.getPhone(), farmer.getPassword());
	}
	
	@GetMapping("/sold-products/{farmerId}")
	public List<SoldProductDTO> getFarmerSoldProducts(@PathVariable Long farmerId) {
		return productsService.getFarmerSoldProducts(farmerId);
	}
	
	@GetMapping("/buyerRequests/{farmerId}")
	public List<BuyResponseDTO> getBuyerRequests(@PathVariable Long farmerId){
		return farmerService.buyerRequests(farmerId);
	}
	
	@PostMapping("/sellNow/{requestedId}")
	public String sellNow(@PathVariable Long requestedId) {
		
		return productsService.sellNow(requestedId);
		
	}
	@PostMapping("/donateNow/{requestedId}")
	public String donateNow(@PathVariable Long requestedId) {
		
		return productsService.donateNow(requestedId);
		
	}
	
	@PostMapping("/sendNgo/{productId}")
	public String sendToNgo(@PathVariable Long productId) {
		
		return productsService.sendToNgo(productId);
		
	}

}
