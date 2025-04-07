package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.LoyaltyMapper;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoyaltyServiceTest {
    @InjectMocks
    private LoyaltyService service;
    @Mock
    private ILoyaltyProvider provider;
    @Spy
    private LoyaltyMapper mapper = new LoyaltyMapper();

    @Test
    void givenValidDataWhenGetSystemCode() throws IOException {
        //Arrange
        LoyaltySystemCodeServerResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSystemCode();
        when(provider.getSystemCode(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltySystemCodeResponse response = service.getSystemCode("1234");

        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getSystemCode(any(), any());
        verify(mapper).convertResponse(any(LoyaltySystemCodeServerResponse.class));
    }

    @Test
    void givenValidDataWhenGetSumPoint() throws IOException {
        //Arrange
        LoyaltySumPointServerResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        when(provider.getSumPoint(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltySumPointResponse response = service.getSumPoint("1234", "123");

        assertNotNull(response);
        verify(provider).getSumPoint(any(), any());
        verify(mapper).convertResponse(any(LoyaltySumPointServerResponse.class));
    }

    @Test
    void givenValidDataWhenRegisterSubscription() throws IOException {
        //Arrange
        RegisterSubscriptionRequest request = LoyaltySERequestFixture.withDefaultRegisterSubscription();
        LoyaltyRegisterSubscriptionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscription();
        when(provider.registerSubscription(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.registerSubscription("1234", "123", request);

        assertNotNull(response);
        assertEquals(LoyaltyResponse.REGISTERED_EXIT.getMessage(), response.getMessage());
        verify(provider).registerSubscription(any(), any());
    }

    @Test
    void givenValidDataWhenRegisterSubscriptionExist() throws IOException {
        //Arrange
        RegisterSubscriptionRequest request = LoyaltySERequestFixture.withDefaultRegisterSubscription();
        LoyaltyRegisterSubscriptionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscriptionExist();
        when(provider.registerSubscription(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.registerSubscription("1234", "123", request);

        assertNotNull(response);
        assertEquals(LoyaltyResponse.REGISTRATION_EXISTS.getMessage(), response.getMessage());
        verify(provider).registerSubscription(any(), any());
    }

    @Test
    void givenValidDataWhenRegisterSubscriptionException() throws IOException {
        //Arrange
        RegisterSubscriptionRequest request = LoyaltySERequestFixture.withDefaultRegisterSubscription();
        LoyaltyRegisterSubscriptionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterSubscriptionException();
        when(provider.registerSubscription(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.registerSubscription("123", "123", request)
        );
        //Assert
        assertEquals("REGISTER_ERROR", exception.getCode());
    }
}
