package com.agro.SmartAgroMarket.dto;

import com.agro.SmartAgroMarket.models.Farmer;
import com.agro.SmartAgroMarket.models.Products;

public class ProductWithFarmerDTO {
	    private Products product;
	    private Farmer farmer;
	    
	    public ProductWithFarmerDTO(Products product, Farmer farmer) {
	    	this.product = product;
	    	this.farmer = farmer;
	    }

		public Products getProduct() {
			return product;
		}

		public void setProduct(Products product) {
			this.product = product;
		}

		public Farmer getFarmer() {
			return farmer;
		}

		public void setFarmer(Farmer farmer) {
			this.farmer = farmer;
		}
	    
	    
}
