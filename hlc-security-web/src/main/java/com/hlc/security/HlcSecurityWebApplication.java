package com.hlc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class HlcSecurityWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(HlcSecurityWebApplication.class, args);
	}
}
