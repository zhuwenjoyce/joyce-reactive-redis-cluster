package com.joyce.reactive.redis.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Controller
public class RedisTestController {

    @Qualifier("reactiveRedisTemplate")
    private ReactiveStringRedisTemplate reactiveRedisTemplate;

    @RequestMapping("/test")
    public String tt () {
        return "123";
    }

}
