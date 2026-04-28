package com.agro.SmartAgroMarket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.models.NGO;

public interface NGORepo extends JpaRepository<NGO, Long> {

	NGO findByName(String name);

	NGO findByNgoUID(String name);

	NGO findByContact(String phone);
	
	boolean existsByContact(String contact); 

}
