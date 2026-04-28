package com.agro.SmartAgroMarket.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.SmartAgroMarket.models.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long>{
	
	List<Notification> findByUserIdAndPortalTypeOrderByCreatedAtDesc(Long userId, String portalType);

	List<Notification> findByCreatedAtBefore(LocalDateTime time);

}
