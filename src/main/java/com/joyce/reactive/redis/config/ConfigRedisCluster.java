package com.joyce.reactive.redis.config;

import com.joyce.reactive.redis.model.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * this code demo from : https://www.baeldung.com/spring-data-redis-reactive
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Slf4j
@Configuration
public class ConfigRedisCluster {

    @Primary
    @Bean("ReactiveRedisConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        return new LettuceConnectionFactory("localhost", 6379);
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7000));
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7001));
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7002));
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7003));
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7004));
        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7005));

        log.info("assemble bean ReactiveRedisConnectionFactory is successfully.");

        return new LettuceConnectionFactory(clusterConfiguration);
    }

//    @Bean("reactiveRedisTemplate")
//    public ReactiveRedisTemplate<String, EmployeeModel> reactiveRedisTemplate(
//            @Qualifier("ReactiveRedisConnectionFactory") @Autowired ReactiveRedisConnectionFactory factory
//    ) {
//        // definition key
//        StringRedisSerializer keySerializer = new StringRedisSerializer();
//        // definition value
//        Jackson2JsonRedisSerializer<EmployeeModel> valueSerializer =
//                new Jackson2JsonRedisSerializer<>(EmployeeModel.class);
//
//        RedisSerializationContext.RedisSerializationContextBuilder<String, EmployeeModel> builder =
//                RedisSerializationContext.newSerializationContext(keySerializer);
//        RedisSerializationContext<String, EmployeeModel> context =
//                builder.value(valueSerializer).build();
//        return new ReactiveRedisTemplate<>(factory, context);
//    }

}
