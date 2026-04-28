package com.agro.SmartAgroMarket.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Auctions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long productId;
	private Long buyerId;
	
	private String buyerPhone;
	
	

	private double auctionPrice;
	private int quantity;
	
	private int allocatedQuantity;
	

	private boolean allocated;
	
	private LocalDateTime auctionTime;

	
	public boolean isAllocated() {
		return allocated;
	}

	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}
	
	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public int getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(int allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(double auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public LocalDateTime getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(LocalDateTime auctionTime) {
		this.auctionTime = auctionTime;
	}
	
}
