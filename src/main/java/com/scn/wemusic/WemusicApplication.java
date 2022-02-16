package com.scn.wemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(basePackages = "com.scn")
public class WemusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WemusicApplication.class, args);
	}

}
