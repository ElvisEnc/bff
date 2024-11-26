package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.debit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.CreateAuthorizationOnlinePurchaseResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DebitCardControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private DebitCardController controller;
    @Mock
    private IDebitCardService service;
    @Mock
    private ObjectMapper objectMapper;

    private static final String GET_LIST_AUTHORIZATIONS = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/authorizations";
    private static final String CREATE_AUTHORIZATION_ONLINE_PURCHASE = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/authorizations";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenValidDataWhenChangeAmountThenThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        DCLimitsRequest request = DebitCardRequestFixture.withDefaultDCLimitsRequest();
        GenericResponse mockResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);
        when(service.changeAmount(any(), any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_CHANGE_AMOUNT = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/limits";
        mockMvc.perform(patch(URL_PATCH_CHANGE_AMOUNT, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        verify(service).changeAmount(any(), any(), any());
    }

    @Test
    void givenPersonCodeWhenGetListDebitCardThenSuccess() throws Exception {
        // Arrange
        ListDebitCardResponse responseExpected = DebitCardResponseFixture.withDefaultListDebitCardResponse();
        when(service.getListDebitCard(any())).thenReturn(responseExpected);

        // Act
        String GET_LIST_DEBIT_CARD = "/api/v1/debit-cards/persons/{personId}/cards";
        MvcResult result = mockMvc.perform(get(GET_LIST_DEBIT_CARD, "123")
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
        verify(service).getListDebitCard(any());
    }

    @Test
    void givenPersonIdAndCardIdWhenGetAccountListDebitCardThenSuccess() throws Exception {
        // Arrange
        ListAccountTDResponse responseExpected = DebitCardResponseFixture.withDefaultListAccountTDResponse();
        when(service.getAccountsTD(any(), any())).thenReturn(responseExpected);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/debit-cards/persons/{personId}/cards/{cardId}/accounts", "123", "123")
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
        verify(service).getAccountsTD(any(), any());
    }

    @Test
    void givenPersonIdAndCardIdWhenGetListAuthorizationsThenDCInternetAuthorizationNWResponse() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        InternetAuthorizationResponse expected = DebitCardResponseFixture.withDefaultInternetAuthorizationResponse();
        when(service.getListAuthorizations(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_LIST_AUTHORIZATIONS, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getListAuthorizations(any(), any());
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenErrorCreate() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        GenericResponse expected = GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.SUCCESS_CREATE);
        CreateAuthorizationOnlinePurchaseRequest request = DebitCardRequestFixture.withDefaultCreateAuthOnlinePurchaseRequest();
        when(service.createAuthorizationOnlinePurchase(any(), any(), any())).thenReturn(GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.SUCCESS_CREATE));

        // Act
        MvcResult result = mockMvc.perform(put(CREATE_AUTHORIZATION_ONLINE_PURCHASE, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();
        // Assert
        assertEquals(response, actual);
        verify(service).createAuthorizationOnlinePurchase(any(), any(), any());
    }

    @Test
    void givenPersonIdAndCardIdAndAuthIdWhenDeleteAuthThenSuccess() throws Exception {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_DELETE_AUTH_PURCHASE);
        when(service.deleteAuthOnlinePurchases(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String url = "/api/v1/debit-cards/persons/{personId}/cards/{carId}/authorizations/{authId}";
        MvcResult result = mockMvc.perform(delete(url, 123, 123, 123)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).deleteAuthOnlinePurchases(any(), any(), any());
    }

    @Test
    void givenPersonIdAndCardIdWhenActiveAssuranceThenSuccess() throws Exception {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        UpdateDebitCardAssuranceRequest request = DebitCardRequestFixture.withDefaultUpdateDebitCardAssuranceRequest();
        when(service.activeDebitCardAssurance(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String url = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/assurance";
        MvcResult result = mockMvc.perform(put(url, 123, 123)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).activeDebitCardAssurance(any(), any(), any());
    }

    @Test
    void givenPersonIdAndCardIdWhenActivateDebitCardThenSuccess() throws Exception {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVATE_DEBIT_CARD);
        ActivateDebitCardRequest request = DebitCardRequestFixture.withDefaultActivateDebitCardRequest();
        when(service.activateDebitCard(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String url = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/activate";
        MvcResult result = mockMvc.perform(post(url, 123, 123)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).activateDebitCard(any(), any(), any());
    }

    @Test
    void givenValidDataWhenDetailThenThenDCDetailResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        DCDetailResponse mockResponse = DebitCardResponseFixture.withDefaultDCDetailResponse();
        when(service.detail(any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_CHANGE_AMOUNT = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}";
        mockMvc.perform(get(URL_PATCH_CHANGE_AMOUNT, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        verify(service).detail(any(), any());
    }

    @Test
    void givenValidDataWhenLockStatusThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        DCLockStatusRequest request = DebitCardRequestFixture.withDefaultDCLockStatusRequest();
        GenericResponse mockResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        when(service.lockStatus(any(), any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_UPDATE_LOCK_STATUS = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/lock-status";
        MvcResult result = mockMvc.perform(patch(URL_PATCH_UPDATE_LOCK_STATUS, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(mockResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).lockStatus(any(), any(), any());
    }

    @Test
    void givenValidDataWhenModifyAccountsOrderThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        DCAccountsOrderRequest request = DebitCardRequestFixture.withDefaultDCAccountsOrderRequest();
        GenericResponse mockResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_MODIFY_ACCOUNTS_ORDER);
        when(service.modifyAccountsOrder(any(), any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_MODIFY_ORDER_ACCOUNTS = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/accounts";
        MvcResult result = mockMvc.perform(patch(URL_PATCH_MODIFY_ORDER_ACCOUNTS, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(mockResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).modifyAccountsOrder(any(), any(), any());
    }

    @Test
    void givenValidDataWhenChangePinCardThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        ChangePinRequest request = DebitCardRequestFixture.withDefaultChangePinRequest();
        GenericResponse mockResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_PIN_CARD);
        when(service.changePinCard(any(), any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_CHANGE_PIN_CARD = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/change-pin";
        MvcResult result = mockMvc.perform(patch(URL_PATCH_CHANGE_PIN_CARD, personId, cardId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(mockResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).changePinCard(any(), any(), any());
    }
}
