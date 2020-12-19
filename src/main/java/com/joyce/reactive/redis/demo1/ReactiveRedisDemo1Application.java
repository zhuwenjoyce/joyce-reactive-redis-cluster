package com.joyce.reactive.redis.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * this demo can provide throughput 8090/second
 */
@SpringBootApplication
public class ReactiveRedisDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveRedisDemo1Application.class, args);
	}

}
