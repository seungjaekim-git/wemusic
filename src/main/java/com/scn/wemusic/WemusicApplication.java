package com.scn.wemusic;

import com.scn.wemusic.user.item.AppProperties;
import com.scn.wemusic.user.item.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class WemusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WemusicApplication.class, args);
	}

}
