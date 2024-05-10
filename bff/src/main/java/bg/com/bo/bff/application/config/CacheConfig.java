package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.constants.CacheConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
@Configuration
public class CacheConfig {
    @Value("${cache.ttl.default}")
    private Integer defaultTtl;

    @Value("${cache.prefix}")
    private String cachePrefix;

    @Value("${cache.certs.ttl}")
    private Integer cacheCertsTtl;

    @Value("${cache.encryption.keys.ttl}")
    private Integer encryptionKeysTtl;

    @Value("${cache.account.statement.ttl}")
    private Integer accountStatementTtl;

    @Value("${cache.destination.accounts.ttl}")
    private Integer destinationAccountTtl;

    @Value("${cache.qr.generated.paid.ttl}")
    private Integer qrListGeneratedAndPaidTtl;

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(CacheConstants.CERTS_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(cacheCertsTtl))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .prefixCacheNameWith(cachePrefix))
                .withCacheConfiguration(CacheConstants.ENCRYPTION_KEYS_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(encryptionKeysTtl))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .prefixCacheNameWith(cachePrefix))
                .withCacheConfiguration(CacheConstants.ACCOUNTS_STATEMENTS,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(accountStatementTtl))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .prefixCacheNameWith(cachePrefix))
                .withCacheConfiguration(CacheConstants.DESTINATION_ACCOUNTS,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(destinationAccountTtl))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .prefixCacheNameWith(cachePrefix))
                .withCacheConfiguration(CacheConstants.QR_GENERATED_PAID,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(qrListGeneratedAndPaidTtl))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .prefixCacheNameWith(cachePrefix));
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(defaultTtl))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .prefixCacheNameWith(cachePrefix);

    }
}