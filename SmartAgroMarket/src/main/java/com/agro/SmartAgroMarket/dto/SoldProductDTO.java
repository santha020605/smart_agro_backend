package com.agro.SmartAgroMarket.dto;

import java.util.List;

public class SoldProductDTO {
	
	private String productName;
	private int totalQuantity;
	private int remainingQuantity;
	private String status;
	private List<BuyerDTO> buyers;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productNaame) {
		this.productName = productNaame;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public int getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<BuyerDTO> getBuyers() {
		return buyers;
	}
	public void setBuyers(List<BuyerDTO> buyers) {
		this.buyers = buyers;
	}
	
	
	

}
