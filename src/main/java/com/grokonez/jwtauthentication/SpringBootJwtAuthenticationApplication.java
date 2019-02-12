package com.grokonez.jwtauthentication;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.grokonez.jwtauthentication.services.StorageService;

@SpringBootApplication
public class SpringBootJwtAuthenticationApplication  extends SpringBootServletInitializer implements CommandLineRunner {
    
	@Resource
	StorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtAuthenticationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//storageService.deleteAll();
		//storageService.init();
	}
}
