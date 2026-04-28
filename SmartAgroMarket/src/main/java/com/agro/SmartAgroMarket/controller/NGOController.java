package com.agro.SmartAgroMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.SmartAgroMarket.Repository.NGORepo;
import com.agro.SmartAgroMarket.Repository.NGORequestsRepo;
import com.agro.SmartAgroMarket.Repository.ProductsRepository;
import com.agro.SmartAgroMarket.dto.BuyRequestDTO;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.dto.PurchasedProductDTO;
import com.agro.SmartAgroMarket.models.NGO;
import com.agro.SmartAgroMarket.models.NGORequests;
import com.agro.SmartAgroMarket.models.Products;
import com.agro.SmartAgroMarket.service.NGOService;
import com.agro.SmartAgroMarket.service.ProductsService;

@RestController
@RequestMapping("/ngo")
@CrossOrigin("*")
public class NGOController {
	
	private NGOService ngoService;
	private NGORequestsRepo requestRepo;
	private ProductsRepository productsRepo;
	private ProductsService productsService;
	
	public NGOController(NGOService ngoService, NGORequestsRepo requestRepo, ProductsRepository productsRepo, ProductsService productsService) {
		this.ngoService = ngoService;
		this.productsRepo = productsRepo;
		this.requestRepo = requestRepo;
		this.productsService = productsService;
	}
	
	@PostMapping("/register")
	public NGO register(@RequestBody NGO ngo) {
		return ngoService.register(ngo);
	}
	
	@PostMapping("/login")
	public NGO login(@RequestBody NGO ngo) {
		return ngoService.login(ngo.getName(), ngo.getContact());
	}
	
	public NGORequests requestsProduct(@RequestBody NGORequests req) {
		req.setStatus("PENDING");
		return requestRepo.save(req);
	}
	
	@GetMapping("/getProducts")
	public List<Products> getDonatedProducts(){
		return productsRepo.findDonatedProducts();
	}
	
	@PostMapping("/requestProduct")
	public BuyResponseDTO requestDonatedProduct(@RequestBody BuyRequestDTO request) {
		
		return ngoService.requestProduct(request);
		
		
	}
	
	@PostMapping("/getDonated/{ngoId}")
	public List<PurchasedProductDTO> getNGOPurchasedProducts(@PathVariable Long ngoId ){
		return productsService.getDonatedNGOProducts(ngoId);
		
	}
	

}
