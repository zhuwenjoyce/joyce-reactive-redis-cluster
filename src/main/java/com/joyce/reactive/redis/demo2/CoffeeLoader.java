package com.joyce.reactive.redis.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Component
public class CoffeeLoader {
	private final ReactiveRedisConnectionFactory factory;
	private final ReactiveRedisOperations<String, Coffee> coffeeOps;

	public CoffeeLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Coffee> coffeeOps) {
		this.factory = factory;
		this.coffeeOps = coffeeOps;
	}

	@PostConstruct
	public void loadData() {
		factory.getReactiveConnection()
				.serverCommands()
				.flushAll()
				.thenMany(
					Flux.just("a Jet Black Redis", "b Darth Redis", "c Black Alert Redis")
						.map(name -> new Coffee(String.valueOf(name.charAt(0)), name))
						.flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee))
				)
				.thenMany(coffeeOps.keys("*")
						.flatMap(coffeeOps.opsForValue()::get))
				.subscribe(System.out::println);
		log.info("load data to redis cluster is successfully.");
	}
}
