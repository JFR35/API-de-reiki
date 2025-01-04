package com.reikitubienestar.reiki_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ReikiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReikiRestApplication.class, args);
	}

}
