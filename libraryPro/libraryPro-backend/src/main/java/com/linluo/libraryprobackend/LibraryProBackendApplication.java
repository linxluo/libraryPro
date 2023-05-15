package com.linluo.libraryprobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibraryProBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryProBackendApplication.class, args);
	}

}
