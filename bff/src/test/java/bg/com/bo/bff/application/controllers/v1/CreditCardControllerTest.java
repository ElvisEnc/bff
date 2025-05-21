package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.credit.card.AuthorizationCreditCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.BlockCreditCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.CashAdvanceRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.CreditCardRequestFixture;
import bg.com.bo.bff.application.dtos.request.credit.card.CreditCardStatementRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.FeePrepaidCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.PayCreditCardRequest;
import bg.com.bo.bff.application.dtos.response.credit.card.AvailableCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CashAdvanceFeeResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CashAdvanceResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CreditCardResponseFixture;
import bg.com.bo.bff.application.dtos.response.credit.card.CreditCardStatementsResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.DetailCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.DetailPrepaidCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.FeePrepaidCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.LinkserCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.ListCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PayCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PeriodCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PurchaseAuthResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.ICreditCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreditCardControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private CreditCardController controller;
    @Mock
    private ICreditCardService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenPersonCodeWhenGetListCreditCardThenListCreditCardResponse() throws Exception {
        // Arrange
        ListCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultListCreditCardResponse();
        when(service.getListCard(any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}";
        MvcResult result = mockMvc.perform(get(path, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getListCard(any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenGetDetailCreditCardThenDetailResponse() throws Exception {
        // Arrange
        DetailCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultDetailCreditCardResponse();
        when(service.getDetailsCreditCard(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}";
        MvcResult result = mockMvc.perform(get(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getDetailsCreditCard(any(), any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenGetDetailPrepaidCardThenDetailResponse() throws Exception {
        // Arrange
        DetailPrepaidCardResponse expectedResponse = CreditCardResponseFixture.withDefaultDetailPrepaidCardResponse();
        when(service.getDetailsPrepaidCard(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/prepaid";
        MvcResult result = mockMvc.perform(get(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getDetailsPrepaidCard(any(), any());
    }

    @Test
    void givenRequestBlockCreditCardWhenBlockCreditCardThenResponseExpected() throws Exception {
        // Arrange
        BlockCreditCardRequest requestMock = CreditCardRequestFixture.withDefaultBlockCreditCardRequest();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        when(service.blockCreditCard(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/lock-status";
        MvcResult result = mockMvc.perform(patch(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).blockCreditCard(any(), any(), any());
    }

    @Test
    void givenCmsCardWhenGetAvailableCreditCardThenResponseExpected() throws Exception {
        // Arrange
        AvailableCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultAvailableCreditCardResponse();
        when(service.getAvailable(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/available?cmsCard={cmsCard}";
        MvcResult result = mockMvc.perform(get(path, "123", "123", "13-01-10-020136")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getAvailable(any(), any(), any());
    }

    @Test
    void givenPersonIdGetPeriodsCreditCardThenResponseExpected() throws Exception {
        // Arrange
        List<PeriodCreditCardResponse> expectedResponse = CreditCardResponseFixture.withDefaultPeriods();
        when(service.getPeriods(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/payment-periods";
        MvcResult result = mockMvc.perform(get(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getPeriods(any(), any());
    }

    @Test
    void givenCmsAccountNumberAndAmountCardWhenGetCashAdvanceFeeThenResponseExpected() throws Exception {
        // Arrange
        CashAdvanceFeeResponse expectedResponse = CreditCardResponseFixture.createCashAdvanceFeeResponse();
        when(service.getCashAdvanceFee(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/cash-advance/fee?cmsAccount={cmsAccount}&amount={amount}";
        MvcResult result = mockMvc.perform(get(path, "123", "13-01-10-034599", 100.5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getCashAdvanceFee(any(), any(), any());
    }

    @Test
    void givenPersonIdAndCmsAccountWhenGetCreditCardsThenResponseExpected() throws Exception {
        // Arrange
        List<LinkserCreditCardResponse> expectedResponse = CreditCardResponseFixture.withDefaultLinkserCreditCardResponse();
        when(service.getCreditCards(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards?cmsAccount={cmsAccount}";
        MvcResult result = mockMvc.perform(get(path, "123", "13-01-10-000036")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getCreditCards(any(), any());
    }

    @Test
    void givenCashAdvanceRequestWhenMakeCashAdvanceThenResponseExpected() throws Exception {
        // Arrange
        CashAdvanceRequest requestMock = CreditCardRequestFixture.withDefaultCashAdvanceRequest();
        CashAdvanceResponse expectedResponse = CreditCardResponseFixture.withDefaultCashAdvanceResponse();
        when(service.makeCashAdvance(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/cash-advance";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).makeCashAdvance(any(), any(), any());
    }

    @Test
    void givenCreditCardRequestWhenGetStatementsThenResponseExpected() throws Exception {
        // Arrange
        CreditCardStatementRequest requestMock = CreditCardRequestFixture.withDefaultCreditCardStatementRequest();
        List<CreditCardStatementsResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultCreditCardStatementsResponse());
        when(service.creditCardStatements(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/statements";
        MvcResult result = mockMvc.perform(post(path, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));

        // Assert
        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).creditCardStatements(any(), any());
    }

    @Test
    void givenListPurchaseRequestWhenGetPurchasesAuthorizationsThenResponseExpected() throws Exception {
        // Arrange
        List<PurchaseAuthResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultPurchaseAuthResponse());
        when(service.getPurchasesAuthorizations(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/123/authorizations?cmsCard=13-01-10-0000000001&type=L";
        MvcResult result = mockMvc.perform(get(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(ApiDataResponse.of(expectedResponse));

        // Assert
        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getPurchasesAuthorizations(any(), any(), any());
    }

    @Test
    void givenPayCreditCardRequestWhenPayCreditCardThenResponseExpected() throws Exception {
        // Arrange
        PayCreditCardRequest requestMock = CreditCardRequestFixture.defaultPayCreditCardRequest();
        PayCreditCardResponse expectedResponse = CreditCardResponseFixture.defaultPayCreditCardResponse();
        when(service.payCreditCard(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/accounts/{accountId}/payments";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).payCreditCard(any(), any(), any());
    }

    @Test
    void givenAuthorizationCreditCardCaseIRequestWhenAuthorizationCreditCardThenResponseExpected() throws Exception {
        // Arrange
        AuthorizationCreditCardRequest requestMock = CreditCardRequestFixture.withDefaultAuthorizationCreditCardRequest();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_AUTHORIZATION_ONLINE);
        when(service.authorizationCreditCard(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/authorizations";
        MvcResult result = mockMvc.perform(post(path, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).authorizationCreditCard(any(), any());
    }

    @Test
    void givenAuthorizationCreditCardCaseLRequestWhenAuthorizationCreditCardThenResponseExpected() throws Exception {
        // Arrange
        AuthorizationCreditCardRequest requestMock = CreditCardRequestFixture.withDefaultAuthorizationCreditCardRequest();
        requestMock.setRequestType("L");
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_AUTHORIZATION_ONLINE);
        when(service.authorizationCreditCard(any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/authorizations";
        MvcResult result = mockMvc.perform(post(path, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).authorizationCreditCard(any(), any());
    }

    @Test
    void givenAuthorizationCreditCardCaseErrorRequestWhenAuthorizationCreditCardThenResponseErrorExpected() throws Exception {
        // Arrange
        AuthorizationCreditCardRequest requestMock = CreditCardRequestFixture.withDefaultAuthorizationCreditCardRequest();
        requestMock.setRequestType("A");

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/authorizations";
        MvcResult result = mockMvc.perform(post(path, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        int status = result.getResponse().getStatus();

        assertNotNull(result);
        assertEquals(400, status);
    }

    @Test
    void givenComissionPrepaidCardRequestWhenComissionPrepaidCardThenResponseExpected() throws Exception {
        // Arrange
        FeePrepaidCardRequest requestMock = CreditCardRequestFixture.withDefaultComissionPrepaidCardRequest();
        FeePrepaidCardResponse expectedResponse = CreditCardResponseFixture.withDefaultComissionPrepaidCardResponse();
        when(service.getFeePrepaidCard(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/credit-cards/persons/{personId}/cards/{cardId}/prepaid/fee";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(expectedResponse);

        // Assert
        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(service).getFeePrepaidCard(any(), any(), any());
    }
}