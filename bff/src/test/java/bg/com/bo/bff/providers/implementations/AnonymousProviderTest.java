package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.encryptor.AnonymousSetKey;
import bg.com.bo.bff.models.payload.encryption.EncryptionKey;
import bg.com.bo.bff.providers.fixtures.anonymous.key.provider.AnonymousKeyProviderFixture;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class AnonymousProviderTest {
    public static final int MAX_SIZE = 2;
    public static final int EXPIRATION_THRESHOLD = 10;
    public static final int KEY_TTL = 60;
    private AnonymousKeyProvider provider;
    private final EncryptionAlgorithm encryptionAlgorithm = EncryptionAlgorithm.RSA;
    private ValueOperations<String, Object> valueOperations;
    private SetOperations<String, Object> setOperations;
    private RedisTemplate<String,Object> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        setOperations = mock(SetOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForSet()).thenReturn(setOperations);

        provider = new AnonymousKeyProvider(redisTemplate);

        setField(provider, "maxSize", MAX_SIZE);
        setField(provider, "keyTtl", KEY_TTL);
        setField(provider, "expirationThreshold", EXPIRATION_THRESHOLD);
    }

    @Test
    void givenNonExistingAlgorithmWhenGetAnonymousKeyThenReturnException() throws IOException {
        // Arrange
        EncryptionAlgorithm nonExistingEncryptionAlgorithm = mock(EncryptionAlgorithm.class);
        when(nonExistingEncryptionAlgorithm.getCode()).thenReturn("NON_EXISTING_ALGORITHM");

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> provider.getAnonymousKey(nonExistingEncryptionAlgorithm));

        // Assert
        assertEquals(DefaultMiddlewareError.INTERNAL_SERVER_ERROR.getMessage(), exception.getMessage());
        assertEquals(DefaultMiddlewareError.INTERNAL_SERVER_ERROR.getCode(), exception.getCode());
    }

    @Test
    void givenExistingAnonymousKeysAndMaxSizeReachedWhenGetAnonymousKeyThenReturnEncryptedKey() throws IOException {
        // Arrange
        EncryptionKey expected = AnonymousKeyProviderFixture.withDefaultEncryptionKey();
        String expectedAsString = Util.objectToString(expected);
        Long expectedExpiration = System.currentTimeMillis() + Util.minutesToMillis(KEY_TTL) + Util.minutesToMillis(EXPIRATION_THRESHOLD);
        String expectedSetKey = String.format("%s:%s:%s", CacheConstants.ANONYMOUS_KEY_PREFIX, expected.getId(), expectedExpiration);
        String expectedValueKey = String.format("%s:%s", CacheConstants.ANONYMOUS_KEY_PREFIX, expected.getId());

        when(setOperations.size(CacheConstants.ANONYMOUS_KEYS)).thenReturn(Long.valueOf(MAX_SIZE));
        when(redisTemplate.hasKey(CacheConstants.ANONYMOUS_KEYS)).thenReturn(true);
        when(setOperations.randomMember(CacheConstants.ANONYMOUS_KEYS)).thenReturn(expectedSetKey);
        when(valueOperations.get(expectedValueKey)).thenReturn(expectedAsString);

        // Act
        EncryptionKey result = provider.getAnonymousKey(encryptionAlgorithm);

        // Assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenExistingAnonymousKeysButExpiredAndMaxSizeReachedWhenGetAnonymousKeyThenNewEncryptedKey() throws IOException {
        // Arrange
        EncryptionKey expired = AnonymousKeyProviderFixture.withDefaultEncryptionKey();
        Long expiredData = System.currentTimeMillis();
        String expiredSetKey = String.format("%s:%s:%s", CacheConstants.ANONYMOUS_KEY_PREFIX, expired.getId(), expiredData);

        when(setOperations.size(CacheConstants.ANONYMOUS_KEYS)).thenReturn(Long.valueOf(MAX_SIZE));
        when(redisTemplate.hasKey(CacheConstants.ANONYMOUS_KEYS)).thenReturn(true);
        when(setOperations.randomMember(CacheConstants.ANONYMOUS_KEYS)).thenReturn(expiredSetKey);
        doNothing().when(valueOperations).set(any(String.class),any(String.class),any(Long.class),any(TimeUnit.class));
        when(setOperations.add(any(),any())).thenReturn(1L);
        when(setOperations.members(CacheConstants.ANONYMOUS_KEYS)).thenReturn(Set.of(expiredSetKey));
        when(setOperations.remove(CacheConstants.ANONYMOUS_KEYS, expiredSetKey)).thenReturn(1L);

        // Act
        EncryptionKey result = provider.getAnonymousKey(encryptionAlgorithm);

        // Assert
        assertNotSame(result.getId(), expired.getId());
        verify(valueOperations, times(1)).set(any(String.class),any(String.class),any(Long.class),any(TimeUnit.class));
        verify(setOperations, times(1)).add(any(), any());
        assertThat(result).hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {MAX_SIZE - 1})
    void givenMaxSizeNoReachedWhenGetAnonymousKeyThenReturnNewEncryptedKey(Long actualSize){
        // Arrange
        when(setOperations.size(CacheConstants.ANONYMOUS_KEYS)).thenReturn(actualSize);
        doNothing().when(valueOperations).set(any(String.class),any(String.class),any(Long.class),any(TimeUnit.class));
        when(setOperations.add(any(),any())).thenReturn(1L);

        // Act
        EncryptionKey result = provider.getAnonymousKey(encryptionAlgorithm);

        // Assert
        verify(valueOperations, times(1)).set(any(String.class),any(String.class),any(Long.class),any(TimeUnit.class));
        verify(setOperations, times(1)).add(any(), any());
        assertThat(result).hasNoNullFieldsOrProperties();
    }

    @Test
    void givenExistingAnonymousKeyWhenGetAnonymousKeyByIdThenReturnEncryptedKey() throws IOException {
        // Arrange
        EncryptionKey expected = AnonymousKeyProviderFixture.withDefaultEncryptionKey();
        String expectedAsString = Util.objectToString(expected);

        when(valueOperations.get(CacheConstants.ANONYMOUS_KEY_PREFIX + ":" + expected.getId())).thenReturn(expectedAsString);

        // Act
        EncryptionKey result = provider.getAnonymousKey(expected.getId());

        // Assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenNoExistingAnonymousKeyWhenGetAnonymousKeyByIdThenReturnNull() throws IOException {
        // Arrange
        EncryptionKey expected = AnonymousKeyProviderFixture.withDefaultEncryptionKey();
        when(valueOperations.get(CacheConstants.ANONYMOUS_KEY_PREFIX + ":" + expected.getId())).thenReturn(null);

        // Act
        EncryptionKey result = provider.getAnonymousKey(expected.getId());

        // Assert
        assertNull(result);
    }
}
