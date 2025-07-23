package bg.com.bo.bff.services.implementations.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class SessionServiceTest {
    private Cache cache;
    private SessionService service;

    @BeforeEach
    void setUp() {
        CacheManager cacheManager = Mockito.mock(CacheManager.class);
        cache = Mockito.mock(Cache.class);
        when(cacheManager.getCache(any())).thenReturn(cache);
        service = new SessionService(cacheManager);
    }

    @Test
    void givenTokenWhenPutOnBlacklistThenSaveAtCacheSuccessfully() {
        // Arrange
        String token = UUID.randomUUID().toString();

        // Act
        service.blacklist(token);

        //Assert
        verify(cache).putIfAbsent(token, token);
    }

    @Test
    void givenTokenWhenOnBlacklistThenReturnTrue() {
        // Arrange
        String expectedToken = UUID.randomUUID().toString();
        Cache.ValueWrapper value = () -> expectedToken;
        when(cache.get(expectedToken)).thenReturn(value);

        // Act
        boolean result = service.isOnBlacklist(expectedToken);

        //Assert
        assertTrue(result);
    }

    @Test
    void givenTokenWhenNotOnBlacklistThenReturnFalse() {
        // Arrange
        String unexpectedToken = UUID.randomUUID().toString();

        // Act
        boolean result = service.isOnBlacklist(unexpectedToken);

        //Assert
        assertFalse(result);
    }
}
