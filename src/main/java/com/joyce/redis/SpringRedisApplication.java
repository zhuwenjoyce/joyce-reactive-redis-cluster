package com.joyce.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * demo from : https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-redis
 *
 * this demo can provide throughput 3890/second
 */
@SpringBootApplication
public class SpringRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }

}
