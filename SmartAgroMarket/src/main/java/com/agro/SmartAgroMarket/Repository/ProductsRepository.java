package com.agro.SmartAgroMarket.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agro.SmartAgroMarket.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{
	
	List<Products> findByFarmerId(Long id);
	
	List<Products> findByNameContainingIgnoreCase(String name);
	
	List<Products> findByName(String name);
	
	@Query("SELECT p FROM Products p WHERE p.farmerId = :farmerId AND p.status = 'SOLD'")
	List<Products> findSoldProductsByFarmer(@Param("farmerId") Long farmerId);
	
	@Query("SELECT p FROM Products p WHERE p.farmerId = :farmerId AND p.status = 'SOLD' OR p.status = 'UNSOLD'")
	List<Products> findSalesOfProductsByFarmer(@Param("farmerId") Long farmerId);
	
	@Query("SELECT p FROM Products p WHERE p.status = 'IN_PROGRESS'")
	List<Products> findLiveProducts();
	
	@Query("SELECT p FROM Products p WHERE p.status = 'DONATED'")
	List<Products> findDonatedProducts();

	List<Products> findByStatus(String string);
	
	List<Products> findByStatusIn(List<String> status);
	


}
