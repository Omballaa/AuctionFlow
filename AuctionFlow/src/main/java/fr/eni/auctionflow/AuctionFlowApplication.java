package fr.eni.auctionflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.eni.encheres.model") 
@EnableJpaRepositories("com.eni.encheres.dao")
public class AuctionFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionFlowApplication.class, args);
	}

}



