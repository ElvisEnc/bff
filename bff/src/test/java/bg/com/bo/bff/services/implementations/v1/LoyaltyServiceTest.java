package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyRequestFixture;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchant;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loyalty.LoyaltyMapper;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltySERequestFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyCityListAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyFeaturedMerchantAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGeneralInformationResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetCategoryPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetGenericTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetImageResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetInitialPointsVamosResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetLevelResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetPromotionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyMerchantCampaignVoucherAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyPostRegisterRedeemVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyQrTransactionAPIResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySEResponseFixture;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatementPointsResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyStatusResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTradeCategoryResponse;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
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
        LoyaltyPostRegisterRedeemVoucherResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();
        when(provider.registerRedeemVoucher(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyRedeemVoucherResponse response = service.registerRedeemVoucher("1234", "123", request);

        assertNotNull(response);
        verify(provider).registerRedeemVoucher(any(), any());
        verify(mapper).convertResponse(any(LoyaltyPostRegisterRedeemVoucherResponse.class));
    }

    @Test
    void givenNullInformationWhenRegisterRedeemVoucherThenRequestBuiltWithoutInformation() throws IOException {
        // Arrange
        RegisterRedeemVoucherRequest request = LoyaltySERequestFixture.withDefaultRegisterRedeemVoucherNull();
        LoyaltyPostRegisterRedeemVoucherResponse expectedResponse = LoyaltySEResponseFixture.withDefaultRegisterRedeemVoucher();

        when(provider.registerRedeemVoucher(any(), any())).thenReturn(expectedResponse);

        // Act
        LoyaltyRedeemVoucherResponse response = service.registerRedeemVoucher("5678", "SYS-002", request);

        // Assert
        assertNotNull(response);
        verify(provider).registerRedeemVoucher(any(), any());
        verify(mapper).convertResponse(any(LoyaltyPostRegisterRedeemVoucherResponse.class));
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

    @Test
    void givenValidDataWhenStoreFeatured() throws IOException {
        //Arrange
        LoyaltyGetStoreFeaturedResponse expectedResponse = LoyaltySEResponseFixture.withDefaultStoreFeatured();
        List<LoyaltyGetStoreFeaturedResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        when(provider.getStoreFeatured(any())).thenReturn(expectedListResponse);

        //Act
        List<LoyaltyStoreFeaturedResponse> response = service.getStoreFeatured("123");

        assertNotNull(response);
        verify(provider).getStoreFeatured(any());
        verify(mapper).convertStoreFeatured(expectedListResponse);
    }

    @Test
    void givenEmptyStoreFeaturedListWhenStoreFeaturedThenReturnsEmpty() throws IOException {
        // Arrange
        List<LoyaltyGetStoreFeaturedResponse> emptyResponse = Collections.emptyList();

        when(provider.getStoreFeatured(any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyStoreFeaturedResponse> response = service.getStoreFeatured("123");

        // Assert
        assertNotNull(response);

        verify(provider).getStoreFeatured(any());
        verify(mapper).convertStoreFeatured(emptyResponse);
    }

    @Test
    void givenStoreFeaturedWhenConvertResponseThenCategoryIsNull() {
        // Arrange
        LoyaltyGetStoreFeaturedResponse expectedResponse = LoyaltySEResponseFixture.withDefaultStoreFeaturedNull();
        List<LoyaltyGetStoreFeaturedResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        // Act
        List<LoyaltyStoreFeaturedResponse> result = mapper.convertStoreFeatured(expectedListResponse);

        // Assert
        assertNotNull(result);
    }

    @Test
    void givenQRTransactionsWhenPromotions() throws IOException{
        // Arrange
        LoyaltyGetGenericTransactionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultQrTransactions();

        when(provider.getQRTransactions(any(), any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyGenericVoucherTransactionResponse response = service.getQRTransactions("1234", "1234", "123");

        assertNotNull(response);
        verify(provider).getQRTransactions(any(), any(), any());
        verify(mapper).convertVoucherQrTransaction(any(LoyaltyGetGenericTransactionsResponse.class));
    }

    @Test
    void givenVoucherQrTransactionsWhenConvertResponseThenVoucherIsNull() {
        // Arrange
        LoyaltyGetGenericTransactionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultVoucherQrNull();

        // Act
        LoyaltyGenericVoucherTransactionResponse result = mapper.convertVoucherQrTransaction(expectedResponse);

        // Assert
        assertNotNull(result);
    }

    @Test
    void givenNullResponseWhenConvertVoucherQrTransactionThenReturnNull() {
        // Act
        LoyaltyGenericVoucherTransactionResponse result = mapper.convertVoucherQrTransaction(null);

        // Assert
        assertNull(result);
    }


    @Test
    void givenValidDataWhenVoucherTransactions() throws IOException {
        //Arrange
        LoyaltyGetTransactionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultTransactions();
        List<LoyaltyGetTransactionsResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        when(provider.getVoucherTransactions(any(), any(), any())).thenReturn(expectedListResponse);

        //Act
        List<LoyaltyVoucherTransactionsResponse> response = service.getVoucherTransactions("123", "123", "123");

        assertNotNull(response);
        verify(provider).getVoucherTransactions(any(), any(), any());
        verify(mapper).convertVoucherTransaction(expectedListResponse);
    }

    @Test
    void givenEmptyVoucherTransactionsListWhenVoucherTransactionsThenReturnsEmpty() throws IOException {
        // Arrange
        List<LoyaltyGetTransactionsResponse> emptyResponse = Collections.emptyList();

        when(provider.getVoucherTransactions(any(), any(), any())).thenReturn(emptyResponse);

        // Act
        List<LoyaltyVoucherTransactionsResponse> response = service.getVoucherTransactions("123", "123", "123");

        // Assert
        assertNotNull(response);

        verify(provider).getVoucherTransactions(any(), any(), any());
        verify(mapper).convertVoucherTransaction(emptyResponse);
    }

    @Test
    void givenVoucherTransactionsWhenConvertResponseThenVoucherTransactionsIsNull() {
        // Arrange
        List<LoyaltyGetTransactionsResponse> expectedListResponse = null;

        // Act
        List<LoyaltyVoucherTransactionsResponse> result = mapper.convertVoucherTransaction(expectedListResponse);

        // Assert
        assertNotNull(result);
    }

    @Test
    void givenVoucherTransactionsWhenConvertResponseThenVoucherIsNull() {
        // Arrange
        LoyaltyGetTransactionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultVoucherNull();
        List<LoyaltyGetTransactionsResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        // Act
        List<LoyaltyVoucherTransactionsResponse> result = mapper.convertVoucherTransaction(expectedListResponse);

        // Assert
        assertNotNull(result);
    }

    @Test
    void givenVoucherTransactionsWhenConvertResponseThenStoreIsNull() {
        // Arrange
        LoyaltyGetTransactionsResponse expectedResponse = LoyaltySEResponseFixture.withDefaultStoreNull();
        List<LoyaltyGetTransactionsResponse> expectedListResponse = new ArrayList<>();
        expectedListResponse.add(expectedResponse);

        // Act
        List<LoyaltyVoucherTransactionsResponse> result = mapper.convertVoucherTransaction(expectedListResponse);

        // Assert
        assertNotNull(result);
    }

    @Test
    void givenValidDataWhenGetTradeCategories() throws IOException {
        //Arrange
        List<LoyaltyGetTradeCategoryResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultLoyaltyGetTradeCategories();
        when(provider.getTradeCategories(any(), any())).thenReturn(expectedResponse);

        //Act
        LoyaltyTradeCategoryListResponse response = service.getTradeCategories("1234");

        assertNotNull(response);
        verify(provider).getTradeCategories(any(), any());
    }

    @Test
    void givenNullApiResponseWhenConvertResponseTradeCategoryThenReturnEmptyList() {
        // Act
        List<LoyaltyTradeCategoryResponse> result = mapper.convertResponseTradeCategory(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidDataGetFeaturedMerchants() throws IOException {
        //Arrange
        List<LoyaltyFeaturedMerchantAPIResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultFeaturedMerchants();
        when(provider.getFeaturedMerchant(any(), any())).thenReturn(expectedResponse);

        //Act

        LoyaltyFeaturedMerchantListResponse response = service.getFeaturedMerchants("1234");

        assertNotNull(response);
        verify(provider).getFeaturedMerchant(any(), any());
    }

    @Test
    void givenNullApiResponseWhenConvertResponseFeaturedMerchantThenReturnEmptyList() {
        // Act
        List<LoyaltyFeaturedMerchant> result = mapper.convertResponseFeaturedMerchant(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void givenValidDataWhenGetCityList() throws IOException {
        //Arrange
        List<LoyaltyCityListAPIResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultGetCityList();
        when(provider.getCityList(any(), any())).thenReturn(expectedResponse);

        //Act

        LoyaltyCityListResponse response = service.getCityList("1234");

        assertNotNull(response);
        verify(provider).getCityList(any(), any());
    }

    @Test
    void givenNullApiResponseWhenConvertResponseCityThenReturnEmptyList() {
        // Act
        List<LoyaltyCityListResponse.LoyaltyCity> result = mapper.convertResponseCity(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidDataWhenGetCityCategoryMerchants() throws IOException {
        //Arrange
        List<LoyaltyFeaturedMerchantAPIResponse> expectedResponse = LoyaltySEResponseFixture.withDefaultGetCityCategoryMerchants();
        when(provider.getCityCategoryMerchants(any(), any())).thenReturn(expectedResponse);

        //Act

        LoyaltyFeaturedMerchantListResponse response = service.getCityCategoryMerchants(
                "1234",
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                UUID.fromString("3ffa6099-b116-43eb-be81-9cea46d0c542")
        );

        assertNotNull(response);
        verify(provider).getCityCategoryMerchants(any(), any());
    }

    @Test
    void getVoucherDetailShouldReturnExpectedResponse() throws IOException {
        //Arrange
        LoyaltyQrTransactionAPIResponse expectedResponse = LoyaltySEResponseFixture.withDefaultGetVoucherDetail();
        when(provider.getVoucherDetail(any(), any(), any())).thenReturn(expectedResponse);

        //Act

        LoyaltyQrTransactionResponse response = service.getVoucherDetail(
                "1234",
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                "54545"
        );

        assertNotNull(response);
        verify(provider).getVoucherDetail(any(), any(), any());
    }

    @Test
    void testGetMerchantCampaignVouchers_success() throws IOException {
        LoyaltyMerchantCampaignVoucherAPIResponse expectedResponse = LoyaltySEResponseFixture.withDefaultGetCampaignVouchers();
        when(provider.getMerchantCampaignVouchers(any(), any())).thenReturn(expectedResponse);

        //Act

        LoyaltyMerchantVoucherCategoryResponse response = service.getMerchantCampaignVouchers(
                "1234",
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                1);

        assertNotNull(response);
        verify(provider).getMerchantCampaignVouchers(any(), any());

    }

    @Test
    void givenNullVouchersWhenGetMerchantCampaignVouchersThenReturnEmptyArrays() throws IOException {
        // Arrange
        LoyaltyMerchantCampaignVoucherAPIResponse apiResponse = new LoyaltyMerchantCampaignVoucherAPIResponse();
        apiResponse.setRedemptionVoucher(null);
        apiResponse.setTravelVoucher(null);
        apiResponse.setProductVoucher(null);
        apiResponse.setDiscountVoucher(null);

        when(provider.getMerchantCampaignVouchers(any(), any())).thenReturn(apiResponse);

        // Act
        LoyaltyMerchantVoucherCategoryResponse response = service.getMerchantCampaignVouchers(
                "1234",
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                UUID.fromString("1b528e2e-2846-4808-bdf1-b55331a1e112"),
                1
        );

        // Assert
        assertNotNull(response);
        assertNotNull(response.getRedemptionVoucher());
        assertEquals(0, response.getRedemptionVoucher().length);
        assertNotNull(response.getTravelVoucher());
        assertEquals(0, response.getTravelVoucher().length);
        assertNotNull(response.getProductVoucher());
        assertEquals(0, response.getProductVoucher().length);
        assertNotNull(response.getDiscountVoucher());
        assertEquals(0, response.getDiscountVoucher().length);
    }

    @Test
    void testGetVoucherTransactedList_success() throws IOException {
        List<LoyaltyPostRegisterRedeemVoucherResponse> expectedResponse = LoyaltySEResponseFixture
                .withDefaultGetVoucherTransactedList();
        when(provider.getVoucherTransactedList(anyMap(), anyString(), anyInt(), anyString())).thenReturn(expectedResponse);

        //Act

        LoyaltyVoucherTransactedListResponse response = service.getVoucherTransactedList(
                "1234",
                123132,
                "1"
        );

        assertNotNull(response);
        verify(provider).getVoucherTransactedList(anyMap(), anyString(), anyInt(), anyString());
    }

    @Test
    void givenNullApiResponseWhenConvertVoucherTransactedListResponseThenReturnEmptyList() {
        // Act
        List<LoyaltyRedeemVoucherResponse> result = mapper.convertVoucherTransactedListResponse(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
