package com.dmyroniuk.teya.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dmyroniuk.teya")
public class TeyaTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeyaTaskApplication.class, args);
	}

}
