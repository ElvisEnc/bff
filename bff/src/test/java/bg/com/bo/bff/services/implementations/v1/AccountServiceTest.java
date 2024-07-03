package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.request.own.account.AccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountResponseFixture;
import bg.com.bo.bff.application.dtos.response.own.account.GetTransactionLimitResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitUpdateAccountResponse;
import bg.com.bo.bff.providers.interfaces.IAccountProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Spy
    @InjectMocks
    private AccountService service;

    @Mock
    private IAccountProvider provider;

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGenericResponseSuccess() throws IOException {
        // Arrange
        UpdateTransactionLimitRequest request = AccountRequestFixture.withDefaultUpdateTransactionLimitRequest();
        when(provider.updateTransactionLimit(any(), any(), any(), any())).thenReturn(GenericResponse.instance(TransactionLimitUpdateAccountResponse.SUCCESS));

        // Act
        GenericResponse response = service.updateTransactionLimit("1212", "122334", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getCode(), TransactionLimitUpdateAccountResponse.SUCCESS.getCode());
        assertEquals(response.getMessage(), TransactionLimitUpdateAccountResponse.SUCCESS.getMessage());
        verify(provider).updateTransactionLimit(any(), any(), any(), any());

    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitResponse() throws IOException {
        // Arrange
        GetTransactionLimitResponse expected = AccountResponseFixture.withDefaultGetTransactionLimitResponse();
        when(provider.getTransactionLimit(any(), any(), any())).thenReturn(OwnAccountMWResponseFixture.withDefaultTransactionLimitListMWResponse());

        // Act
        GetTransactionLimitResponse actual = service.getTransactionLimit("1212", "122334", new HashMap<>());

        // Assert
        assertNotNull(actual);
        assertEquals(expected.getData().getAmountLimit(), actual.getData().getAmountLimit());
        assertEquals(expected.getData().getCountLimit(), actual.getData().getCountLimit());
        verify(provider).getTransactionLimit(any(), any(), any());
    }
}