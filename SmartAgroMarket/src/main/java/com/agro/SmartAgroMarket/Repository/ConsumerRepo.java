package com.agro.SmartAgroMarket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.models.Consumer;

public interface ConsumerRepo extends JpaRepository<Consumer, Long>{
	
	Consumer findByPhone(String phone);

}
