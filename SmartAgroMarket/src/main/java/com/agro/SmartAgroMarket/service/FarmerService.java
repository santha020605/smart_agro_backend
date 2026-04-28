package com.agro.SmartAgroMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agro.SmartAgroMarket.Repository.BuyerResponseRepo;
import com.agro.SmartAgroMarket.Repository.FarmerRepository;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.models.Farmer;

@Service
public class FarmerService {
	
	
	@Autowired
	private FarmerRepository farmerRepo;
	
	@Autowired
	private BuyerResponseRepo buyerResponseRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Farmer registerFarmer(Farmer farmer) {
		
		if(farmerRepo.existsByPhone(farmer.getPhone())) {
			return null;
		}
		farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
		return farmerRepo.save(farmer);
	}
	
	public Farmer login(String phone,String password) {
		
		Farmer farmer = farmerRepo.findByPhone(phone);
		
		if(farmer==null || !passwordEncoder.matches(password, farmer.getPassword())) {
			return null;
		}
		
		return farmer;
		
	}
	
	public List<BuyResponseDTO> buyerRequests(Long farmerId){
		return buyerResponseRepo.findByFarmerId(farmerId);
	}

}
