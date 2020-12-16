package com.joyce.reactive.redis.config;

import com.joyce.reactive.redis.model.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.Arrays;

@Slf4j
@Configuration
public class RedisConfig {

//    @Autowired
//    RedisConnectionFactory factory;

    @Bean("reactiveRedisConnectionFactory")
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(
            @Value("${redis.cluster.password}") String clusterPassword
            , @Value("${redis.cluster.nodes}") String clusterNodes
    ) {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        Arrays.stream(clusterNodes.split(","))
                .forEach(node -> {
                    String[] nodeInfo = node.split(":");
                    clusterConfiguration.addClusterNode(new RedisClusterNode(nodeInfo[0], Integer.valueOf(nodeInfo[1])));
                    log.info("config redis node {}:{} to cluster for reactive redis is successfully.", nodeInfo[0], nodeInfo[1]);
                });

//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7000));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7001));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7002));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7003));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7004));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7005));

//        log.info("assemble bean ReactiveRedisConnectionFactory is successfully.");

        ReactiveRedisConnectionFactory factory = new LettuceConnectionFactory(clusterConfiguration);
        return factory;
    }

    @Bean
    public ReactiveRedisTemplate<String, EmployeeModel> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<EmployeeModel> serializer = new Jackson2JsonRedisSerializer<>(EmployeeModel.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, EmployeeModel> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, EmployeeModel> context = builder.value(serializer)
            .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveKeyCommands keyCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
            .keyCommands();
    }

    @Bean
    public ReactiveStringCommands stringCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
            .stringCommands();
    }
}
