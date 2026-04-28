package com.agro.SmartAgroMarket.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agro.SmartAgroMarket.models.Auctions;

public interface AuctionRepo extends JpaRepository<Auctions, Long>{
	List<Auctions> findByProductId(Long productId);

	List<Auctions> findByProductIdOrderByAuctionPriceDesc(Long id);
	
	@Query("SELECT MAX(a.auctionPrice) FROM Auctions a WHERE a.productId IN (SELECT p.id FROM Products p WHERE p.name = :name)")
	Double findLastHighestPriceByProductName(String name);

	List<Auctions> findByProductIdAndAllocatedTrue(Long id);
	
	List<Auctions> findByBuyerIdAndAllocatedTrue(Long buyerId);

	@Modifying
	void deleteByProductId(Long id);


}
