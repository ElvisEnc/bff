package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.request.RegistryRequestFixture;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.enums.response.RegistryControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistryServicesTests {
    @Mock
    private IEncryptionProvider encryptionProvider;

    @Mock
    private ILoginAGNProvider loginAGNProvider;

    @InjectMocks
    private RegistryService service;

    @Test
    void givenValidRequestWhenRegisterByMigrationThenReturn() throws NoSuchAlgorithmException {
        // Arrange
        RegistryRequest request = RegistryRequestFixture.withDefault();

        KeyPairGenerator generator = KeyPairGenerator.getInstance(EncryptionAlgorithm.RSA.getCode());
        generator.initialize(EncryptionAlgorithm.RSA.getKeySize());
        KeyPair keyPair = generator.generateKeyPair();
        String appPublicKey = CipherUtils.encodeKeyToBase64(keyPair.getPublic());

        when(loginAGNProvider.login(request)).thenReturn(true);
        when(encryptionProvider.createKeys()).thenReturn(keyPair);
        when(loginAGNProvider.registerDevice(eq(request), any())).thenReturn(true);

        // Act
        RegistryResponse response = service.registerByMigration(request);

        // Assert
        verify(loginAGNProvider).login(request);
        verify(encryptionProvider).createKeys();
        verify(loginAGNProvider).registerDevice(eq(request), any());

        assertEquals(request.getCredentials().getPersonId(), response.getPersonId());
        assertEquals(appPublicKey, response.getAppKey());
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
        KeyPair keyPair = generator.generateKeyPair();

        when(loginAGNProvider.login(request)).thenReturn(true);
        when(encryptionProvider.createKeys()).thenReturn(keyPair);
        when(loginAGNProvider.registerDevice(eq(request), any())).thenReturn(false);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.registerByMigration(request));

        // Assert
        verify(loginAGNProvider).login(request);
        verify(encryptionProvider).createKeys();
        verify(loginAGNProvider).registerDevice(eq(request), any());

        assertEquals(HandledException.class, exception.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, ((HandledException) exception).getStatus());
        assertEquals(RegistryControllerErrorResponse.INVALID_REGISTER.getCode(), ((HandledException) exception).getCode());
        assertEquals(RegistryControllerErrorResponse.INVALID_REGISTER.getDescription(), ((HandledException) exception).getMessage());

        verify(loginAGNProvider).login(request);
        verify(encryptionProvider).createKeys();
        verify(loginAGNProvider).registerDevice(eq(request), any());
    }
}
