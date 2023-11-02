package com.system.artworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ArtworkSpaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArtworkSpaceApplication.class, args);
	}

}
