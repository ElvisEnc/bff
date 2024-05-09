package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddAchAccountRequestFixture;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequestFixture;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequestFixture;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.*;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddAccountResponse;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddThirdAccountResponse;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DestinationAccountControllerTest {
    private static final String URL_THIRD = "/api/v1/destination-accounts/1234567/third-accounts";
    private static final String URL_WALLET = "/api/v1/destination-accounts/1234567/wallets";
    private static final String URL_ACH = "/api/v1/destination-accounts/1234567/ach-accounts";
    private static final String DELETE_THIRD_ACCOUNT = "/api/v1/destination-accounts/23/third-accounts/46/delete";
    private static final String DELETE_ACH_ACCOUNT = "/api/v1/destination-accounts/56/ach-accounts/46";
    private static final String DELETE_WALLET_ACCOUNT = "/api/v1/destination-accounts/123/wallets/456/wallet-accounts/789";
    private static final String GET_LIST_BANKS = "/api/v1/destination-accounts/banks";
    private static final String GET_ACCOUNT_TYPES = "/api/v1/destination-accounts/account-types";
    private static final String GET_BRANCH_OFFICE = "/api/v1/destination-accounts/banks/{bankCode}/branch-offices";
    private static final String GET_DESTINATION_ACCOUNT = "/api/v1/destination-accounts/persons/1020";
    private static final String GET_VALIDATE_ACCOUNT = "/api/v1/destination-accounts?accountNumber=79509515&clientName=Pa";
    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private DestinationAccountController controller;
    @Mock
    private IDestinationAccountService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;
    private HttpHeaders headers = new HttpHeaders();
    private Enumeration<String> enumerations;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        headers.add("topaz-channel", "2");

        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );

        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        this.enumerations = lists.elements();

        headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
    }

    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnOk() throws Exception {
        // Arrange
        GenericResponse expected = GenericResponse.instance(AddThirdAccountResponse.SUCCESS);
        AddThirdAccountRequest request = AddThirdAccountRequestFixture.withDefault();
        when(service.addThirdAccount(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(put(URL_THIRD)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).addThirdAccount(any(), any(), any());
    }

    @Test
    void givenValidaDataWhenAddAchAccountThenReturnOk() throws Exception {
        // Arrange
        GenericResponse expected = GenericResponse.instance(AddAccountResponse.SUCCESS);
        AddAchAccountRequest request = AddAchAccountRequestFixture.withDefault();
        when(service.addAchAccount(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(put(URL_ACH)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();
        // Assert
        assertEquals(response, actual);
        verify(service).addAchAccount(any(), any(), any());
    }

    @Test
    void givenValidDataWhenDeleteThirdAccountThenReturnOk() throws Exception {
        // Arrange
        String personId = "23";
        int identifier = 46;
        String ip = "127.0.0.1";
        String deviceId = "123";

        DeleteThirdAccountRequest request = new DeleteThirdAccountRequest();
        request.setAccountId(1);

        GenericResponse expected = new GenericResponse();

        when(service.delete(personId, identifier, deviceId, ip, request)).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(delete(DELETE_THIRD_ACCOUNT)
                        .header("device-id", deviceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        verify(service).delete(personId, identifier, deviceId, ip, request);
    }

    @Test
    void givenInvalidDataWhenDeleteThirdAccountThenReturnOk() throws Exception {
        // Arrange
        String deviceId = "123";
        DeleteThirdAccountRequest request = new DeleteThirdAccountRequest();
        request.setAccountId(0);

        // Act and Assert
        MvcResult result = mockMvc.perform(delete(DELETE_THIRD_ACCOUNT)
                        .header("device-id", deviceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void givenValidaDataWhenAddWalletdAccountThenReturnOk() throws Exception {
        // Arrange
        GenericResponse expected = GenericResponse.instance(AddThirdAccountResponse.SUCCESS);
        AddWalletAccountRequest request = AddWalletAccountRequestFixture.withDefault();

        when(service.addWalletAccount(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(put(URL_WALLET)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).addWalletAccount(any(), any(), any());
    }
    @Test
    void givenValidaDAtaWhenDeleteWalletAccountThenReturnOk() throws Exception {
        // Arrange
        String deviceId = "123";

        // Act and Assert
        MvcResult result = mockMvc.perform(delete(DELETE_WALLET_ACCOUNT)
                        .header("device-id", deviceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenValidDataWhenDeleteAchAccountThenReturnOk() throws Exception {
        // Arrange
        String personId = "56";
        int identifier = 46;
        String ip = "127.0.0.1";
        String deviceId = "123";

        GenericResponse expected = new GenericResponse();

        when(service.deleteAchAccount(personId, identifier, deviceId, ip)).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(delete(DELETE_ACH_ACCOUNT)
                        .header("device-id", deviceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        verify(service).deleteAchAccount(personId, identifier, deviceId, ip);
    }

    @Test
    void givenRequestWhenGetAccountTypesReturnOk() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get(GET_ACCOUNT_TYPES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        verify(service).accountTypes();
    }

    @Test
    void givenUrlGetBanksWhenGetBanksThenReturnList() throws Exception {
        // Arrange
        BanksResponse expected = BanksResponseFixture.withDefault();
        when(service.getBanks()).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(get(GET_LIST_BANKS)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getBanks();
    }

    @Test
    void givenBankCodeWhenGetBranchOfficeThenListBranchOffice() throws Exception {
        // Arrange
        Integer bankCode = 1017;
        BranchOfficeResponse mockResponse = BranchOfficeResponseFixture.withDefault();
        when(service.getBranchOffice(bankCode)).thenReturn(mockResponse);

        // Act
        mockMvc.perform(get(GET_BRANCH_OFFICE, bankCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].id").value(mockResponse.getData().get(0).getId()))
                .andExpect(jsonPath("$.data[0].description").value(mockResponse.getData().get(0).getDescription()));

        // Assert
        verify(service).getBranchOffice(bankCode);
    }

    @Test
    void givenPersonCodeWhenGetDestinationAccountsThenAllList() throws Exception {
        // Arrange
        DestinationAccountRequest requestMock = DestinationAccountRequestFixture.withDefault();
        DestinationAccountResponse responseExpected = DestinationAccountResponseFixture.withDefault();
        when(service.getDestinationAccounts(any(), any(), any())).thenReturn(responseExpected);

        // Act
        MvcResult result = mockMvc.perform(post(GET_DESTINATION_ACCOUNT)
                        .content(objectMapper.writeValueAsString(requestMock))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String response = objectMapper.writeValueAsString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getDestinationAccounts(any(), any(), any());
    }

    @Test
    void givenAccountNumberAndClientNameWhenGetValidateDestinationAccountThenValidateAccountResponse() throws Exception {
        // Arrange
        ValidateAccountResponse expected = ValidateAccountResponseFixture.withDefault();
        when(service.getValidateDestinationAccounts(any(),any(),any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc
                .perform(get(GET_VALIDATE_ACCOUNT)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getValidateDestinationAccounts(any(),any(),any());

    }

}