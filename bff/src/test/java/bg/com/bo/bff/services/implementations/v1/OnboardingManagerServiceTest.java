package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponseFixture;
import bg.com.bo.bff.mappings.providers.onboarding.manager.OnboardingManagerMapper;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.OnboardingManagerMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;
import bg.com.bo.bff.providers.interfaces.IOnboardingManagerProvider;
import bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager.OnboardingMiddlewareResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingManagerServiceTest {
    @InjectMocks
    private OnboardingManagerService service;
    @Spy
    private OnboardingManagerMapper mapper = new OnboardingManagerMapper();

    @Mock
    private IOnboardingManagerProvider provider;
    @Mock
    private NpsService self;

    @Test
    void testGetAllDevices() throws IOException {
        // Arrange
        ListDevicesMWResponse mwResponseMock = OnboardingManagerMWResponseFixture.withDefaultListDevicesOnboarding();
        List<OnboardingManagerResponse> expectedResponse = OnboardingManagerResponseFixture.withDefaultGellAllDevice();


        // Act
        when(provider.listDeviceOnboardingManager(anyInt())).thenReturn(mwResponseMock);
        List<OnboardingManagerResponse> response = service.getAllDevices(15);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.get(0).getDeviceId(), response.get(0).getDeviceId());
        assertEquals(expectedResponse.get(0).getAgentUser(), response.get(0).getAgentUser());
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).listDeviceOnboardingManager(anyInt());

    }

    @Test
    void testGetAllDevicesWithNull() throws IOException {
        // Arrange
        ListDevicesMWResponse mwResponseMock = OnboardingManagerMWResponseFixture.withDefaultListDevicesOnboardingWithNull();
        List<OnboardingManagerResponse> expectedResponse = List.of();

        // Act
        when(provider.listDeviceOnboardingManager(anyInt())).thenReturn(mwResponseMock);
        List<OnboardingManagerResponse> response = service.getAllDevices(15);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).listDeviceOnboardingManager(anyInt());

    }

    @Test
    void testGetAllDevicesWithDataNull() throws IOException {
        // Arrange
        ListDevicesMWResponse mwResponseMock = OnboardingManagerMWResponseFixture
                .withDefaultListDevicesOnboardingWithDataNull();
        List<OnboardingManagerResponse> expectedResponse = List.of();


        // Act
        when(provider.listDeviceOnboardingManager(anyInt())).thenReturn(mwResponseMock);
        List<OnboardingManagerResponse> response = service.getAllDevices(15);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).listDeviceOnboardingManager(anyInt());

    }


    @Test
    void testDisableDevice() throws IOException {
        // Arrange
        GenericResponse expected = GenericResponse.instance(OnboardingMiddlewareResponse.SUCCESS_DEACTIVATE_DEVICE);

        when(provider.disableDevice(anyInt(), anyString())).thenReturn(
                OnboardingManagerMWResponseFixture.withDefaultDisableDevice()
        );

        // Act
        GenericResponse response = service.disableDevice(2525, "123456");

        // Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).disableDevice(anyInt(), anyString());

    }


}
