package bg.com.bo.bff.shared.cache;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@TestConfiguration
public class EmbeddedRedisConfiguration {
    private final RedisServer redisServer;

    public EmbeddedRedisConfiguration() {
        this.redisServer = new RedisServer();
    }

    @PostConstruct
    public void startRedis() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}
