package com.agro.SmartAgroMarket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro.SmartAgroMarket.Repository.NotificationRepo;
import com.agro.SmartAgroMarket.models.Notification;

@Service
public class NotificationService {
	
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	public void sendNotification(Long userId, String portalType, String title, String message) {
		Notification n = new Notification();
		
		n.setUserId(userId);
		n.setPortalType(portalType);
		n.setTitle(title);
		n.setMessage(message);
		n.setSeen(false);
		
		notificationRepo.save(n);
	}
	
	public List<Notification> getNotification(Long userId, String portalType){
		
		return notificationRepo.findByUserIdAndPortalTypeOrderByCreatedAtDesc(userId, portalType);
		
	}
	

}
