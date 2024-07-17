package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.request.own.account.AccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountResponseFixture;
import bg.com.bo.bff.application.dtos.response.own.account.GetTransactionLimitResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.application.dtos.response.own.account.Account;
import bg.com.bo.bff.application.dtos.response.own.account.AccountListResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AddAccountResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OwnAccountControllerTest {

    private static final String GET_ACCOUNTS_URL = "/api/v1/accounts/persons/{personId}/document-number/{document}";
    private static final String GET_ACCOUNTS_ONLY_PERSON_ID_URL = "/api/v1/accounts/persons/{personId}";
    private static final String TRANSACTION_LIMIT_UPDATE_URL = "/api/v1/accounts/persons/{personId}/account/{accountId}/transactional-limits";
    private static final String TRANSACTION_LIMIT_GET_URL = "/api/v1/accounts/persons/{personId}/account/{accountId}/transactional-limits";

    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private OwnAccountController controller;

    @Mock
    private IAccountService service;

    @Mock
    private HttpServletRequest httpServletRequest;

    private Enumeration<String> enumerations;

    private ObjectMapper objectMapper;
    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "127.0.0.1",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0",
                DeviceMW.USER_DEVICE_ID.getCode(), "129"
        );

        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        this.enumerations = lists.elements();

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        headers.add("topaz-channel", "2");
        headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
        headers.add(DeviceMW.USER_DEVICE_ID.getCode(), "129");
    }

    @Test
    void givenPersonIdWhenGetAccountsThenResponseEntityOkAccountListResponse() throws Exception {
        // Arrange
        String personId = "123456";
        String documentNumber = "1234";
        AccountListResponse expected = new AccountListResponse();
        Account account = new Account();
        List<Account> list = new ArrayList<>();
        list.add(account);
        expected.setData(list);

        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        when(service.getAccounts(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_ACCOUNTS_URL, personId, documentNumber)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getAccounts(any(), any());
    }

    @Test
    void givenOnlyPersonIdWhenGetAccountsThenResponseEntityOkAccountListResponse() throws Exception {
        // Arrange
        String personId = "123456";
        AccountListResponse expected = new AccountListResponse();
        Account account = new Account();
        List<Account> list = new ArrayList<>();
        list.add(account);
        expected.setData(list);

        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        when(service.getAccounts(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_ACCOUNTS_ONLY_PERSON_ID_URL, personId)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getAccounts(any(), any());
    }

    @Test
    void givenPersonAndIdAccountWhenGetTransactionLimitThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "123456";
        String accountId = "123456";
        GenericResponse expected = GenericResponse.instance(AddAccountResponse.SUCCESS);
        UpdateTransactionLimitRequest request = AccountRequestFixture.withDefaultUpdateTransactionLimitRequest();
        when(service.updateTransactionLimit(any(), any(), any(), any())).thenReturn(expected);
        String json = objectMapper.writeValueAsString(request);

        // Act
        MvcResult result = mockMvc
                .perform(put(TRANSACTION_LIMIT_UPDATE_URL, personId, accountId)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).updateTransactionLimit(any(), any(), any(), any());
    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitResponse() throws Exception {
        // Arrange
        String personId = "123456";
        String accountId = "123456";
        GetTransactionLimitResponse expected = AccountResponseFixture.withDefaultGetTransactionLimitResponse();
        UpdateTransactionLimitRequest request = AccountRequestFixture.withDefaultUpdateTransactionLimitRequest();
        when(service.getTransactionLimit(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(get(TRANSACTION_LIMIT_GET_URL, personId, accountId)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getTransactionLimit(any(), any(), any());
    }
}
