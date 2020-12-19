package com.joyce.reactive.redis.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * demo from : https://spring.io/guides/gs/spring-data-reactive-redis/
 *
 * this demo can provide throughput 9306/second
 */
@SpringBootApplication
public class ReactiveRedisDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveRedisDemo2Application.class, args);
	}

}
