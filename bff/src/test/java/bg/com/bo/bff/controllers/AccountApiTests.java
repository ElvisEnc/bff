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
        AccountListResponse accountListResponse = new AccountListResponse();
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
        accountListResponse.setData(list);
        Mockito.when(iAccountService.getAccounts(personId)).thenReturn(accountListResponse);

        // Act
        ResponseEntity<AccountListResponse>  response = accountController.accounts(personId);

        // Assert
        assert response.getStatusCode().value()== HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }
}
