package com.system.artworkspace;

import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ArtworkSpaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArtworkSpaceApplication.class, args);
	}

}
