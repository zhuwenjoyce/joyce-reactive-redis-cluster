package com.joyce.redis.config;

import com.joyce.redis.queue.MessagePublisher;
import com.joyce.redis.queue.RedisMessagePublisher;
import com.joyce.redis.queue.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Configuration
@ComponentScan("com.joyce.redis")
@EnableRedisRepositories(basePackages = "com.joyce.redis.repo")
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Bean("jedisConnectionFactory")
    JedisConnectionFactory jedisConnectionFactory(
            @Value("${redis.cluster.password}") String clusterPassword
            , @Value("${redis.cluster.nodes}") String clusterNodes
    ) {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.setPassword(clusterPassword);
        Arrays.stream(clusterNodes.split(","))
                .forEach(node -> {
                    String[] nodeInfo = node.split(":");
                    clusterConfiguration.addClusterNode(new RedisClusterNode(nodeInfo[0], Integer.valueOf(nodeInfo[1])));
                    log.info("config redis node {}:{} to cluster is successfully.", nodeInfo[0], nodeInfo[1]);
                });
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7000));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7001));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7002));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7003));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7004));
//        clusterConfiguration.addClusterNode(new RedisClusterNode("127.0.0.1", 7005));
        return new JedisConnectionFactory(clusterConfiguration);
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("jedisConnectionFactory") JedisConnectionFactory factory
    ) {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer(
            @Qualifier("jedisConnectionFactory") JedisConnectionFactory factory
    ) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher(
            @Qualifier("redisTemplate") RedisTemplate redisTemplate
    ) {
        return new RedisMessagePublisher(redisTemplate, topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
}
