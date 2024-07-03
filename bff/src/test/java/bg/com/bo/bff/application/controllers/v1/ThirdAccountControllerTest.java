package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccount;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccountListResponse;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
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
class ThirdAccountControllerTest {
    @InjectMocks
    private ThirdAccountController accountController;

    @Mock
    private IThirdAccountService iThirdAccountService;

    @Test
    void givenPersonIdAndCompaniesWhenToGetThirdAccountsThenReturnListThirdAccounts() throws IOException {
        // Arrange
        String personId = "123456";
        String companies = "123456";
        ThirdAccountListResponse accountListResponse = new ThirdAccountListResponse();
        ThirdAccount account = new ThirdAccount();
        List<ThirdAccount> list = new ArrayList<>();
        list.add(account);
        accountListResponse.setData(list);
        Mockito.when(iThirdAccountService.getListThirdAccounts(companies,personId)).thenReturn(accountListResponse);

        // Act
        ResponseEntity<ThirdAccountListResponse> response = accountController.getThirdAccounts(companies, personId);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }
}