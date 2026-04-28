package com.agro.SmartAgroMarket.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agro.SmartAgroMarket.Repository.AuctionRepo;
import com.agro.SmartAgroMarket.Repository.NGORepo;
import com.agro.SmartAgroMarket.Repository.OrderRepo;
import com.agro.SmartAgroMarket.Repository.ProductsRepository;
import com.agro.SmartAgroMarket.models.Auctions;
import com.agro.SmartAgroMarket.models.NGO;
import com.agro.SmartAgroMarket.models.Orders;
import com.agro.SmartAgroMarket.models.Products;

@Service
public class AuctionService {
	
	private OrderRepo orderRepo;
	private AuctionRepo auctionRepo;
	private ProductsRepository productRepo;
	private NGORepo ngoRepo;
	
	public AuctionService(OrderRepo orderRepo,AuctionRepo auctionRepo,ProductsRepository productsRepo,NGORepo ngoRepo) {
		this.orderRepo = orderRepo;
		this.auctionRepo = auctionRepo;
		this.productRepo = productsRepo;
		this.ngoRepo = ngoRepo;
	}
	
	public String placeAuction(Auctions auction) {
		 Products product = productRepo.findById(auction.getProductId()).orElse(null);

	        if (product == null) {
	            return null;
	        }

	        
	        if (!product.getStatus().equals("IN PROGRESS")) {
	            return "Auction not active";
	        }
	        
	        if(product.getHighestPrice() >= auction.getAuctionPrice()) {
	        	return "Bid must be greater than current price";
	        }
	        
	        if(product.getHighestPrice() != product.getBasePrice() &&
	            auction.getAuctionPrice() <= product.getHighestPrice()) {
	            return "Bid must be higher than current price";
	        }
	      
	        if (auction.getQuantity() > product.getQuantity()) {
	            return "Not enough quantity available";
	        }

	        
	        auction.setAuctionTime(LocalDateTime.now());
	        auctionRepo.save(auction);

	        
	        product.setHighestPrice(auction.getAuctionPrice());
	        productRepo.save(product);

	        return "Bid placed successfully";
	    }
	public List<Auctions> getAuctionByProduct(Long productId){
		return auctionRepo.findByProductId(productId);
	}
	
	
	public void processAuction(Long productId) {
		Products product = productRepo.findById(productId).orElse(null);
		if(product == null) {
			return;
		}
		int remainingQty = product.getQuantity();
		
		List<Auctions> auctions = auctionRepo.findByProductId(productId);
		
		auctions.sort(Comparator.comparingDouble(Auctions::getAuctionPrice).reversed());
		
		for(Auctions auction : auctions) {
			if(remainingQty<=0) {
				break;
			}
			
			int allocatedQty = Math.min(auction.getQuantity(), remainingQty);
			
			Orders order = new Orders();
			order.setProductId(productId);
			order.setBuyerId(auction.getBuyerId());
			order.setQuantity(allocatedQty);
			order.setFinalPrice(auction.getAuctionPrice());
			
			orderRepo.save(order);
			
			remainingQty = remainingQty - allocatedQty;
		}
		
		if(remainingQty==0) {
			product.setStatus("SOLD");
		}
		else {
			product.setStatus("UNSOLD");
			
			notifyNGO(productId,remainingQty);
		}
		
		productRepo.save(product);

  }
	public void notifyNGO(Long productId,int quantity) {
		List<NGO> ngos = ngoRepo.findAll();
		
		for(NGO ngo:ngos) {
			
		}
	}
	
	public List<Auctions> getAllocatedBuyers(Long productId) {
	    return auctionRepo.findByProductIdAndAllocatedTrue(productId);
	}
}
