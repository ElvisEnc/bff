package bg.com.bo.bff.providers.implementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientTokenFixture;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.application.config.MiddlewareConfigFixture;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenMiddlewareProviderTest {
    @Spy
    @InjectMocks
    TokenMiddlewareProvider provider;

    @Mock
    MiddlewareConfig middlewareConfig;

    @Mock
    IHttpClientFactory httpClientFactory;

    @Mock
    CloseableHttpClient closeableHttpClientMock;

    @Mock
    CloseableHttpResponse closeableHttpGetResponseMock;

    @Mock
    HttpEntity httpEntityMock;

    @Mock
    StatusLine statusLineMock;

    @Mock
    LoadingCache<String, ClientToken> tokenCache;

    @BeforeEach
    void setUp() {
        provider = new TokenMiddlewareProvider(httpClientFactory, middlewareConfig, tokenCache);
        setField(provider, "middlewareConfig", MiddlewareConfigFixture.withDefault(), MiddlewareConfig.class);
    }

    @Test
    void generateAccountAccessToken() throws IOException {
        // Arrange
        String sanitizedProjectName = "transactionmanager";
        Mockito.when(httpClientFactory.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(tokenCache.getIfPresent(sanitizedProjectName)).thenReturn(null);

        String json = Util.objectToString(ClientTokenFixture.withDefault());
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        // Act
        ClientToken result = provider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());

        // Assert
        assertNotNull(result);
    }

    @Test
    void generateCacheAccountAccessToken() throws IOException {
        // Arrange
        String sanitizedProjectName = "transactionmanager";
        ClientToken cachedClientToken = ClientTokenFixture.withDefault();
        Mockito.when(tokenCache.getIfPresent(sanitizedProjectName)).thenReturn(cachedClientToken);

        // Act
        ClientToken result = provider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());

        // Assert
        assertNotNull(result);
        assertEquals(cachedClientToken, result);
        Mockito.verify(tokenCache).getIfPresent(sanitizedProjectName);
    }

    @Test
    void generateAccountAccessTokenError400() throws IOException {
        //Arrange
        Mockito.when(httpClientFactory.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine().getStatusCode()).thenReturn(401);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpEntityMock);

        ErrorMiddlewareProvider errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        String json = Util.objectToString(errorMiddlewareProvider);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            provider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());
        });

        //Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    void generateAccountAccessTokenError() throws IOException {
        //Arrange
        Mockito.when(httpClientFactory.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenThrow(new RuntimeException("Test Catch General"));
        GenericException expected = new GenericException(DefaultMiddlewareError.MW_TOKEN_FAILURE);

        //Act
        Exception result = assertThrows(Exception.class, () -> provider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey()));

        //Assert
        assertThat(result).usingRecursiveComparison().ignoringFields("source").isEqualTo(expected);
    }
}