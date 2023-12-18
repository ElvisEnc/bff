package bg.com.bo.bff.controllers;

import bg.com.bo.bff.controllers.v1.AccountController;
import bg.com.bo.bff.model.Account;
import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AccountApiTests {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private IAccountService iAccountService;

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
        Mockito.when(iAccountService.getAccounts(personId, documentNumber)).thenReturn(accountListResponse);

        // Act
        ResponseEntity<AccountListResponse> response = accountController.accounts(personId, documentNumber);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }
}
