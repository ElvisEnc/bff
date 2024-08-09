package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.request.own.account.AccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountResponseFixture;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitsMWResponse;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
import bg.com.bo.bff.providers.models.enums.middleware.own.account.OwnAccountsMiddlewareResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnAccountServiceTest {
    @InjectMocks
    private OwnAccountService service;
    @Mock
    private IOwnAccountsProvider provider;
    @Mock
    private IOwnAccountsMapper mapper;

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGenericResponseSuccess() throws IOException {
        // Arrange
        UpdateTransactionLimitRequest request = AccountRequestFixture.withDefaultUpdateTransactionLimitRequest();
        when(provider.updateTransactionLimit(any(), any(), any(), any())).thenReturn(GenericResponse.instance(OwnAccountsMiddlewareResponse.SUCCESS_UPDATE_TRANSACTION_LIMIT));

        // Act
        GenericResponse response = service.updateTransactionLimit("1212", "122334", request, new HashMap<>());

        // Assert
        assertNotNull(response);
        verify(provider).updateTransactionLimit(any(), any(), any(), any());

    }

    @Test
    void givenPersonAndIdAccountAndAmountWhenTransactionLimitUpdateThenGetTransactionLimitResponse() throws IOException {
        // Arrange
        TransactionLimitsMWResponse expected = OwnAccountMWResponseFixture.withDefaultTransactionLimitsMWResponse();
        when(provider.getTransactionLimit(any(), any(), any())).thenReturn(expected);
        when(mapper.convertResponse(any(TransactionLimitsMWResponse.class))).thenReturn(AccountResponseFixture.withDefaultTransactionLimitsResponse());

        // Act
        TransactionLimitsResponse actual = service.getTransactionLimit("1212", "122334", new HashMap<>());

        // Assert
        assertNotNull(actual);
        verify(provider).getTransactionLimit(any(), any(), any());
        verify(mapper).convertResponse(any(TransactionLimitsMWResponse.class));
    }
}