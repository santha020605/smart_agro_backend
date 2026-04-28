package com.agro.SmartAgroMarket.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agro.SmartAgroMarket.service.ProductsService;

@Component
public class AuctionScheduler {
	
	@Autowired
	private ProductsService productService;
	
	@Scheduled(fixedRate = 60000)
	public void autoUpdateStatus() {
		productService.updateAuctiondetails();
		productService.closeAuction();
		productService.allocateProductBuyers();
		productService.clearPastProducts();
	}
	
	
}
