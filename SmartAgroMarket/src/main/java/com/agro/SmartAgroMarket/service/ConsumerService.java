package com.agro.SmartAgroMarket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agro.SmartAgroMarket.Repository.ConsumerRepo;
import com.agro.SmartAgroMarket.models.Consumer;

@Service
public class ConsumerService {
	
	@Autowired
	private ConsumerRepo consumerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	public Consumer registerConsumer(Consumer consumer) {
		consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
		return consumerRepo.save(consumer);
	}
	
    public Consumer login(String phone,String password) {
		
		Consumer consumer = consumerRepo.findByPhone(phone);
		
		
		if(consumer == null || !passwordEncoder.matches(password, consumer.getPassword())) {
			return null;
		}
		
		return consumer;
		
	}
    
    public Optional<Consumer> findById(Long consumerId) {
    	
    	return consumerRepo.findById(consumerId);
    	
    }

}
