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

import com.agro.SmartAgroMarket.models.Auctions;
import com.agro.SmartAgroMarket.service.AuctionService;

@RestController
@RequestMapping("/auctions")
@CrossOrigin
public class AuctionsController {
	
	@Autowired
	private AuctionService auctionService;
	
	@PostMapping("/place")
	public String placeAuction(@RequestBody Auctions auction) {
		return auctionService.placeAuction(auction);
	}
	
	@GetMapping("/product/{productId}")
	public List<Auctions> getAuctions(@PathVariable Long productId){
		return auctionService.getAuctionByProduct(productId);
	}
	
	@PostMapping("/process/{productId}")
	public String processAuction(@PathVariable Long productId) {
		auctionService.processAuction(productId);
		return "Auction processed successfully";
	}
	

}
