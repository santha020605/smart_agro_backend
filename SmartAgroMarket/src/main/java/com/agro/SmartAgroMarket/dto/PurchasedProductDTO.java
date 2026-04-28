package com.agro.SmartAgroMarket.dto;

public class PurchasedProductDTO {
	
	    private Long productId;
	    private String productName;
	    private int allocatedQuantity;
	    private double price;
	    private String farmerPhone;
	    
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
		public int getAllocatedQuantity() {
			return allocatedQuantity;
		}
		public void setAllocatedQuantity(int allocatedQuantity) {
			this.allocatedQuantity = allocatedQuantity;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getFarmerPhone() {
			return farmerPhone;
		}
		public void setFarmerPhone(String farmerPhone) {
			this.farmerPhone = farmerPhone;
		}

}
