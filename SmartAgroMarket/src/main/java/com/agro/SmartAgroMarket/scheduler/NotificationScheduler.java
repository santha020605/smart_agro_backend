package com.agro.SmartAgroMarket.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agro.SmartAgroMarket.Repository.NotificationRepo;
import com.agro.SmartAgroMarket.models.Notification;

@Component
public class NotificationScheduler {
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	@Scheduled(cron = "0 0 10 * * ?")
	public void deleteOldNotifications() {
		
		LocalDateTime time = LocalDateTime.now().minusDays(1);
		
		List<Notification> oldList = notificationRepo.findByCreatedAtBefore(time);
		
		notificationRepo.deleteAll(oldList);
		
	}

}
