package com.agro.SmartAgroMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartAgroMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartAgroMarketApplication.class, args);
	}

}
