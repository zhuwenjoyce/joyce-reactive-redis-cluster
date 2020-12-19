package com.joyce.reactive.redis.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CoffeeController {
	private final ReactiveRedisOperations<String, Coffee> coffeeOps;

	CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
		this.coffeeOps = coffeeOps;
	}

	@GetMapping("/coffee/coffees")
	public Flux<Coffee> all() {
		log.info("request /coffees ...");
		return coffeeOps.keys("*")
				.flatMap(coffeeOps.opsForValue()::get);
	}

	@GetMapping("/coffee/id/a")
	public Mono<Coffee> getKeyA() {
		return coffeeOps.opsForValue().get("a");
	}
}
