package com.joyce.reactive.redis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.PreDestroy;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Configuration
public class ConfigRedisFlush_DB_BeforeDestroy {

//    @Qualifier("reactiveRedisConnectionFactory")
//    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;
//
//
//    @PreDestroy
//    public void cleanRedis() {
//        RedisConnectionFactory factory = null;
//        factory.getConnection().flushDb();
//    }

}
