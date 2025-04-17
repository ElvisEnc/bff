package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyRequestFixture;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
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
    void givenNullInformationWhenRegisterRedeemVoucherThenRequestBuiltWithoutInformation() throws IOException {
        // Arrange
        RegisterRedeemVoucherRequest request = LoyaltySERequestFixture.withDefaultRegisterRedeemVoucherNull();
        LoyaltyRegisterRedeemVoucherResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();

        when(provider.registerRedeemVoucher(any(), any())).thenReturn(expectedResponse);

        // Act
        LoyaltyRedeemVoucherResponse response = service.registerRedeemVoucher("5678", "SYS-002", request);

        // Assert
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
        LoyaltyStatusResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscription();
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
        LoyaltyStatusResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscriptionFalse();
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
        LoyaltyStatementRequest request = LoyaltyRequestFixture.withDefaultStatement();
        List<LoyaltyStatementPointsResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultStatementPoints();

        when(provider.statementPoints(any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoyaltyStatementResponse> response = service.statementPoints("1234", "1234", request);

        assertNotNull(response);
        verify(provider).statementPoints(any(), any());
        verify(mapper).convertResponse(expectedResponse);
    }

    @Test
    void givenEmptyStatementPointsListWhenStatementPointsThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyStatementRequest request = LoyaltyRequestFixture.withDefaultStatement();
        List<LoyaltyStatementPointsResponse> emptyResponse = Collections.emptyList();

        when(provider.statementPoints(any(), any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyStatementResponse> response = service.statementPoints("1234", "1234", request);

        // Assert
        assertNotNull(response);

        verify(provider).statementPoints(any(), any());
        verify(mapper).convertResponse(emptyResponse);
    }

    @Test
    void givenNullStatementPointsListWhenStatementPointsThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyStatementRequest request = LoyaltyRequestFixture.withDefaultStatement();

        when(provider.statementPoints(any(), any())).thenReturn(null);

        // Act
        List<LoyaltyStatementResponse> response = service.statementPoints("1234", "1234", request);

        // Assert
        assertNotNull(response);

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

    @Test
    void givenValidDataWhenGetImageInformation() throws IOException{
        // Arrange
        LoyaltyGetImageResponse expectedResponse = LoyaltySEResponseFixture.withDefaultImage();

        when(provider.getImageInformation(any())).thenReturn(expectedResponse);

        //Act
        LoyaltyImageResponse response = service.getImageInformation("1234");

        assertNotNull(response);
        verify(provider).getImageInformation(any());
        verify(mapper).convertResponse(any(LoyaltyGetImageResponse.class));
    }

    @Test
    void givenValidDataWhenImagesInformation() throws IOException {
        //Arrange
        LoyaltyImageRequest request = LoyaltyRequestFixture.withDefaultImage();
        LoyaltyGetImageResponse expectedResponse = LoyaltySEResponseFixture.withDefaultImage();
        List<LoyaltyGetImageResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        when(provider.getImagesInformation(any())).thenReturn(expectedListResponse);

        //Act
        List<LoyaltyImageResponse> response = service.getImagesInformation(request);

        assertNotNull(response);
        verify(provider).getImagesInformation(any());
        verify(mapper).convertResponseImage(expectedListResponse);
    }

    @Test
    void givenEmptyStatementPointsListWhenImagesInformationThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyImageRequest request = LoyaltyRequestFixture.withDefaultImage();
        List<LoyaltyGetImageResponse> emptyResponse = Collections.emptyList();

        when(provider.getImagesInformation(any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyImageResponse> response = service.getImagesInformation(request);

        // Assert
        assertNotNull(response);

        verify(provider).getImagesInformation(any());
        verify(mapper).convertResponseImage(emptyResponse);
    }

    @Test
    void givenNullStatementPointsListWhenImagesInformationThenReturnsEmptyMovements() throws IOException {
        // Arrange
        LoyaltyImageRequest request = LoyaltyRequestFixture.withDefaultImage();

        when(provider.getImagesInformation(any())).thenReturn(null);

        // Act
        List<LoyaltyImageResponse> response = service.getImagesInformation(request);

        // Assert
        assertNotNull(response);

        verify(provider).getImagesInformation(any());
    }

    @Test
    void givenImageWithValidIdentifierWhenConvertResponseImageThenIdentifierIsPreserved() {
        // Arrange
        LoyaltyGetImageResponse responseWithId = LoyaltySEResponseFixture.withDefaultImageData();

        List<LoyaltyGetImageResponse> list = List.of(responseWithId);

        // Act
        List<LoyaltyImageResponse> result = mapper.convertResponseImage(list);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(123, result.get(0).getIdentifier());
    }

    @Test
    void givenValidDataWhenCategoryPromotions() throws IOException {
        //Arrange
        LoyaltyGetCategoryPromotionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultCategoryPromotion();
        List<LoyaltyGetCategoryPromotionResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        when(provider.getCategoryPromotions(any())).thenReturn(expectedListResponse);

        //Act
        List<LoyaltyCategoryPromotionResponse> response = service.getCategoryPromotions("123");

        assertNotNull(response);
        verify(provider).getCategoryPromotions(any());
        verify(mapper).convertResponseCategoryProm(expectedListResponse);
    }

    @Test
    void givenEmptyCategoryPromotionsListWhenCategoryPromotionsThenReturnsEmpty() throws IOException {
        // Arrange
        List<LoyaltyGetCategoryPromotionResponse> emptyResponse = Collections.emptyList();

        when(provider.getCategoryPromotions(any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyCategoryPromotionResponse> response = service.getCategoryPromotions("123");

        // Assert
        assertNotNull(response);

        verify(provider).getCategoryPromotions(any());
        verify(mapper).convertResponseCategoryProm(emptyResponse);
    }

    @Test
    void givenNullCategoryPromotionsListWhenCategoryPromotionsThenReturnsEmpty() throws IOException {
        // Arrange
        when(provider.getCategoryPromotions(any())).thenReturn(null);

        // Act
        List<LoyaltyCategoryPromotionResponse> response = service.getCategoryPromotions("123");

        // Assert
        assertNotNull(response);

        verify(provider).getCategoryPromotions(any());
    }

    @Test
    void givenValidDataWhenCategoryPointsLevels() throws IOException {
        //Arrange
        LoyaltyGetLevelResponse expectedResponse = LoyaltySEResponseFixture.withDefaultLoyaltyGetLevel();
        List<LoyaltyGetLevelResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        when(provider.getCategoryPointsLevels(any())).thenReturn(expectedListResponse);

        //Act
        List<LoyaltyLevelResponse> response = service.getCategoryPointsLevels("123");

        assertNotNull(response);
        verify(provider).getCategoryPointsLevels(any());
        verify(mapper).convertLevels(expectedListResponse);
    }

    @Test
    void givenEmptyCategoryPointsLevelsListWhenCategoryPointsLevelsThenReturnsEmpty() throws IOException {
        // Arrange
        List<LoyaltyGetLevelResponse> emptyResponse = Collections.emptyList();

        when(provider.getCategoryPointsLevels(any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyLevelResponse> response = service.getCategoryPointsLevels("123");

        // Assert
        assertNotNull(response);

        verify(provider).getCategoryPointsLevels(any());
        verify(mapper).convertLevels(emptyResponse);
    }

    @Test
    void givenNullCategoryPointsLevelsListWhenCategoryPointsLevelsThenReturnsEmpty() throws IOException {
        // Arrange
        when(provider.getCategoryPointsLevels(any())).thenReturn(null);

        // Act
        List<LoyaltyLevelResponse> response = service.getCategoryPointsLevels("123");

        // Assert
        assertNotNull(response);

        verify(provider).getCategoryPointsLevels(any());
    }

    @Test
    void givenValidDataWhenTermsConditions() throws IOException{
        // Arrange
        LoyaltyGetTermsConditionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultTermsConditions();

        when(provider.termsConditions(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyTermsConditionsResponse response = service.termsConditions("1234");

        assertNotNull(response);
        verify(provider).termsConditions(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGetTermsConditionsResponse.class));
    }

    @Test
    void givenValidDataWhenCheckFlowTrue() throws IOException {
        //Arrange
        LoyaltyStatusResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscription();
        when(provider.checkFlow(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.checkFlow("1234");

        assertNotNull(response);
        assertEquals(LoyaltyResponse.VALIDATE_PROGRAM.getMessage(), response.getMessage());
        verify(provider).checkFlow(any(), any());
    }

    @Test
    void givenValidDataWhenCheckFlowFalse() throws IOException {
        //Arrange
        LoyaltyStatusResponse expectedResponse = LoyaltySEResponseFixture.withDefaultSubscriptionFalse();
        when(provider.checkFlow(any(), any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.checkFlow("123")
        );
        //Assert
        assertEquals("NOT_VALIDATE_PROGRAM", exception.getCode());
        verify(provider).checkFlow(any(), any());
    }

    @Test
    void givenValidDataWhenPromotions() throws IOException{
        // Arrange
        LoyaltyGetPromotionResponse expectedResponse = LoyaltySEResponseFixture.withDefaultPromotion();

        when(provider.getPromotions(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyPromotionResponse response = service.getPromotions("1234", "1234");

        assertNotNull(response);
        verify(provider).getPromotions(any(), any());
        verify(mapper).convertResponse(any(LoyaltyGetPromotionResponse.class));
    }

    @Test
    void givenNullResponseWhenPromotionsThenReturnNull() throws IOException {
        // Arrange
        when(provider.getPromotions(any(), any())).thenReturn(null);

        // Act
        LoyaltyPromotionResponse response = service.getPromotions("1234", "1234");

        // Assert
        assertNull(response);
        verify(provider).getPromotions(any(), any());
        verify(mapper).convertResponse((LoyaltyGetPromotionResponse) isNull());
    }
    @Test
    void givenPromotionWithoutImageWhenConvertResponseThenImageIsNull() {
        // Arrange
        LoyaltyGetPromotionResponse responseWithoutImage = LoyaltySEResponseFixture.withDefaultPromotionNull();

        // Act
        LoyaltyPromotionResponse result = mapper.convertResponse(responseWithoutImage);

        // Assert
        assertNotNull(result);
        assertNull(result.getImage());
    }

}
