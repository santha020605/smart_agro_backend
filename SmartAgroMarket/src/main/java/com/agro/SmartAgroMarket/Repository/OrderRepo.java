package com.agro.SmartAgroMarket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.models.Orders;

public interface OrderRepo extends JpaRepository<Orders, Long>{

}
