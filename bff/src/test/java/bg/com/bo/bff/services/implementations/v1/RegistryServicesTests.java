package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequestFixture;
import bg.com.bo.bff.application.dtos.response.registry.RegistryResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.RegistryControllerErrorResponse;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistryServicesTests {

    @Mock
    private ILoginAGNProvider loginAGNProvider;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private MiddlewareConfig mdwConfig;

    @InjectMocks
    private RegistryService service;

    @Test
    void givenValidRequestWhenRegisterByMigrationThenReturn() throws NoSuchAlgorithmException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        KeyPairGenerator generator = KeyPairGenerator.getInstance(EncryptionAlgorithm.RSA.getCode());
        generator.initialize(EncryptionAlgorithm.RSA.getKeySize());

        when(loginAGNProvider.login(request)).thenReturn(true);
        Mockito.doNothing().when(cache).evict(any(EncryptInfo.class));
        when(cacheManager.getCache(any())).thenReturn(cache);
        when(loginAGNProvider.registerDevice(eq(request), any())).thenReturn(true);

        // Act
        RegistryResponse response = service.registerByMigration(request);

        // Assert
        verify(loginAGNProvider).login(request);
        verify(loginAGNProvider).registerDevice(eq(request), any());

        assertEquals(request.getCredentials().getPersonId(), response.getPersonId());
    }

    @Test
    void givenCacheNullWhenRegisterByMigrationThenReturn() throws NoSuchAlgorithmException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        KeyPairGenerator generator = KeyPairGenerator.getInstance(EncryptionAlgorithm.RSA.getCode());
        generator.initialize(EncryptionAlgorithm.RSA.getKeySize());

        when(loginAGNProvider.login(request)).thenReturn(true);
        when(cacheManager.getCache(any())).thenReturn(null);
        when(loginAGNProvider.registerDevice(eq(request), any())).thenReturn(true);

        // Act
        RegistryResponse response = service.registerByMigration(request);

        // Assert
        verify(loginAGNProvider).login(request);
        verify(loginAGNProvider).registerDevice(eq(request), any());

        assertEquals(request.getCredentials().getPersonId(), response.getPersonId());
    }

    @Test
    void givenInvalidCredentialsWhenRegisterByMigrationThenReturnUnauthorized() throws NoSuchAlgorithmException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();
        when(loginAGNProvider.login(request)).thenReturn(false);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.registerByMigration(request));

        // Assert
        verify(loginAGNProvider).login(request);
        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.UNAUTHORIZED, ((HandledException) exception).getStatus());
        assertEquals(GenericControllerErrorResponse.UNAUTHORIZED.getCode(), ((HandledException) exception).getCode());
        assertEquals(GenericControllerErrorResponse.UNAUTHORIZED.getDescription(), ((HandledException) exception).getMessage());
    }

    @Test
    void givenInvalidRegisterDataWhenRegisterByMigrationThenReturnBadRequest() throws NoSuchAlgorithmException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        KeyPairGenerator generator = KeyPairGenerator.getInstance(EncryptionAlgorithm.RSA.getCode());
        generator.initialize(EncryptionAlgorithm.RSA.getKeySize());

        when(loginAGNProvider.login(request)).thenReturn(true);
        when(loginAGNProvider.registerDevice(eq(request), any())).thenReturn(false);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.registerByMigration(request));

        // Assert
        verify(loginAGNProvider).login(request);
        verify(loginAGNProvider).registerDevice(eq(request), any());

        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, ((HandledException) exception).getStatus());
        assertEquals(RegistryControllerErrorResponse.INVALID_REGISTER.getCode(), ((HandledException) exception).getCode());
        assertEquals(RegistryControllerErrorResponse.INVALID_REGISTER.getDescription(), ((HandledException) exception).getMessage());

        verify(loginAGNProvider).login(request);
        verify(loginAGNProvider).registerDevice(eq(request), any());
    }
}
