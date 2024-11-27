package bg.com.bo.bff.application.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class PrefixedStringRedisSerializer implements RedisSerializer<String> {

    private final String prefix;
    private final StringRedisSerializer delegate = new StringRedisSerializer();

    public PrefixedStringRedisSerializer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public byte[] serialize(String key) {
        return delegate.serialize(prefix + key);
    }

    @Override
    public String deserialize(byte[] bytes) {
        String deserializedKey = delegate.deserialize(bytes);
        if (deserializedKey != null && deserializedKey.startsWith(prefix))
            return deserializedKey.substring(prefix.length());
        return deserializedKey;
    }
}