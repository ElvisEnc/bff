package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.encryptor.AnonymousSetKey;
import bg.com.bo.bff.models.payload.encryption.EncryptionKey;
import bg.com.bo.bff.providers.interfaces.IAnonymousKeyProvider;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class AnonymousKeyProvider implements IAnonymousKeyProvider {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LogManager.getLogger(AnonymousKeyProvider.class.getName());

    @Value("${anonymous.keys.max.size}")
    private Integer maxSize;
    @Value("${anonymous.key.ttl}")
    private Integer keyTtl;
    @Value("${anonymous.key.expiration.threshold}")
    private Integer expirationThreshold;

    public AnonymousKeyProvider(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public EncryptionKey getAnonymousKey(EncryptionAlgorithm encryptionAlgorithm) {
        Long size = redisTemplate.opsForSet().size(CacheConstants.ANONYMOUS_KEYS);

        if (size == null || size < maxSize) {
            return createAnonymousKey(encryptionAlgorithm);
        } else {
            EncryptionKey value = getAnonymousKeyFromStack();
            if (value != null)
                return value;
            else {
                cleanStack();
                return createAnonymousKey(encryptionAlgorithm);
            }
        }
    }

    @Override
    public EncryptionKey getAnonymousKey(String id) {
        Object result = redisTemplate.opsForValue().get(CacheConstants.ANONYMOUS_KEY_PREFIX + ":" + id);
        if (result == null)
            return null;
        try {
            return Util.stringToObject((String) result, EncryptionKey.class);
        } catch (IOException e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a key and save in the stack.
     *
     * @return an encryption key.
     */
    private EncryptionKey createAnonymousKey(EncryptionAlgorithm encryptionAlgorithm) {
        KeyPair keyPair;
        try {
            keyPair = CipherUtils.createKeys(encryptionAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }

        EncryptionKey key = EncryptionKey.create(keyPair);
        long keyTtlInMillis = Util.minutesToMillis(keyTtl);
        Long expiration = System.currentTimeMillis() + keyTtlInMillis;
        String keyName = String.format("%s:%s", CacheConstants.ANONYMOUS_KEY_PREFIX, key.getId());
        String keyNameForSet = String.format("%s:%s:%s", CacheConstants.ANONYMOUS_KEY_PREFIX, key.getId(), expiration);

        try {
            redisTemplate.opsForValue().set(keyName, Util.objectToString(key), keyTtl, TimeUnit.MINUTES);
        } catch (IOException e) {
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }

        redisTemplate.opsForSet().add(CacheConstants.ANONYMOUS_KEYS, keyNameForSet);

        return key;
    }

    /**
     * Check for expired keys and remove them from the stack.
     */
    private void cleanStack() {
        Set<Object> values = redisTemplate.opsForSet().members(CacheConstants.ANONYMOUS_KEYS);
        if (values != null) {
            for (Object value : values) {
                AnonymousSetKey anonymousSetKey = new AnonymousSetKey((String) value);
                if (anonymousSetKey.isExpired(expirationThreshold))
                    redisTemplate.opsForSet().remove(CacheConstants.ANONYMOUS_KEYS, value);
            }
        }
    }

    /**
     * Get a random existent key if exist any, otherwise return null.
     *
     * @return an existent key from stack.
     */
    private EncryptionKey getAnonymousKeyFromStack() {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(CacheConstants.ANONYMOUS_KEYS)))
            return null;

        String key = (String) redisTemplate.opsForSet().randomMember(CacheConstants.ANONYMOUS_KEYS);
        AnonymousSetKey anonymousSetKey = new AnonymousSetKey(key);
        if (anonymousSetKey.isExpired(expirationThreshold)) return null;

        Object value = redisTemplate.opsForValue().get(anonymousSetKey.getValue());
        if (value != null) {
            try {
                return Util.stringToObject((String) value, EncryptionKey.class);
            } catch (IOException e) {
                logger.error(e);
                throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
            }
        } else
            return null;
    }
}
