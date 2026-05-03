package com.agro.SmartAgroMarket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro.SmartAgroMarket.Repository.AuctionRepo;
import com.agro.SmartAgroMarket.Repository.BuyerResponseRepo;
import com.agro.SmartAgroMarket.Repository.ConsumerRepo;
import com.agro.SmartAgroMarket.Repository.FarmerRepository;
import com.agro.SmartAgroMarket.Repository.NGORepo;
import com.agro.SmartAgroMarket.Repository.ProductsRepository;
import com.agro.SmartAgroMarket.dto.BuyRequestDTO;
import com.agro.SmartAgroMarket.dto.BuyResponseDTO;
import com.agro.SmartAgroMarket.dto.BuyerDTO;
import com.agro.SmartAgroMarket.dto.PurchasedProductDTO;
import com.agro.SmartAgroMarket.dto.SoldProductDTO;
import com.agro.SmartAgroMarket.models.Auctions;
import com.agro.SmartAgroMarket.models.Consumer;
import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.NGO;
import com.agro.SmartAgroMarket.models.Products;

@Service
public class ProductsService {
	
	private ProductsRepository productRepo;
	private AuctionRepo auctionRepo;
	private ConsumerRepo consumerRepo;
	private FarmerRepository farmerRepo;
	private BuyerResponseRepo buyerResponseRepo;
	private NGORepo ngoRepo;
	private NotificationService notificationService;
	
	public ProductsService(ProductsRepository productRepo, AuctionRepo auctionRepo, ConsumerRepo consumerRepo, FarmerRepository farmerRepo,BuyerResponseRepo buyerResponseRepo, NGORepo ngoRepo, NotificationService notificationService) {
		this.productRepo = productRepo;
		this.auctionRepo = auctionRepo;
		this.consumerRepo = consumerRepo;
		this.farmerRepo = farmerRepo;
		this.buyerResponseRepo = buyerResponseRepo;
		this.ngoRepo = ngoRepo;
		this.notificationService = notificationService;
	}
	
	private LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime todayAuctionStart = now.withHour(4).withMinute(30).withSecond(0);
    private final LocalDateTime todayAuctionEnd = now.withHour(6).withMinute(30).withSecond(0);
    private final LocalDateTime todayLastSale = now.withHour(14).withMinute(30).withSecond(0);

	
	public Products addProduct(Products product) {

		double suggested = product.getBasePrice() + (product.getBasePrice() * 0.10);
	    product.setSuggestedPrice(suggested);
	    product.setHighestPrice(product.getBasePrice());
	    product.setRemainingQuantity(product.getQuantity());
	    
	    LocalDateTime startTime = todayAuctionStart;
	    LocalDateTime endTime = todayAuctionEnd;
		LocalDateTime lastSale = todayLastSale;

	    
	    if (now.isBefore(lastSale)) {

	        if (now.isBefore(todayAuctionStart)) {
	            startTime = todayAuctionStart;
	            endTime = todayAuctionEnd;
				product.setStatus("NOT STARTED");
	        }
	        else if (now.isBefore(todayAuctionEnd)) {
	            startTime = todayAuctionStart;
	            endTime = todayAuctionEnd;
				product.setStatus("IN PROGRESS");
	        }
	        else if(now.isAfter(todayAuctionEnd)) {
	        	Double lastPrice = auctionRepo.findLastHighestPriceByProductName(product.getName());

	            if (lastPrice != null) {
	                product.setBasePrice(lastPrice);
	                product.setHighestPrice(lastPrice);
	            }

	            product.setStatus("AVAILABLE");
	            product.setAuctionStartTime(endTime);
	            product.setAuctionEndTime(lastSale);
	            product.setUpdatedTime(now);
	            
	            return productRepo.save(product);

	        }

	    } else {
	        // CASE 2: After 8 PM  Next day auction
	        startTime = todayAuctionStart.plusDays(1);
	        endTime = todayAuctionEnd.plusDays(1);
			lastSale = todayLastSale.plusDays(1);
			product.setStatus("NOT STARTED");
	    }
	    
	    product.setAuctionStartTime(startTime);
	    product.setAuctionEndTime(endTime);
	    product.setUpdatedTime(now);
	    

		
		return productRepo.save(product);
	}
	
	public List<Products> getMyProducts(Long farmerId){
		return productRepo.findByFarmerId(farmerId);
	}
	
	public List<Products> getAllProducts(){
		return productRepo.findAll();
	}
	
	public List<Products> searchProducts(String name){
		return productRepo.findByNameContainingIgnoreCase(name);
	}
	
	public void updateAuctiondetails() {
		List<Products> products = productRepo.findAll();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(Products p : products) {
			if(p.getStatus().equals("NOT STARTED") && p.getAuctionStartTime() != null &&
				    p.getAuctionEndTime() != null && now.isAfter(p.getAuctionStartTime()) && now.isBefore(p.getAuctionEndTime())) {
				p.setStatus("IN PROGRESS");
			}
			
			productRepo.save(p);
		}
	}
	public void closeAuction() {
        List<Products> products = productRepo.findAll();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(Products p:products) {
			if(p.getStatus().equals("IN PROGRESS")	&& p.getAuctionEndTime() != null && now.isAfter(p.getAuctionEndTime())){
				 p.setStatus("AVAILABLE");
				}
			productRepo.save(p);
			}
	}
	
	public BuyResponseDTO buyAvailableProduct(BuyRequestDTO request) {
		

	    if (request.getProductId() == null) {
	        throw new RuntimeException("Product Id is missing");
	    }
		
		Products product = productRepo.findById(request.getProductId()).orElse(null);
		
		Consumer buyer = consumerRepo.findById(request.getBuyerId()).orElse(null);
		
		Farmer farmer = farmerRepo.findById(request.getFarmerId()).orElse(null);
		
		int remaining = product.getRemainingQuantity()>0 ? product.getRemainingQuantity() : product.getQuantity();
		
		if(request.getQuantity() > remaining) {
			throw new RuntimeException("Insufficient Quantity");
		}
		
		BuyResponseDTO res = new BuyResponseDTO();
		
		res.setProductId(product.getId());
		res.setProductName(product.getName());
		res.setBuyerName(buyer.getName());
		res.setFarmerId(farmer.getId());
		res.setBuyerPhone(buyer.getPhone());
		res.setRemainingQuantity(remaining);
		res.setRequestedQuantity(request.getQuantity());
		res.setStatus("PENDING");
		
		notificationService.sendNotification(request.getFarmerId(), "FARMER", "New Buyer Request", buyer.getName() + " requested " + request.getQuantity() + " kg of "+ product.getName());
		return buyerResponseRepo.save(res);
		
		
	}
public BuyResponseDTO requestDonatedProduct(BuyRequestDTO request) {
	
	
	    if(request.getProductId() == null) {
	        throw new RuntimeException("Product Id is missing");
	    }
		
		Products product = productRepo.findById(request.getProductId()).orElse(null);
		
		NGO ngo = ngoRepo.findById(request.getBuyerId()).orElse(null);
		
		Farmer farmer = farmerRepo.findById(request.getFarmerId()).orElse(null);
		
		int remaining = product.getRemainingQuantity()>0 ? product.getRemainingQuantity() : product.getQuantity();
		
		if(request.getQuantity() > remaining) {
			throw new RuntimeException("Insufficient Quantity");
		}
		
		BuyResponseDTO res = new BuyResponseDTO();
		
		res.setProductId(product.getId());
		res.setProductName(product.getName());
		res.setBuyerName(ngo.getName());
		res.setFarmerId(farmer.getId());
		res.setBuyerPhone(ngo.getContact());
		res.setRemainingQuantity(remaining);
		res.setRequestedQuantity(request.getQuantity());
		res.setStatus("REQUEST PENDING");
		
		return buyerResponseRepo.save(res);
		
		
	}
	public void allocateProductBuyers() {
		List<Products> products = productRepo.findAll();
		
		for(Products p : products) {
			
			if((p.getStatus().equals("AVAILABLE") || p.getStatus().equals("PARTIALLY SOLD") )&&  p.getAuctionEndTime() != null && LocalDateTime.now().isAfter(p.getAuctionEndTime())) {
			
			List<Auctions> auctions = auctionRepo.findByProductIdOrderByAuctionPriceDesc(p.getId());
			
//			if(p.getStatus().equals("PARTIALLY SOLD") && now.isAfter(p.getAuctionEndTime())) {
//				
//				p.setStatus("UNSOLD");
//				p.setUpdatedTime(now);
//				
//				productRepo.save(p);
//				break;
//				
//			}
			   int remaining = p.getRemainingQuantity();
				
				for(Auctions auction : auctions) {
					
					if(remaining<=0 || auction.isAllocated()) {
						break;
					}
					if(auction.getQuantity()<=remaining) {
						auction.setAllocated(true);
						auction.setAllocatedQuantity(auction.getQuantity());
						remaining = remaining - auction.getQuantity();
						
					}
					else {
						auction.setAllocated(true);
						auction.setAllocatedQuantity(remaining);
						remaining = 0;
					}
					
					auctionRepo.save(auction);
					notificationService.sendNotification(auction.getBuyerId(), "BUYER", "Auction Details"," You got the " + auction.getQuantity() + " kg of "+ p.getName() + " at ₹"+auction.getAuctionPrice());
				}
				
				p.setRemainingQuantity(remaining);
				
				if(remaining == p.getQuantity()) {
					p.setStatus("UNSOLD");
					p.setUpdatedTime(now.plusDays(1));
				}
				else if(remaining>0) {
					p.setStatus("PARTIALLY SOLD");
					p.setAuctionStartTime(todayAuctionEnd);
					p.setAuctionEndTime(todayLastSale);
				}
				else {
					p.setStatus("SOLD");
					p.setUpdatedTime(now.plusDays(1));
				}
				
				productRepo.save(p);
				
			}
		}
	}
	public List<Products> farmerIdAndStatus(Long farmerId) {
	    return productRepo.findSoldProductsByFarmer(farmerId);
	    
	}
	
	public List<PurchasedProductDTO> getPurchasedProducts(Long buyerId) {

	    List<Auctions> auctions = auctionRepo.findByBuyerIdAndAllocatedTrue(buyerId);
	    
	    if(auctions == null) {
			throw new RuntimeException("NGO not found");
		}
	    

	    return auctions.stream().map(a -> {
	        Products p = productRepo.findById(a.getProductId()).orElse(null);
	        
	        if(p == null) {
	    		return null;
	    	}
	        
	        Farmer f = farmerRepo.findById(p.getFarmerId()).orElse(null);

	        PurchasedProductDTO dto = new PurchasedProductDTO();
	        dto.setProductId(p.getId());
	        dto.setProductName(p.getName());
	        dto.setAllocatedQuantity(a.getAllocatedQuantity());
	        dto.setPrice(a.getAuctionPrice());
	        
	        if(f !=null) {
		        dto.setFarmerPhone(f.getPhone());
		     }

	        return dto;
	    }).toList();
	}
	
	public List<SoldProductDTO> getFarmerSoldProducts(Long farmerId){
		
		List<Products> products = productRepo.findByFarmerId(farmerId);

	    List<SoldProductDTO> result = new ArrayList<>();

	    for (Products p : products) {

	        if (!p.getStatus().equals("SOLD") &&
	            !p.getStatus().equals("AVAILABLE") &&
	            !p.getStatus().equals("PARTIALLY SOLD") &&
	            !p.getStatus().equals("DONATED")) continue;
	        List<Auctions> auctions =
	            auctionRepo.findByProductIdAndAllocatedTrue(p.getId());

	        List<BuyerDTO> buyers = new ArrayList<>();

	        for (Auctions a : auctions) {

	            BuyerDTO b = new BuyerDTO();
	            b.setBuyerId(a.getBuyerId());
	            b.setPrice(a.getAuctionPrice());
	            b.setAllocatedQuantity(a.getAllocatedQuantity());
	            
	            Consumer consumer = consumerRepo.findById(a.getBuyerId()).orElse(null);
	            NGO ngo = ngoRepo.findById(a.getBuyerId()).orElse(null);
	            if(p.getStatus().equals("DONATED")) {
	            	
	            	if (ngo != null) {
		                b.setBuyerName(ngo.getName());
		                b.setBuyerPhone(ngo.getContact());
		            } else {
		                b.setBuyerName("Not Found");
		            }
	            	
	            }
	            else {
	            	if (consumer != null) {
		                b.setBuyerName(consumer.getName());
		                b.setBuyerPhone(consumer.getPhone());
		            } else {
		                b.setBuyerName("Not Found");
		            }
	            }
	            

	            buyers.add(b);
	        }

	        SoldProductDTO dto = new SoldProductDTO();
	        dto.setProductName(p.getName());
	        dto.setTotalQuantity(p.getQuantity());
	        dto.setStatus(p.getStatus());
	        dto.setRemainingQuantity(p.getRemainingQuantity());
	        dto.setBuyers(buyers);

	        result.add(dto);
	    }

	    return result;
	}
	
	public List<PurchasedProductDTO> getDonatedNGOProducts(Long ngoId) {
		
		NGO ngo = ngoRepo.findById(ngoId).orElse(null);
		
		if(ngo == null) {
			throw new RuntimeException("NGO not found");
		}
		
		String phone = ngo.getContact();

	    List<BuyResponseDTO> ngoBuy = buyerResponseRepo.findByBuyerPhoneAndStatus(phone, "DONATED");
	    

	    return ngoBuy.stream().map(a -> {
	    	
	    	Products p = productRepo.findById(a.getProductId()).orElse(null);
	    	
	    	
	    	if(p == null) {
	    		return null;
	    	}
	  
	        
	        Farmer f = farmerRepo.findById(p.getFarmerId()).orElse(null);

	        PurchasedProductDTO dto = new PurchasedProductDTO();
	        dto.setProductId(p.getId());
	        dto.setProductName(p.getName());
	        dto.setAllocatedQuantity(a.getRequestedQuantity());
	        dto.setPrice(p.getBasePrice());
	        
	        if(f !=null) {
	          dto.setFarmerPhone(f.getPhone());
	        }
	       

	        return dto;
	    }).filter(Objects::nonNull).toList();
	}
	
	public String sellNow(Long requestId) {
		
		BuyResponseDTO req = buyerResponseRepo.findById(requestId).orElseThrow();
		
		Products product = productRepo.findById(req.getProductId()).orElseThrow();
		
		Consumer buyer = consumerRepo.findByPhone(req.getBuyerPhone());
		
		int remaining = product.getRemainingQuantity();
		
		if(req.getRequestedQuantity() > remaining) {
			req.setStatus("REJECTED");
			buyerResponseRepo.save(req);
			
			return "Insufficient Stock";
		}
		
		remaining -= req.getRequestedQuantity();
		
		product.setRemainingQuantity(remaining);
		
		if(remaining == 0) {
			product.setStatus("SOLD");
		}
		else {
			product.setStatus(product.getStatus());
		}
		
		productRepo.save(product);
		
		req.setStatus("SOLD");
		buyerResponseRepo.save(req);
		
		Auctions auction = new Auctions();
		
		auction.setBuyerId(buyer.getId());
		auction.setAllocated(true);
		auction.setAllocatedQuantity(req.getRequestedQuantity());
		auction.setAuctionPrice(product.getHighestPrice());
		auction.setAuctionTime(now);
		auction.setBuyerPhone(buyer.getPhone());
		auction.setProductId(product.getId());
		auction.setQuantity(req.getRequestedQuantity());
		
		auctionRepo.save(auction);
		
		if(remaining == 0) {
			List<BuyResponseDTO> others = buyerResponseRepo.findByProductIdAndStatus(req.getProductId(),"PENDING");
			
			for(BuyResponseDTO r : others) {
				if(!r.getId().equals(requestId)) {
					r.setStatus("REJECTED");
					buyerResponseRepo.save(r);
				}
			}
		}
		
		notificationService.sendNotification(buyer.getId(), "BUYER", "Request Accepted", "Farmer accepted your requests for "+ product.getName());
		
		return "Product sold Successfully";
		
		
		
		
		
	}
	
public String donateNow(Long requestId) {
		
		BuyResponseDTO req = buyerResponseRepo.findById(requestId).orElseThrow();
		
		Products product = productRepo.findById(req.getProductId()).orElseThrow();
		
		NGO buyer = ngoRepo.findByContact(req.getBuyerPhone());
		
		int remaining = product.getRemainingQuantity();
		
		if(req.getRequestedQuantity() > remaining) {
			req.setStatus("REJECTED");
			buyerResponseRepo.save(req);
			
			return "Insufficient Stock";
		}
		
		remaining -= req.getRequestedQuantity();
		
		product.setRemainingQuantity(remaining);
		
		if(remaining == 0) {
			product.setStatus("SOLD");
		}
		else {
			product.setStatus(product.getStatus());
		}
		
		productRepo.save(product);
		
		req.setStatus("DONATED");
		buyerResponseRepo.save(req);
		
		Auctions auction = new Auctions();
		
		auction.setBuyerId(buyer.getId());
		auction.setAllocated(true);
		auction.setAllocatedQuantity(req.getRequestedQuantity());
		auction.setAuctionPrice(product.getHighestPrice());
		auction.setAuctionTime(now);
		auction.setBuyerPhone(buyer.getContact());
		auction.setProductId(product.getId());
		auction.setQuantity(req.getRequestedQuantity());
		
		auctionRepo.save(auction);
		
		if(remaining == 0) {
			List<BuyResponseDTO> others = buyerResponseRepo.findByProductIdAndStatus(req.getProductId(),"REQUEST PENDING");
			
			for(BuyResponseDTO r : others) {
				if(!r.getId().equals(requestId)) {
					r.setStatus("REJECTED");
					buyerResponseRepo.save(r);
				}
			}
		}
		notificationService.sendNotification(buyer.getId(), "BUYER", "Request Accepted", "Farmer accepted your requests for "+ product.getName());
		return "Product sold Successfully";
		
		
		
		
		
	}
	

    @Transactional
	public void clearPastProducts() {
		
		List<Products> products = productRepo.findByStatusIn(Arrays.asList("SOLD","UNSOLD","DONATED"));
		
		LocalDateTime now = LocalDateTime.now();
		
		for(Products p : products) {
			if(p.getUpdatedTime().plusDays(1).isBefore(now)) {
				
				
				List<Auctions> auctions = auctionRepo.findByProductId(p.getId());
				List<BuyResponseDTO> buyResponses = buyerResponseRepo.findByProductId(p.getId());
				
				if(auctions!=null) {
					auctionRepo.deleteByProductId(p.getId());
				}
				if(buyResponses!=null) {
					buyerResponseRepo.deleteByProductId(p.getId());
				}
				
				
				productRepo.delete(p);
			}
		}
	}
	
	public String sendToNgo(Long productId) {
		
		Products product = productRepo.findById(productId).orElse(null);
		
		if(product.getStatus().equals("UNSOLD") || product.getStatus().equals("PARTIALLY SOLD") || product.getStatus().equals("AVAILABLE")) {
			
		Products productDonated = new Products();
		productDonated.setAuctionEndTime(product.getAuctionEndTime());
		productDonated.setAuctionStartTime(product.getAuctionStartTime());
		productDonated.setBasePrice(product.getBasePrice());
		productDonated.setCategory(product.getCategory());
		productDonated.setFarmerId(product.getFarmerId());
		productDonated.setHighestPrice(product.getHighestPrice());
		productDonated.setName(product.getName());
		productDonated.setQuantity(product.getRemainingQuantity());
		productDonated.setRemainingQuantity(product.getRemainingQuantity());
		productDonated.setUpdatedTime(now);
		productDonated.setStatus("DONATED");
		productDonated.setSuggestedPrice(product.getSuggestedPrice());
		
		
		productRepo.save(productDonated);
		
		if(product.getStatus().equals("UNSOLD")) {
			product.setStatus("DONATED");
		}
		else {
			product.setStatus("SOLD");
		}
		
		List<NGO> ngos = ngoRepo.findAll();
		
		for(NGO n:ngos) {
			if(n!=null) {
			notificationService.sendNotification(n.getId(), "NGO", "New Donation Request", "New Product "+product.getName()+" uploaded");
			}
		}
		
		
		
		return "Message sent to the NGOs";
		
		}
		return "Products sale still available";
		
	}
	
	public String deleteProduct(Long id) {
		productRepo.deleteById(id);
		return "Deleted Successfully";
	}
	
	

}
