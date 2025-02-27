package fr.eni.auctionflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("fr.eni.auctionflow.model")
@EnableJpaRepositories("fr.eni.auctionflow.dao")
public class AuctionFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionFlowApplication.class, args);
	}

}



