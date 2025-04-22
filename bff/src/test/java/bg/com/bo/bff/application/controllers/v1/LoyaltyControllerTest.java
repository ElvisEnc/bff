package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyRequestFixture;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyResponseFixture;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoyaltyControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private LoyaltyController controller;
    @Mock
    private ILoyaltyService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenPersonIdWhenGetSystemCodeThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltySystemCodeResponse responseExpected = LoyaltyResponseFixture.withDefaultSystemCode();
        when(service.getSystemCode(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getSystemCode(any());
    }

    @Test
    void givenPersonIdWhenGetSumPointThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyPointResponse responseExpected = LoyaltyResponseFixture.withDefaultSumPoint();
        when(service.getSumPoint(any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code/{codeSystem}/sum-points";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123", "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getSumPoint(any(), any());
    }

    @Test
    void givenPersonIdWhenRegisterSubscriptionThenReturnSuccess() throws Exception {
        // Arrange
        RegisterSubscriptionRequest request = LoyaltyRequestFixture.withDefaultRegisterSubscription();
        GenericResponse responseExpected = LoyaltyResponseFixture.withDefaultGeneric();
        when(service.registerSubscription(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/accounts/{accountId}/register-subscription";
        MvcResult result = mockMvc.perform(post(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).registerSubscription(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenRedeemVoucherThenReturnSuccess() throws Exception {
        // Arrange
        RegisterRedeemVoucherRequest request = LoyaltyRequestFixture.withDefaultRegisterRedeemVoucher();
        LoyaltyRedeemVoucherResponse responseExpected = LoyaltyResponseFixture.withDefaultRedeemVoucher();
        when(service.registerRedeemVoucher(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code/{codeSystem}/redeem-voucher";
        MvcResult result = mockMvc.perform(post(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).registerRedeemVoucher(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenGetLevelThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyLevelResponse responseExpected = LoyaltyResponseFixture.withDefaultLevel();
        when(service.getLevel(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/level";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getLevel(any());
    }

    @Test
    void givenPersonIdWhenGetInitialPointsVAMOSThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyInitialPointsResponse responseExpected = LoyaltyResponseFixture.withDefaultInitialPoints();
        when(service.getInitialPointsVAMOS(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/initial-points-vamos";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getInitialPointsVAMOS(any());
    }

    @Test
    void givenPersonIdWhenGetPointsPeriodThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyPointResponse responseExpected = LoyaltyResponseFixture.withDefaultSumPoint();
        when(service.getPointsPeriod(any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code/{codeSystem}/points-period";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getPointsPeriod(any(), any());
    }

    @Test
    void givenPersonIdWhenVerifySubscriptionThenReturnSuccess() throws Exception {
        // Arrange
        GenericResponse responseExpected = LoyaltyResponseFixture.withDefaultGeneric();
        when(service.verifySubscription(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/verify-subscription";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).verifySubscription(any());
    }

    @Test
    void givenPersonIdWhenStatementPointsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyStatementRequest request = LoyaltyRequestFixture.withDefaultStatement();
        LoyaltyStatementResponse responseExpected = LoyaltyResponseFixture.withDefaultStatement();
        List<LoyaltyStatementResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.statementPoints(any(), any(), any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code/{codeSystem}/statement-points";
        MvcResult result = mockMvc.perform(post(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).statementPoints(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenGetGeneralInformationThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyGeneralInfoResponse responseExpected = LoyaltyResponseFixture.withDefaultGeneralInfo();
        when(service.getGeneralInformation(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/general-information";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getGeneralInformation(any());
    }

    @Test
    void givenPersonIdWhenGetImageInformationThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyImageResponse responseExpected = LoyaltyResponseFixture.withDefaultImage();
        when(service.getImageInformation(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/image/{imageId}/image-information";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getImageInformation(any());
    }

    @Test
    void givenPersonIdWhenGetImagesInformationThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyImageRequest request = LoyaltyRequestFixture.withDefaultImage();
        LoyaltyImageResponse responseExpected = LoyaltyResponseFixture.withDefaultImage();
        List<LoyaltyImageResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getImagesInformation(any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/images-information";
        MvcResult result = mockMvc.perform(post(urlLoyalty)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getImagesInformation(any());
    }

    @Test
    void givenPersonIdWhenGetCategoryPromotionThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyCategoryPromotionResponse responseExpected = LoyaltyResponseFixture.withDefaultCategoryPromotion();
        List<LoyaltyCategoryPromotionResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getCategoryPromotions(any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/category-promotions";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getCategoryPromotions(any());
    }

    @Test
    void givenPersonIdWhenGetCategoryPointsLevelsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyLevelResponse responseExpected = LoyaltyResponseFixture.withDefaultLevel();
        List<LoyaltyLevelResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getCategoryPointsLevels(any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/category-points-level";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getCategoryPointsLevels(any());
    }

    @Test
    void givenPersonIdWhenTermsConditionsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyTermsConditionsResponse responseExpected = LoyaltyResponseFixture.withDefaultTermsConditions();
        when(service.termsConditions(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/terms-conditions";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).termsConditions(any());
    }

    @Test
    void givenPersonIdWhenCheckFlowThenReturnSuccess() throws Exception {
        // Arrange
        GenericResponse responseExpected = LoyaltyResponseFixture.withDefaultGeneric();
        when(service.checkFlow(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/check-flow";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).checkFlow(any());
    }

    @Test
    void givenPersonIdWhenPromotionsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyPromotionResponse responseExpected = LoyaltyResponseFixture.withDefaultPromotion();
        when(service.getPromotions(any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/promotion/{promotionId}/promotion";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getPromotions(any(), any());
    }

    @Test
    void givenPersonIdWhenStoreFeaturedThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyStoreFeaturedResponse responseExpected = LoyaltyResponseFixture.withDefaultStoreFeatured();
        List<LoyaltyStoreFeaturedResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getStoreFeatured(any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/store-featured";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getStoreFeatured(any());
    }

    @Test
    void givenPersonIdWhenQRTransactionsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyGenericVoucherTransactionResponse responseExpected = LoyaltyResponseFixture.withDefaultQrTransactions();
        when(service.getQRTransactions(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/voucher/{voucherId}/type/{typeVoucher}/qr-transactions";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123","123", "CONSUMO")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getQRTransactions(any(), any(), any());
    }


    @Test
    void givenPersonIdWhenVoucherTransactionsThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyVoucherTransactionsResponse responseExpected = LoyaltyResponseFixture.withDefaultVoucherTransactions();
        List<LoyaltyVoucherTransactionsResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getVoucherTransactions(any(), any(), any())).thenReturn(responseListExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/system-code/{codeSystem}/status/{status}/voucher-transactions";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123","123", "VIGENTE")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getVoucherTransactions(any(), any(), any());
    }



    @Test
    void givenPersonIdWhenGetTradeCategoriesThenReturnSuccess() throws Exception {
        // Arrange
        LoyaltyTradeCategoryListResponse responseExpected = LoyaltyResponseFixture.withDefaultTradeCategories();
        when(service.getTradeCategories(any())).thenReturn(responseExpected);

        // Act
        String urlLoyalty = "/api/v1/loyalty/persons/{personId}/trade-categories";
        MvcResult result = mockMvc.perform(get(urlLoyalty, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getTradeCategories(any());
    }
}
