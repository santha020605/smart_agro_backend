package com.agro.SmartAgroMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro.SmartAgroMarket.Repository.BuyerResponseRepo;
import com.agro.SmartAgroMarket.Repository.NGORepo;
import com.agro.SmartAgroMarket.dto.BuyRequestDTO;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.NGO;

@Service
public class NGOService {
	
	@Autowired
	private NGORepo ngoRepo;
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	
	public NGO register(NGO ngo) {
		
		if(ngoRepo.existsByContact(ngo.getContact())) {
			return null;
		}
		
		return ngoRepo.save(ngo);
		
		
	}
	public NGO login(String name, String phone) {

		NGO ngo = ngoRepo.findByContact(phone);
		if (ngo == null || !ngo.getName().equals(name)) {
			return null;
		}

		return ngo;

	}
	
	public BuyResponseDTO requestProduct(BuyRequestDTO request) {
		
		
		notificationService.sendNotification(request.getFarmerId(), "FARMER", "NGO Request","Request one Product");
		return productsService.requestDonatedProduct(request);
		
		
	}

}
