package com.ravi.ramzanorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RamzanOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RamzanOrderApplication.class, args);
	}

}
