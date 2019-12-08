package ru.maxmorev.minioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MinioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinioServiceApplication.class, args);
	}

}
