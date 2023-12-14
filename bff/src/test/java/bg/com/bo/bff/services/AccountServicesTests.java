package bg.com.bo.bff.services;

import bg.com.bo.bff.model.*;
import bg.com.bo.bff.model.interfaces.AccountListMapper;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.services.v1.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AccountServicesTests {
    @Autowired
    private AccountService accountService;

    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        String personId = "123456789";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        accountService = new AccountService(httpClientFactoryMock, AccountListMapper.INSTANCE);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpPostResponseMock = Mockito.mock(CloseableHttpResponse.class);
        CloseableHttpResponse closeableHttpGetResponseMock = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity httpPostEntityMock = Mockito.mock(HttpEntity.class);
        HttpEntity httpGetEntityMock = Mockito.mock(HttpEntity.class);

        ClientToken clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken("test");
        clientTokenMock.setTokenType("test");
        clientTokenMock.setExpiresIn(600);
        clientTokenMock.setScope("test");
        clientTokenMock.setJti("test");
        System.out.println("clientTokenMock:; " + clientTokenMock);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientTokenMock);
        InputStream tokenResponseMock = new ByteArrayInputStream(json.getBytes());

        AccountListMWResponse accountListMWResponseMock = new AccountListMWResponse();
        Account account = new Account();
        account.setAccountId("4355307");
        account.setAccountNumber("1310325715");
        account.setClientName("CLIENTE");
        account.setClientCode("5219027");
        account.setAccountHolderCode(" ");
        account.setCurrencyCode("068");
        account.setCurrencyDescription("Bs");
        account.setProductDescription("CAJA DE AHORRO GANADOBLE");
        account.setAccountManagementCode("I");
        account.setAccountType("CA");
        account.setAvailiableBalance(0);
        account.setAccountManagementDescription("Individual");
        account.setOpeningDate("2019-05-25");
        account.setDateOfLastMovement("2019-08-31");
        account.setTotalBalance(0);
        account.setPledgeFounds(0);
        account.setPendingDeposits(0);
        account.setStatusCode(" ");
        account.setStatusDescription("SIN BLOQUEO");
        account.setBranchCode("712");
        account.setBranchDescription("GRIGOTA");
        account.setDepartamentCode("7");
        account.setDepartamentDescription("SANTA CRUZ");
        account.setAccountUsage("T");
        account.setAccountUsageDescription("Todo Uso");
        List<Account> list = new ArrayList<>();
        list.add(account);
        AccountListMWMetadata mwMetadata = new AccountListMWMetadata();
        mwMetadata.setTotalPag(0);
        mwMetadata.setTotalRecords(0);
        accountListMWResponseMock.setData(list);
        accountListMWResponseMock.setMeta(mwMetadata);
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(accountListMWResponseMock);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpPostResponseMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpPostResponseMock.getEntity()).thenReturn(httpPostEntityMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpGetEntityMock);
        Mockito.when(httpPostEntityMock.getContent()).thenReturn(tokenResponseMock);
        Mockito.when(httpGetEntityMock.getContent()).thenReturn(accountsResponseMock);

        // Act
        AccountListResponse response = accountService.getAccounts(personId);

        // Assert
        Assertions.assertNotNull(response.getData());
    }
}
