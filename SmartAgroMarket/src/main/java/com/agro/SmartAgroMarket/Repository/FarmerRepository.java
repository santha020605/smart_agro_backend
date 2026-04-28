package com.agro.SmartAgroMarket.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.Products;

public interface FarmerRepository extends JpaRepository<Farmer, Long>{
	
	Farmer findByPhone(String phone);

	boolean existsByPhone(String phone);
}
