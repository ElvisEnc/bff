package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.*;
import bg.com.bo.bff.providers.dtos.response.loyalty.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.LoyaltyMapper;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
        LoyaltyPointServerResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        when(provider.getSumPoint(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyPointResponse response = service.getSumPoint("1234", "123");

        assertNotNull(response);
        verify(provider).getSumPoint(any(), any());
        verify(mapper).convertResponse(any(LoyaltyPointServerResponse.class));
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
        GenericException exception = assertThrows(GenericException.class, () ->
                service.registerSubscription("123", "123", request)
        );
        //Assert
        assertEquals("REGISTER_ERROR", exception.getCode());
        verify(provider).registerSubscription(any(), any());
    }

    @Test
    void givenValidDataWhenRegisterRedeemVoucher() throws IOException {
        //Arrange
        RegisterRedeemVoucherRequest request = LoyaltySERequestFixture.withDefaultRegisterRedeemVoucher();
        LoyaltyRegisterRedeemVoucherResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();
        when(provider.registerRedeemVoucher(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyRedeemVoucherResponse response = service.registerRedeemVoucher("1234", "123", request);

        assertNotNull(response);
        verify(provider).registerRedeemVoucher(any(), any());
        verify(mapper).convertResponse(any(LoyaltyRegisterRedeemVoucherResponse.class));
    }

    @Test
    void givenValidDataWhenGetLevel() throws IOException {
        //Arrange
        LoyaltyGetLevelResponse expectedResponse = LoyaltySEResponseFixture.withDefaultLoyaltyGetLevel();
        when(provider.getLevel(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyLevelResponse response = service.getLevel("1234");

        assertNotNull(response);
        verify(provider).getLevel(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGetLevelResponse.class));
    }

    @Test
    void givenValidDataWhenGetPointsPeriod() throws IOException {
        //Arrange
        LoyaltyPointServerResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSumPoint();
        when(provider.getPointsPeriod(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyPointResponse response = service.getPointsPeriod("1234", "123");

        assertNotNull(response);
        verify(provider).getPointsPeriod(any(), any());
        verify(mapper).convertResponse(any(LoyaltyPointServerResponse.class));
    }

    @Test
    void givenValidDataWhenGetInitialPointsVAMOS() throws IOException {
        //Arrange
        LoyaltyGetInitialPointsVamosResponse expectedResponse = LoyaltySEResponseFixture.withDefaultInitialPoints();
        when(provider.getInitialPointsVAMOS(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyInitialPointsResponse response = service.getInitialPointsVAMOS("1234");

        assertNotNull(response);
        verify(provider).getInitialPointsVAMOS(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGetInitialPointsVamosResponse.class));
    }

    @Test
    void givenValidDataWhenVerifySubscription() throws IOException {
        //Arrange
        LoyaltySubscriptionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscription();
        when(provider.verifySubscription(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.verifySubscription("1234");

        assertNotNull(response);
        assertEquals(LoyaltyResponse.SUBSCRIPTION_EXISTS.getMessage(), response.getMessage());
        verify(provider).verifySubscription(any(), any());
    }

    @Test
    void givenValidDataWhenVerifySubscriptionFalse() throws IOException {
        //Arrange
        LoyaltySubscriptionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscriptionFalse();
        when(provider.verifySubscription(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.verifySubscription("123")
        );
        //Assert
        assertEquals("NOT_SUBSCRIPTION", exception.getCode());
        verify(provider).verifySubscription(any(), any());
    }

    @Test
    void givenValidDataWhenStatementPoints() throws IOException {
        //Arrange
        LoyaltyStatementRequest request = LoyaltySERequestFixture.withDefaultStatement();
        List<LoyaltyStatementPointsResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultStatementPoints();

        when(provider.statementPoints(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyStatementResponse response = service.statementPoints("1234", "1234", request);

        assertNotNull(response);
        verify(provider).statementPoints(any(), any());
        verify(mapper).convertResponse(expectedResponse);
    }

    @Test
    void givenEmptyStatementPointsListWhenStatementPointsThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyStatementRequest request = LoyaltySERequestFixture.withDefaultStatement();
        List<LoyaltyStatementPointsResponse> emptyResponse = Collections.emptyList();

        when(provider.statementPoints(any(), any())).thenReturn(emptyResponse);

        // Act
        LoyaltyStatementResponse response = service.statementPoints("1234", "1234", request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMovements());
        assertTrue(response.getMovements().isEmpty());

        verify(provider).statementPoints(any(), any());
        verify(mapper).convertResponse(emptyResponse);
    }

    @Test
    void givenNullStatementPointsListWhenStatementPointsThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyStatementRequest request = LoyaltySERequestFixture.withDefaultStatement();

        when(provider.statementPoints(any(), any())).thenReturn(null);

        // Act
        LoyaltyStatementResponse response = service.statementPoints("1234", "1234", request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMovements());
        assertTrue(response.getMovements().isEmpty());

        verify(provider).statementPoints(any(), any());
    }


    @Test
    void givenValidDataWhenGeneralInformation() throws IOException {
        //Arrange
        LoyaltyGeneralInformationResponse expectedResponse = LoyaltySEResponseFixture.withDefaultGeneralInformation();

        when(provider.getGeneralInformation(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyGeneralInfoResponse response = service.getGeneralInformation("1234");

        assertNotNull(response);
        verify(provider).getGeneralInformation(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGeneralInformationResponse.class));
    }

    @Test
    void givenValidDataWhenConvertResponseThenReturnsValidLoyaltyGeneralInfoResponse() throws IOException{
        // Arrange
        LoyaltyGeneralInformationResponse expectedResponse = LoyaltySEResponseFixture.withDefaultGeneralInformationData();

        when(provider.getGeneralInformation(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyGeneralInfoResponse response = service.getGeneralInformation("1234");

        assertNotNull(response);
        verify(provider).getGeneralInformation(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGeneralInformationResponse.class));
    }

}
