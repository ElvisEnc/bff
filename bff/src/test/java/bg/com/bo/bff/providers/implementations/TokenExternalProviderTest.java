package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.implementations.feign.CryptoCurrencyFeignClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenExternalProviderTest {
    @Mock
    private CryptoCurrencyFeignClient authClient;

    @InjectMocks
    private TokenExternalProvider tokenExternalProvider;

    private final String key = "mdw_token_test_user";

    private TokenAuthenticationRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = TokenAuthenticationRequestDto.builder()
                .username("test_user")
                .password("dummy")
                .build();
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void shouldRequestNewTokenIfNoneCached() {
        ClientToken newToken = new ClientToken();
        newToken.setAccessToken("new-token");
        newToken.setExpiresIn(1200);

        when(authClient.getTokenAuthentication(any())).thenReturn(newToken);

        ClientToken result = tokenExternalProvider.generateAccountAccessToken(requestDto);

        assertEquals("new-token", result.getAccessToken());
        assertNotNull(result.getRequiredAt());
        assertNotNull(result.getExpiredAt());

        verify(authClient, times(1)).getTokenAuthentication(any());
    }

    @Test
    void shouldThrowGenericExceptionOnFeignError() {
        when(authClient.getTokenAuthentication(any())).thenThrow(new RuntimeException("Feign error"));

        GenericException ex = assertThrows(GenericException.class, () ->
                tokenExternalProvider.generateAccountAccessToken(requestDto));

        assertEquals("BFF-MWTF", ex.getCode());
        verify(authClient, times(1)).getTokenAuthentication(any());
    }

    @Test
    void shouldUseDefaultExpirationIfExpiresInIsNull() {
        // Arrange
        ClientToken tokenWithNullExpiresIn = new ClientToken();
        tokenWithNullExpiresIn.setAccessToken("new-token");
        tokenWithNullExpiresIn.setExpiresIn(null); // importante
        when(authClient.getTokenAuthentication(any())).thenReturn(tokenWithNullExpiresIn);

        // Act
        ClientToken result = tokenExternalProvider.generateAccountAccessToken(requestDto);

        // Assert
        assertEquals("new-token", result.getAccessToken());
        assertEquals(3600, result.getExpiresIn());
        verify(authClient).getTokenAuthentication(any());
    }

    @Test
    void shouldReturnCachedTokenIfNotExpired() throws Exception {
        ClientToken cachedToken = new ClientToken();
        cachedToken.setAccessToken("cached-token");
        cachedToken.setExpiresIn(3600);
        cachedToken.setRequiredAt(LocalDateTime.now().toString());
        cachedToken.setExpiredAt(LocalDateTime.now().plusHours(1).toString());

        Map<String, ClientToken> tokenMap = new HashMap<>();
        tokenMap.put(key, cachedToken);
        setPrivateField(tokenExternalProvider, "tokenStore", tokenMap);

        LocalDateTime futureExpiration = LocalDateTime.now().plusHours(1);
        Map<String, LocalDateTime> expirationMap = new HashMap<>();
        expirationMap.put(key, futureExpiration);
        setPrivateField(tokenExternalProvider, "expirationStore", expirationMap);

        ClientToken result = tokenExternalProvider.generateAccountAccessToken(requestDto);

        assertEquals("cached-token", result.getAccessToken());
        verify(authClient, never()).getTokenAuthentication(any());
    }


}
