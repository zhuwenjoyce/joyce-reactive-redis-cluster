package com.joyce.reactive.redis.demo1.config;

import com.joyce.reactive.redis.demo1.model.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Slf4j
@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory factory;

//    @Bean("reactiveRedisConnectionFactory")
//    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(
//            @Value("${redis.cluster.password}") String clusterPassword
//            , @Value("${redis.cluster.nodes}") String clusterNodes
//    ) {
//        log.info("factory ===== " + factory.getClass().getName());
//        RedisClusterConnection connection = factory.getClusterConnection();
//        String ping = factory.getClusterConnection().ping(new RedisClusterNode("127.0.0.1", 7000));
//        log.info("ping ------ " + ping);
//        String ping2 = factory.getClusterConnection().ping(new RedisClusterNode("127.0.0.1", 7022));
//        log.info("ping2 ------ " + ping2);
//
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
//        clusterConfiguration.setPassword(clusterPassword);
//        Arrays.stream(clusterNodes.split(","))
//                .forEach(node -> {
//                    String[] nodeInfo = node.split(":");
//                    clusterConfiguration.addClusterNode(new RedisClusterNode(nodeInfo[0], Integer.valueOf(nodeInfo[1])));
//                    log.info("config redis node {}:{} to cluster for reactive redis is successfully.", nodeInfo[0], nodeInfo[1]);
//                });
//
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7000));
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7001));
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7002));
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7003));
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7004));
////        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7005));
//
////        log.info("assemble bean ReactiveRedisConnectionFactory is successfully.");
//
//        ReactiveRedisConnectionFactory factory = new LettuceConnectionFactory(clusterConfiguration);
//
//        return factory;
//    }

    @Bean("reactiveRedisTemplate")
    public ReactiveRedisTemplate<String, EmployeeModel> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<EmployeeModel> serializer = new Jackson2JsonRedisSerializer<>(EmployeeModel.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, EmployeeModel> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, EmployeeModel> context = builder.value(serializer)
            .build();
        ReactiveRedisTemplate template = new ReactiveRedisTemplate<>(factory, context);
        return template;
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

    @PreDestroy
    public void cleanRedis() {
        log.info("will destroy when clean redis......");
        factory.getConnection().flushDb();
    }
}
