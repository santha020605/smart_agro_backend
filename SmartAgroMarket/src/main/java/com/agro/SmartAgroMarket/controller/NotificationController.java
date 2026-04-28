package com.agro.SmartAgroMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.SmartAgroMarket.dto.NotificationDTO;
import com.agro.SmartAgroMarket.models.Notification;
import com.agro.SmartAgroMarket.service.NotificationService;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	
	@GetMapping("/{userId}/{portalType}")
	public List<Notification> getNotification(@PathVariable Long userId, @PathVariable String portalType){
		return notificationService.getNotification(userId, portalType);
	}
	
	
	@PostMapping("/test")
	public String testNotification(@RequestBody NotificationDTO dto) {
		
		notificationService.sendNotification(dto.getUserId(), dto.getPortalType(), dto.getTitle(), dto.getMessage());
		
		return "Notification sent";
		
	}
	

}
