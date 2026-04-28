package com.agro.SmartAgroMarket.models;

import java.time.LocalDateTime;
import java.util.List;

import com.agro.SmartAgroMarket.dto.BuyerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String category;
	
	private int quantity;
	
	private int remainingQuantity;
	
	private double basePrice;
	
	private double suggestedPrice;
	
	private double highestPrice;
	
	private LocalDateTime auctionStartTime;
	private LocalDateTime auctionEndTime;
	
	private LocalDateTime updatedTime;
	
	private String status;
	
	private Long farmerId;
	

	
	
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getSuggestedPrice() {
		return suggestedPrice;
	}

	public void setSuggestedPrice(double suggestedPrice) {
		this.suggestedPrice = suggestedPrice;
	}

	public LocalDateTime getAuctionStartTime() {
		return auctionStartTime;
	}

	public void setAuctionStartTime(LocalDateTime auctionStartTime) {
		this.auctionStartTime = auctionStartTime;
	}

	public LocalDateTime getAuctionEndTime() {
		return auctionEndTime;
	}

	public void setAuctionEndTime(LocalDateTime auctionEndTime) {
		this.auctionEndTime = auctionEndTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Long farmerId) {
		this.farmerId = farmerId;
	}


}
