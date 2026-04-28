package com.agro.SmartAgroMarket.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.dto.BuyResponseDTO;

public interface BuyerResponseRepo extends JpaRepository<BuyResponseDTO, Long>{

	List<BuyResponseDTO> findByFarmerId(Long farmerId);

	List<BuyResponseDTO> findByProductIdAndStatus(Long productId, String string);

	List<BuyResponseDTO> findByBuyerPhone(String phone);

	List<BuyResponseDTO> findByBuyerPhoneAndStatus(String phone, String string);

	List<BuyResponseDTO> findByProductId(Long id);

	void deleteByProductId(Long id);

}
