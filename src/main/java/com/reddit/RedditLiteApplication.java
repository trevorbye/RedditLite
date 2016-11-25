package com.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RedditLiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedditLiteApplication.class, args);
	}
}
