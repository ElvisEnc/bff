package bg.com.bo.bff.controllers;

import bg.com.bo.bff.controllers.v1.AccountController;
import bg.com.bo.bff.model.Account;
import bg.com.bo.bff.model.UserData;
import bg.com.bo.bff.model.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountApiTests {
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

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);

            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            ResponseEntity<AccountListResponse> response = accountController.accounts(personId, documentNumber);

            // Assert
            assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            assertNotNull(response.getBody());
        } catch (Exception e) {
            fail("An exception was thrown.");
        }
    }
}
