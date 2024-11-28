package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.destination.account.*;
import bg.com.bo.bff.application.dtos.response.destination.account.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.destination.account.AccountType;
import bg.com.bo.bff.mappings.providers.account.AchAccountsMapper;
import bg.com.bo.bff.mappings.providers.account.IThirdAccountsMapper;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AchAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.ThirdAccountMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountsMWResponse;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.mappings.providers.account.IAchAccountsMapper;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinationAccountServiceTest {
    @InjectMocks
    private DestinationAccountService service;
    @Mock
    private IThirdAccountProvider thirdAccountProvider;
    @Mock
    private IAchAccountProvider achAccountProvider;
    private final DestinationAccountServiceMapper mapper = DestinationAccountServiceMapper.INSTANCE;
    @Spy
    private IAchAccountsMapper iAchAccountsMapper = new AchAccountsMapper();
    @Mock
    private IThirdAccountsMapper thirdMapper;

    @BeforeEach
    void init() {
        service = new DestinationAccountService(thirdAccountProvider, achAccountProvider, mapper, iAchAccountsMapper, thirdMapper);
    }

    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddThirdAccountRequest request = DestinationAccountRequestFixture.withDefaultAddThirdAccountRequest();
        when(thirdAccountProvider.addThirdAccount(any(), any())).thenReturn(DestinationAccountResponseFixture.withDefaultAddAccountResponse());

        // Act
        AddAccountResponse response = service.addThirdAccount("1212", request, new HashMap<>());

        assertNotNull(response);
        verify(thirdAccountProvider).addThirdAccount(any(), any());

    }

    @Test
    void givenValidaDataWhenAddAchAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddAchAccountRequest request = DestinationAccountRequestFixture.withDefaultAddAchAccountRequest();
        when(achAccountProvider.addAchAccount(any(), any())).thenReturn(DestinationAccountResponseFixture.withDefaultAddAccountResponse());

        // Act
        AddAccountResponse response = service.addAchAccount("1212", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(achAccountProvider).addAchAccount(any(), any());
    }

    @Test
    void givenValidDataWhenAddQRAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);

        AddQRAccountRequest request = DestinationAccountRequestFixture.withDefaultAddQRRequest();
        AddAccountResponse expectedResponse = DestinationAccountResponseFixture.withDefaultAddAccountResponse();
        when(achAccountProvider.addAchAccount(any(), any())).thenReturn(expectedResponse);
        when(thirdAccountProvider.addWalletAccount(any(), any())).thenReturn(expectedResponse);
        when(thirdAccountProvider.addThirdAccount(any(), any())).thenReturn(expectedResponse);

        when(iAchAccountsMapper.mapToAchRequest("1212", request)).thenReturn(AchAccountMWRequestFixture.withDefaultOKAddAchAccountBasicRequest());
        when(thirdMapper.mapToThirdRequest("1212", request)).thenReturn(ThirdAccountMWRequestFixture.withDefaultOKAddThirdAccountBasicRequest());
        when(thirdMapper.mapToWalletRequest("1212", request)).thenReturn(ThirdAccountMWRequestFixture.withDefaultOKAddWalletAccountBasicRequest());

        // Act
        GenericResponse responseACH = service.addQRAccount("1212", "ACH", request, new HashMap<>());
        GenericResponse responseThird = service.addQRAccount("1212", "Third", request, new HashMap<>());
        GenericResponse responseWallet = service.addQRAccount("1212", "Wallet", request, new HashMap<>());

        // Assert
        assertNotNull(responseACH);
        assertNotNull(responseThird);
        assertNotNull(responseWallet);

        verify(achAccountProvider).addAchAccount(any(), any());
        verify(thirdAccountProvider).addThirdAccount(any(), any());
        verify(thirdAccountProvider).addWalletAccount(any(), any());
    }

    @Test
    void givenValidDataWhenDeleteAccountThenReturnOk() throws IOException {
        // Arrange
        long accountNumber = 1;
        String personId = "1";
        int identifier = 1;
        String deviceId = "1";
        String ip = "127.0.0.1";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Satisfactorio");
        when(thirdAccountProvider.deleteThirdAccount(any(), any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.deleteThirdAccount(personId, identifier, DeleteAccountRequest.builder().accountNumber(123456).build(), new HashMap<>());

        // Assert
        verify(thirdAccountProvider).deleteThirdAccount(any(), any());
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenValidaDataWhenAddWalletAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddWalletAccountRequest request = DestinationAccountRequestFixture.withDefaultAddWalletAccountRequest();
        when(thirdAccountProvider.addWalletAccount(any(), any())).thenReturn(DestinationAccountResponseFixture.withDefaultAddAccountResponse());

        // Act
        AddAccountResponse response = service.addWalletAccount("1212", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(thirdAccountProvider).addWalletAccount(any(), any());
    }

    @Test
    void givenValidaDataWhenDeleteWalletAccountThenReturnOk() throws IOException {
        // Arrange
        String personId = "1";
        int identifier = 1;

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Se borró la cuenta exitosamente.");
        when(thirdAccountProvider.deleteWalletAccount(any(), anyLong(), anyLong(), any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.deleteWalletAccount(personId, identifier, DeleteAccountRequest.builder().accountNumber(123456).build(), new HashMap<>());

        // Assert
        verify(thirdAccountProvider).deleteWalletAccount(any(), anyLong(), anyLong(), any());
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenValidDataWhenDeleteAchAccountThenReturnOk() throws IOException {
        // Arrange
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Se borró la cuenta exitosamente.");
        when(achAccountProvider.deleteAchAccount(any(), anyLong(), anyLong(), any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.deleteAchAccount("123", 123, DeleteAccountRequest.builder().accountNumber(123456).build(), new HashMap<>());

        // Assert
        verify(achAccountProvider).deleteAchAccount(any(), anyLong(), anyLong(), any());
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenWhenGetAccountTypesThenReturnList() {
        //Act
        AccountTypeListResponse response = service.accountTypes();

        //Assert
        Assertions.assertNotNull(response.getData());
        assertEquals(response.getData().size(), AccountType.values().length);
    }

    @Test
    void givenGetServiceBanksWhenGetBanksThenReturnList() throws Exception {
        // Arrange
        BanksMWResponse expected = AchAccountMWResponseFixture.withDefaultBanksMWResponse();
        when(achAccountProvider.getBanks(any())).thenReturn(expected);

        // Act
        BanksResponse actual = service.getBanks(new HashMap<>());

        // Assert
        assertEquals(expected.getData().size(), actual.getData().size());
        assertEquals(expected.getData().get(0).getCode(), actual.getData().get(0).getCode());
        assertEquals(expected.getData().get(0).getDescription(), actual.getData().get(0).getDescription());

        verify(achAccountProvider).getBanks(any());
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenReturnSuccess() throws IOException {
        // Arrange
        BranchOfficeMWResponse responseMWMock = AchAccountMWResponseFixture.withDefaultBranchOfficeMWResponse();
        BranchOfficeResponse expectedResponse = DestinationAccountResponseFixture.withDefaultBranchOfficeResponse();
        Mockito.when(achAccountProvider.getAllBranchOfficeBank(any(), any()))
                .thenReturn(responseMWMock);
        Mockito.when(iAchAccountsMapper.mapToBranchOfficeResponse(responseMWMock)).thenReturn(expectedResponse);

        // Act
        BranchOfficeResponse response = service.getBranchOffice("123", new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(achAccountProvider).getAllBranchOfficeBank(any(), any());
        verify(iAchAccountsMapper).mapToBranchOfficeResponse(responseMWMock);
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenReturnEmpty() throws IOException {
        // Arrange
        BranchOfficeMWResponse responseMWMock = BranchOfficeMWResponse.builder().data(null).build();
        BranchOfficeResponse expectedResponse = BranchOfficeResponse.builder().data(new ArrayList<>()).build();
        Mockito.when(achAccountProvider.getAllBranchOfficeBank(any(), any())).thenReturn(responseMWMock);

        // Act
        BranchOfficeResponse response = service.getBranchOffice("123", new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(achAccountProvider).getAllBranchOfficeBank(any(), any());
        verify(iAchAccountsMapper).mapToBranchOfficeResponse(responseMWMock);
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenReturnNull() throws IOException {
        // Arrange
        BranchOfficeResponse expectedResponse = BranchOfficeResponse.builder().data(new ArrayList<>()).build();
        Mockito.when(achAccountProvider.getAllBranchOfficeBank(any(), any())).thenReturn(null);

        // Act
        BranchOfficeResponse response = service.getBranchOffice("123", new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(achAccountProvider).getAllBranchOfficeBank(any(), any());
        verify(iAchAccountsMapper).mapToBranchOfficeResponse(any());
    }

    @Test
    void givenAccountNumberAndClientNameWhenGetValidateDestinationAccountThenValidateAccountResponse() throws Exception {
        // Arrange
        String accountNumber = "1310766620";
        String clientName = "BANCO";
        ValidateAccountResponse expected = DestinationAccountResponseFixture.withDefaultValidateAccountResponse();
        when(thirdAccountProvider.validateAccount(any(), any(), any())).thenReturn(expected);
        // Act

        ValidateAccountResponse acutal = service.getValidateDestinationAccounts(accountNumber, clientName, new HashMap<>());

        // Assert
        assertEquals(expected.getData(), acutal.getData());
        verify(thirdAccountProvider).validateAccount(any(), any(), any());
    }

    @Test
    void givenPersonIdAndParameterWhenGetListDestinationAccountThenReturnList() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();
        Boolean isInitial = true;
        String accessToken = "tokentoken";
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken(accessToken);

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        AchAccountsMWResponse achAccountsMWResponse = AchAccountMWResponseFixture.withDefaultAchAccountMWResponse();

        when(thirdAccountProvider.getThirdAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdAccountProvider.getWalletAccounts(personId, parameter)).thenReturn(responseExpected);
        when(achAccountProvider.getAchAccounts(personId, parameter)).thenReturn(achAccountsMWResponse);

        List<DestinationAccount> thirdDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        List<DestinationAccount> walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        List<DestinationAccount> achDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();

        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(thirdDestinationAccount);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);

        // Act
        List<DestinationAccount> result = service.getListDestinationAccount(personId, parameter, isInitial);

        // Assert
        assertEquals(3, result.size());
        verify(thirdAccountProvider).getThirdAccounts(personId, parameter);
        verify(thirdAccountProvider).getWalletAccounts(personId, parameter);
        verify(achAccountProvider).getAchAccounts(personId, parameter);
        verify(thirdMapper, times(2)).convertThirdAccountToDestinationAccount(any(), any(), any());
        verify(iAchAccountsMapper).convertAchAccountToDestinationAccount(any());
    }

    @Test
    void givenPersonIdAndParameterWhenGetListDestinationAccountThenReturnListEmpty() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();
        Boolean isInitial = true;
        String accessToken = "tokentoken";
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken(accessToken);

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        AchAccountsMWResponse achAccountsMWResponse = AchAccountsMWResponse.builder().data(null).build();

        when(thirdAccountProvider.getThirdAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdAccountProvider.getWalletAccounts(personId, parameter)).thenReturn(responseExpected);
        when(achAccountProvider.getAchAccounts(personId, parameter)).thenReturn(achAccountsMWResponse);

        List<DestinationAccount> thirdDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        List<DestinationAccount> walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();

        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(thirdDestinationAccount);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);

        // Act
        List<DestinationAccount> result = service.getListDestinationAccount(personId, parameter, isInitial);

        // Assert
        assertEquals(2, result.size());
        verify(thirdAccountProvider).getThirdAccounts(personId, parameter);
        verify(thirdAccountProvider).getWalletAccounts(personId, parameter);
        verify(achAccountProvider).getAchAccounts(personId, parameter);
        verify(thirdMapper, times(2)).convertThirdAccountToDestinationAccount(any(), any(), any());
        verify(iAchAccountsMapper).convertAchAccountToDestinationAccount(any());
    }


    @Test
    void givenPersonIdAndParameterWhenGetListDestinationAccountThenReturnListNull() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();
        Boolean isInitial = true;
        String accessToken = "tokentoken";
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken(accessToken);

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        AchAccountsMWResponse achAccountsMWResponse = AchAccountsMWResponse.builder().data(null).build();

        when(thirdAccountProvider.getThirdAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdAccountProvider.getWalletAccounts(personId, parameter)).thenReturn(responseExpected);
        when(achAccountProvider.getAchAccounts(personId, parameter)).thenReturn(null);

        List<DestinationAccount> thirdDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        List<DestinationAccount> walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();

        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(thirdDestinationAccount);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);

        // Act
        List<DestinationAccount> result = service.getListDestinationAccount(personId, parameter, isInitial);

        // Assert
        assertEquals(2, result.size());
        verify(thirdAccountProvider).getThirdAccounts(personId, parameter);
        verify(thirdAccountProvider).getWalletAccounts(personId, parameter);
        verify(achAccountProvider).getAchAccounts(personId, parameter);
        verify(thirdMapper, times(2)).convertThirdAccountToDestinationAccount(any(), any(), any());
        verify(iAchAccountsMapper).convertAchAccountToDestinationAccount(any());
    }

    @Test
    void givenValidDataWhenGetThirdAccountThenResponseExpected() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        List<DestinationAccount> thirdDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        when(thirdAccountProvider.getThirdAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(thirdDestinationAccount);

        // Act
        DestinationAccount result = service.getAccount(personId, "1", "1", parameter);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), thirdDestinationAccount.get(0).getId());
        verify(thirdAccountProvider).getThirdAccounts(personId, parameter);
        verify(thirdMapper, times(1)).convertThirdAccountToDestinationAccount(any(), any(), any());
    }

    @Test
    void givenValidDataWhenGetAchAccountThenResponseExpected() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();

        AchAccountsMWResponse achAccountsMWResponse = AchAccountMWResponseFixture.withDefaultAchAccountMWResponse();
        List<DestinationAccount> achDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        when(achAccountProvider.getAchAccounts(personId, parameter)).thenReturn(achAccountsMWResponse);
        when(iAchAccountsMapper.convertAchAccountToDestinationAccount(any())).thenReturn(achDestinationAccount);

        // Act
        DestinationAccount result = service.getAccount(personId, "2", "1", parameter);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), achDestinationAccount.get(0).getId());
        verify(achAccountProvider).getAchAccounts(personId, parameter);
        verify(iAchAccountsMapper).convertAchAccountToDestinationAccount(any());
    }

    @Test
    void givenValidDataWhenGetWalletAccountThenResponseExpected() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        List<DestinationAccount> walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        when(thirdAccountProvider.getWalletAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);

        // Act
        DestinationAccount result = service.getAccount(personId, "3", "1", parameter);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), walletDestinationAccount.get(0).getId());
        verify(thirdAccountProvider).getWalletAccounts(personId, parameter);
        verify(thirdMapper).convertThirdAccountToDestinationAccount(any(), any(), any());
    }

    @Test
    void givenInvalidAccountTypeWhenGetAccountThenExceptionResponse() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> service.getAccount(personId, "4", "1", parameter));

        // Assert
        assertNotNull(exception);
        assertEquals(exception.getCode(), DefaultMiddlewareError.BAD_REQUEST.getCode());
    }

    @Test
    void givenInvalidAccountIdWhenGetAccountThenExceptionResponse() throws IOException {
        // Arrange
        String personId = "1234567";
        Map<String, String> parameter = new HashMap<>();

        ThirdAccountsMWResponse responseExpected = ThirdAccountMWResponseFixture.withDefaultThirdAccountListMWResponse();
        List<DestinationAccount> walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        when(thirdAccountProvider.getWalletAccounts(personId, parameter)).thenReturn(responseExpected);
        when(thirdMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> service.getAccount(personId, "3", "123", parameter));

        // Assert
        assertNotNull(exception);
        assertEquals(exception.getCode(), ThirdAccountMiddlewareError.MDWACTM_3001.getCode());
        verify(thirdAccountProvider).getWalletAccounts(personId, parameter);
        verify(thirdMapper).convertThirdAccountToDestinationAccount(any(), any(), any());
    }
}