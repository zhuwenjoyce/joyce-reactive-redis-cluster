package com.joyce.reactive.redis.demo1.controller;

import com.joyce.reactive.redis.demo1.model.EmployeeModel;
import com.joyce.reactive.redis.demo2.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Slf4j
@RestController
public class MyReactiveRedisController {

    @Autowired
    @Qualifier("reactiveRedisTemplate")
    private ReactiveRedisTemplate<String, EmployeeModel> reactiveRedisTemplate;

    @GetMapping("/my-reactive-redis/init-key-abc")
    public Map<String, Object> initRedisData () {
        EmployeeModel employee = EmployeeModel.builder()
                .id("id-EmployeeModel")
                .name("my-username")
                .department("department-A")
                .build();
        Mono<Boolean> booleanMono = reactiveRedisTemplate.opsForValue().set("id-EmployeeModel", employee);
        booleanMono.subscribe();
        // expiration time is one day
        reactiveRedisTemplate.expire("abc", Duration.of(24L, ChronoUnit.HOURS));
        return new HashMap<>();
    }

    @GetMapping("/my-reactive-redis/get-key-abc")
    public Mono<EmployeeModel> getEmployeeModelByRedis() {
        return reactiveRedisTemplate.opsForValue().get("id-EmployeeModel");
    }

}
