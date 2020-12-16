package com.joyce.redis.queue;

public interface MessagePublisher {

    void publish(final String message);
}
