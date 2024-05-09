package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddAchAccountRequestFixture;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequestFixture;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.*;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponseFixture;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.models.ThirdAccountListResponseFixture;
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.responses.BanksMWResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponse;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddAccountResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.providers.mappings.destination.account.IDestinationAccountMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinationAccountServiceTest {
    private DestinationAccountService service;
    @Mock
    private IThirdAccountProvider thirdAccountProvider;
    @Mock
    private IAchAccountProvider achAccountProvider;
    private final DestinationAccountServiceMapper mapper = DestinationAccountServiceMapper.INSTANCE;
    @Mock
    private IDestinationAccountMapper iDestinationAccountMapper;

    @BeforeEach
    void init() {
        service = new DestinationAccountService(thirdAccountProvider, achAccountProvider, mapper, iDestinationAccountMapper);
    }

    @Test
    void givenValidaDataWhenAddThirdAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddThirdAccountRequest request = AddThirdAccountRequestFixture.withDefault();
        when(thirdAccountProvider.
                addThirdAccount(any(), any(), any())).thenReturn(GenericResponse.instance(AddAccountResponse.SUCCESS));
        when(thirdAccountProvider.generateAccessToken()).thenReturn(clientToken);

        // Act
        GenericResponse response = service.addThirdAccount("1212", request, new HashMap<>());

        assertNotNull(response);
        verify(thirdAccountProvider).generateAccessToken();
        verify(thirdAccountProvider).addThirdAccount(any(), any(), any());

    }

    @Test
    void givenValidaDataWhenAddAchAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddAchAccountRequest request = AddAchAccountRequestFixture.withDefault();
        when(achAccountProvider.generateAccessToken()).thenReturn(clientToken);
        when(achAccountProvider.
                addAchAccount(any(), any(), any())).thenReturn(GenericResponse.instance(AddAccountResponse.SUCCESS));

        // Act
        GenericResponse response = service.addAchAccount("1212", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(achAccountProvider).addAchAccount(any(), any(), any());
        verify(achAccountProvider).generateAccessToken();
    }

    @Test
    void givenValidDataWhenDeleteAccountThenReturnOk() throws IOException {
        // Arrange
        DeleteThirdAccountRequest request = new DeleteThirdAccountRequest();
        request.setAccountId(1);
        String personId = "1";
        int identifier = 1;
        String deviceId = "1";
        String ip = "127.0.0.1";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Satisfactorio");
        when(thirdAccountProvider.delete(personId, identifier, request.getAccountId(), deviceId, ip)).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.delete(personId, identifier, deviceId, ip, request);

        // Assert
        verify(thirdAccountProvider).delete(personId, identifier, request.getAccountId(), deviceId, ip);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenValidaDataWhenAddWalletAccountThenReturnOk() throws IOException {
        // Arrange
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddWalletAccountRequest request = AddWalletAccountRequestFixture.withDefault();
        when(thirdAccountProvider.
                addWalletAccount(any(), any(), any())).thenReturn(GenericResponse.instance(AddAccountResponse.SUCCESS));
        when(thirdAccountProvider.generateAccessToken()).thenReturn(clientToken);

        // Act
        GenericResponse response = service.addWalletAccount("1212", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(thirdAccountProvider).generateAccessToken();
        verify(thirdAccountProvider).addWalletAccount(any(), any(), any());
    }

    @Test
    void givenValidaDataWhenDeleteWalletAccountThenReturnOk() throws IOException {
        // Arrange
        String personId = "1";
        int accountNumber = 1;
        int identifier = 1;
        String deviceId = "1";
        String deviceIp = "127.0.0.1";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Se borró la cuenta exitosamente.");
        when(thirdAccountProvider.deleteWalletAccount(personId, identifier, accountNumber, deviceId, deviceIp)).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.deleteWalletAccount(personId, identifier, accountNumber, deviceId, deviceIp);

        // Assert
        verify(thirdAccountProvider).deleteWalletAccount(personId, identifier, accountNumber, deviceId, deviceIp);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenValidDataWhenDeleteAchAccountThenReturnOk() throws IOException {
        // Arrange
        String personId = "1";
        int identifier = 1;
        String deviceId = "1";
        String ip = "127.0.0.1";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Se borró la cuenta exitosamente.");
        when(achAccountProvider.deleteAchAccount(personId, identifier, deviceId, ip)).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.deleteAchAccount(personId, identifier, deviceId, ip);

        // Assert
        verify(achAccountProvider).deleteAchAccount(personId, identifier, deviceId, ip);
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
        BanksMWResponse expected = BanksMWResponseFixture.withDefault();
        when(achAccountProvider.
                getBanks()).thenReturn(expected);

        // Act
        BanksResponse actual = service.getBanks();

        // Assert
        assertEquals(expected.getData().size(), actual.getData().size());
        assertEquals(expected.getData().get(0).getCode(), actual.getData().get(0).getCode());
        assertEquals(expected.getData().get(0).getDescription(), actual.getData().get(0).getDescription());

        verify(achAccountProvider).getBanks();
    }

    @Test
    void giveValidBankCodeWhenGetAllBranchOfficeBankThenReturnSuccess() throws IOException {
        // Arrange
        Integer bankCode = 123;
        BranchOfficeMWResponse responseMWMock = BranchOfficeMWResponseFixture.withDefault();
        BranchOfficeResponse expectedResponse = BranchOfficeResponseFixture.withDefault();
        Mockito.when(achAccountProvider.getAllBranchOfficeBank(Mockito.any()))
                .thenReturn(responseMWMock);
        Mockito.when(iDestinationAccountMapper.mapToBranchOfficeResponse(responseMWMock)).thenReturn(expectedResponse);

        // Act
        BranchOfficeResponse response = service.getBranchOffice(bankCode);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(achAccountProvider).getAllBranchOfficeBank(bankCode);
        verify(iDestinationAccountMapper).mapToBranchOfficeResponse(responseMWMock);
    }

    @Test
    void givenAccountNumberAndClientNameWhenGetValidateDestinationAccountThenValidateAccountResponse() throws Exception {
        // Arrange
        String accountNumber ="1310766620";
        String clientName ="BANCO";
        ValidateAccountResponse expected = ValidateAccountResponseFixture.withDefault();
        when(thirdAccountProvider.validateAccount(any(),any(),any())).thenReturn(expected);
        // Act

        ValidateAccountResponse acutal = service.getValidateDestinationAccounts(accountNumber, clientName, new HashMap<>());

        // Assert
        assertEquals(expected.getData(), acutal.getData());
        verify(thirdAccountProvider).validateAccount(any(),any(),any());
    }

    @Test
    void givenPersonIdAndParameterWhenGetListDestinationAccountThenReturnList() throws IOException {
        // Arrange
        Integer personId = 1234567;
        Map<String, String> parameter = new HashMap<>();
        Boolean isInitial = true;
        String accessToken = "tokentoken";
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken(accessToken);

        ThirdAccountListResponse thirdAccountsResponse = ThirdAccountListResponseFixture.withDefault();
        ThirdAccountListResponse walletAccountsResponse = ThirdAccountListResponseFixture.withDefault();
        AchAccountMWResponse achAccountMWResponse = AchAccountMWResponseFixture.withDefault();

        when(thirdAccountProvider.generateAccessToken()).thenReturn(clientToken);
        when(thirdAccountProvider.getThirdAccounts(personId, accessToken, parameter)).thenReturn(thirdAccountsResponse);
        when(thirdAccountProvider.getWalletAccounts(personId, accessToken, parameter)).thenReturn(walletAccountsResponse);
        when(achAccountProvider.getAchAccounts(personId, parameter)).thenReturn(achAccountMWResponse);

        DestinationAccount thirdDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        DestinationAccount walletDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();
        DestinationAccount achDestinationAccount = DestinationAccountResponseFixture.getDestinationAccountDefault();

        when(iDestinationAccountMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(thirdDestinationAccount);
        when(iDestinationAccountMapper.convertThirdAccountToDestinationAccount(any(), any(), any())).thenReturn(walletDestinationAccount);
        when(iDestinationAccountMapper.convertAchAccountToDestinationAccount(any())).thenReturn(achDestinationAccount);

        // Act
        List<DestinationAccount> result = service.getListDestinationAccount(personId, parameter, isInitial);

        // Assert
        assertEquals(3, result.size());
        verify(thirdAccountProvider).generateAccessToken();
        verify(thirdAccountProvider).getThirdAccounts(personId, accessToken, parameter);
        verify(thirdAccountProvider).getWalletAccounts(personId, accessToken, parameter);
        verify(achAccountProvider).getAchAccounts(personId, parameter);
        verify(iDestinationAccountMapper, times(2)).convertThirdAccountToDestinationAccount(any(), any(), any());
        verify(iDestinationAccountMapper).convertAchAccountToDestinationAccount(any());
    }
}