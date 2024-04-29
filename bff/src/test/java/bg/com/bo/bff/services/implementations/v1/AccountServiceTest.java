package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.requests.UpdateTransactionLimitRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.responses.accounts.TransactionLimitUpdateAccountResponse;
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
        UpdateTransactionLimitRequest request = UpdateTransactionLimitRequestFixture.withDefault();
        when(provider.updateTransactionLimit(any(),any(), any(), any())).thenReturn(GenericResponse.instance(TransactionLimitUpdateAccountResponse.SUCCESS));

        // Act
        GenericResponse response = service.updateTransactionLimit("1212","122334", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        assertEquals(response.getCode(),TransactionLimitUpdateAccountResponse.SUCCESS.getCode());
        assertEquals(response.getMessage(),TransactionLimitUpdateAccountResponse.SUCCESS.getMessage());
        verify(provider).updateTransactionLimit(any(),any(),any(),any());

    }
}