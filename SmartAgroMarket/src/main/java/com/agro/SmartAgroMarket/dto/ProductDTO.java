package com.agro.SmartAgroMarket.dto;

import com.agro.SmartAgroMarket.models.Consumer;
import com.agro.SmartAgroMarket.models.Products;

public class ProductDTO {
	
	 private Long productId;
	    private String productName;
	    private double basePrice;
	    private double highestPrice;
	    private String status;

	    // Buyer details
	    private Long buyerId;
	    private String buyerName;
	    private String buyerPhone;

	    public ProductDTO(Products product, Consumer buyer) {
	        this.productId = product.getId();
	        this.productName = product.getName();
	        this.basePrice = product.getBasePrice();
	        this.highestPrice = product.getHighestPrice();
	        this.status = product.getStatus();

	        if (buyer != null) {
	            this.buyerId = buyer.getId();
	            this.buyerName = buyer.getName();
	            this.buyerPhone = buyer.getPhone();
	        }
	        
	    }

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public double getBasePrice() {
			return basePrice;
		}

		public void setBasePrice(double basePrice) {
			this.basePrice = basePrice;
		}

		public double getHighestPrice() {
			return highestPrice;
		}

		public void setHighestPrice(double highestPrice) {
			this.highestPrice = highestPrice;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getBuyerId() {
			return buyerId;
		}

		public void setBuyerId(Long buyerId) {
			this.buyerId = buyerId;
		}

		public String getBuyerName() {
			return buyerName;
		}

		public void setBuyerName(String buyerName) {
			this.buyerName = buyerName;
		}

		public String getBuyerPhone() {
			return buyerPhone;
		}

		public void setBuyerPhone(String buyerPhone) {
			this.buyerPhone = buyerPhone;
		}
	    
	    

}
