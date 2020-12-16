package com.joyce.reactive.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * this code demo from : https://www.baeldung.com/spring-data-redis-reactive
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Slf4j
@SpringBootTest
public class ReactiveRedisClusterTest {
    @Qualifier("reactiveRedisTemplate")
    private ReactiveStringRedisTemplate reactiveRedisTemplate;
    private ReactiveListOperations<String, String> reactiveListOps;

    public static final String LIST_NAME = "reactive-redis-junit-test-key";

    @Before
    public void setup() {
        reactiveListOps = reactiveRedisTemplate.opsForList();
    }

    @Test
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        log.info("start!!!");
        Mono<Long> lPush = reactiveListOps.leftPushAll(LIST_NAME, "first", "second")
                .log("Pushed");
        StepVerifier.create(lPush)
                .expectNext(2L)
                .verifyComplete();
        Mono<String> lPop = reactiveListOps.leftPop(LIST_NAME)
                .log("Popped");
        StepVerifier.create(lPop)
                .expectNext("second")
                .verifyComplete();
        log.info("end!!!");
    }
}
