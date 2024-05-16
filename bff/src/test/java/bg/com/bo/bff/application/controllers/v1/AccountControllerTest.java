package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.GetTransactionLimitResponse;
import bg.com.bo.bff.application.dtos.response.GetTransactionLimitResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.models.Account;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddAccountResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private static final String TRANSACTION_LIMIT_UPDATE_URL = "/api/v1/accounts/persons/{personId}/account/{accountId}/transactional-limits";
    private static final String TRANSACTION_LIMIT_GET_URL = "/api/v1/accounts/persons/{personId}/account/{accountId}/transactional-limits";

    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private AccountController controller;

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
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
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
        headers.add(DeviceMW.APP_VERSION.getCode(),"1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
    }

    @Test
    void givenPersonIdWhenGetAccountsThenResponseEntityOkAccountListResponse() throws IOException {
        // Arrange
        String personId = "123456";
        String documentNumber = "1234";
        AccountListResponse accountListResponse = new AccountListResponse();
        Account account = new Account();
        List<Account> list = new ArrayList<>();
        list.add(account);
        accountListResponse.setData(list);
        Mockito.when(service.getAccounts(personId, documentNumber)).thenReturn(accountListResponse);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);

            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            ResponseEntity<AccountListResponse> response = controller.accounts(personId, documentNumber);

            // Assert
            assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            assertNotNull(response.getBody());
        } catch (Exception e) {
            fail("An exception was thrown.");
        }
    }

    @Test
    void givenPersonAndIdAccountWhenGetTransactionLimitThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId="123456";
        String accountId="123456";
        GenericResponse expected = GenericResponse.instance(AddAccountResponse.SUCCESS);
        UpdateTransactionLimitRequest request = UpdateTransactionLimitRequestFixture.withDefault();
        when(service.updateTransactionLimit(any(), any(), any(), any())).thenReturn(expected);
        String json = objectMapper.writeValueAsString(request);

        // Act
        MvcResult result = mockMvc
                .perform(put(TRANSACTION_LIMIT_UPDATE_URL,personId,accountId)
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
        verify(service).updateTransactionLimit(any(),any(), any(), any());
    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitResponse() throws Exception {
        // Arrange
        String personId="123456";
        String accountId="123456";
        GetTransactionLimitResponse expected = GetTransactionLimitResponseFixture.withDefault();
        UpdateTransactionLimitRequest request = UpdateTransactionLimitRequestFixture.withDefault();
        when(service.getTransactionLimit(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(get(TRANSACTION_LIMIT_GET_URL,personId,accountId)
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
        verify(service).getTransactionLimit(any(),any(), any());
    }


}
