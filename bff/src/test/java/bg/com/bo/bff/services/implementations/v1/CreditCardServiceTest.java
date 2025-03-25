package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.credit.card.*;
import bg.com.bo.bff.application.dtos.response.credit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.card.CreditCardMapper;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;
import bg.com.bo.bff.providers.interfaces.ICreditCardProvider;
import bg.com.bo.bff.providers.interfaces.ICreditCardTransactionProvider;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardTransactionMiddlewareError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {
    @InjectMocks
    private CreditCardService service;
    @Mock
    private ICreditCardProvider provider;
    @Mock
    private ICreditCardTransactionProvider transactionProvider;
    @Spy
    private CreditCardMapper mapper = new CreditCardMapper();
    @Mock
    private CreditCardService self;

    // List Credit Card
    @Test
    void givenPersonCodeWhenGetListCreditCardThenListCreditCardResponse() throws IOException {
        //Arrange
        ListCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultListCreditCardMWResponse();
        ListCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultListCreditCardResponse();
        when(provider.getListCreditCard(any())).thenReturn(mwResponseMock);

        //Act
        ListCreditCardResponse response = service.getListCard("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListCreditCard(any());
        verify(mapper).convertResponse(any(ListCreditCardMWResponse.class));
    }

    @Test
    void givenPersonCodeWhenGetListCreditCardThenListNull() throws IOException {
        //Arrange
        ListCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultListCreditCardResponseEmpty();
        when(provider.getListCreditCard(any())).thenReturn(null);

        //Act
        ListCreditCardResponse response = service.getListCard("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    // Details Credit Card
    @Test
    void givenPersonCodeAndCardIdWhenGetDetailsCreditCardThenDetailCreditCardResponse() throws IOException {
        //Arrange
        DetailsCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultDetailsCreditCardMWResponse();
        DetailCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultDetailCreditCardResponse();
        when(provider.getDetailCreditCard(any(), any())).thenReturn(mwResponseMock);

        //Act
        DetailCreditCardResponse response = service.getDetailsCreditCard("123", "123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getDetailCreditCard(any(), any());
        verify(mapper).convertDetails(any(DetailsCreditCardMWResponse.class));
    }

    // Details Prepaid Card
    @Test
    void givenPersonCodeAndCardIdWhenGetDetailsPrepaidCardThenDetailPrepaidCardResponse() throws IOException {
        //Arrange
        DetailPrepaidCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultDetailPrepaidCardResponse();
        DetailPrepaidCardResponse expectedResponse = CreditCardResponseFixture.withDefaultDetailPrepaidCardResponse();
        when(provider.getDetailPrepaidCard(any(), any())).thenReturn(mwResponseMock);

        //Act
        DetailPrepaidCardResponse response = service.getDetailsPrepaidCard("123", "123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getDetailPrepaidCard(any(), any());
        verify(mapper).convertDetails(any(DetailPrepaidCardMWResponse.class));
    }

    // Block Credit Card
    @Test
    void givenRequestBlockCreditCardWhenBlockCreditCardThenResponseExpected() throws IOException {
        //Arrange
        BlockCreditCardRequest request = CreditCardRequestFixture.withDefaultBlockCreditCardRequest();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        when(provider.blockCreditCard(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.blockCreditCard("123", "123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(mapper).mapperRequest(any(BlockCreditCardRequest.class), any());
        verify(provider).blockCreditCard(any());
    }

    @Test
    void givenRequestUnblockCreditCardWhenBlockCreditCardThenResponseExpected() throws IOException {
        //Arrange
        BlockCreditCardRequest request = CreditCardRequestFixture.withDefaultBlockCreditCardRequestUnlock();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        when(provider.blockCreditCard(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.blockCreditCard("123", "123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(mapper).mapperRequest(any(BlockCreditCardRequest.class), any());
        verify(provider).blockCreditCard(any());
    }

    // Available
    @Test
    void givenCmsCardWhenGetAvailableCreditCardThenAvailableCreditCardResponse() throws IOException {
        //Arrange
        AvailableCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultAvailableCreditCardMWResponse();
        AvailableCreditCardResponse expectedResponse = CreditCardResponseFixture.withDefaultAvailableCreditCardResponse();
        when(provider.getAvailable(any())).thenReturn(mwResponseMock);

        //Act
        AvailableCreditCardResponse response = service.getAvailable("123", "123", "123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getAvailable(any());
        verify(mapper).convertAvailable(any());
    }

    // Periods
    @Test
    void givenPersonIdWhenGetPeriodsCreditCardThenReturnExpectedResponse() throws IOException {
        //Arrange
        PeriodCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultPeriods();
        List<PeriodCreditCardResponse> expectedResponse = CreditCardResponseFixture.withDefaultPeriods();
        when(provider.getPaymentPeriods()).thenReturn(mwResponseMock);

        //Act
        List<PeriodCreditCardResponse> response = service.getPeriods("123", "123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getPaymentPeriods();
        verify(mapper).convertPeriods(any());
    }

    @Test
    void givenPersonIdWhenGetPeriodsCreditCardThenReturnExpectedResponseEmpty() throws IOException {
        //Arrange
        PeriodCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withPeriodsNull();
        when(provider.getPaymentPeriods()).thenReturn(mwResponseMock);

        //Act
        List<PeriodCreditCardResponse> response = service.getPeriods("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getPaymentPeriods();
        verify(mapper).convertPeriods(any());
    }

    @Test
    void givenPersonIdWhenGetPeriodsCreditCardThenReturnExpectedResponseNull() throws IOException {
        //Arrange
        when(provider.getPaymentPeriods()).thenReturn(null);

        //Act
        List<PeriodCreditCardResponse> response = service.getPeriods("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getPaymentPeriods();
        verify(mapper).convertPeriods(any());
    }

    @Test
    void givenCmsAccountNumberAndAmountWhenGetCashAdvanceFeeThenExpectResponse() throws IOException {
        //Arrange
        String personId = "123";
        String cmsAccountNumber = "13-01-10-034599";
        BigDecimal amount = BigDecimal.valueOf(100.5);

        CashAdvanceFeeMWResponse mwResponseMock = CreditCardMWResponseFixture.createCashAdvanceFee();
        when(provider.getCashAdvanceFee(any())).thenReturn(mwResponseMock);

        //Act
        CashAdvanceFeeResponse response = service.getCashAdvanceFee(personId, cmsAccountNumber, amount);

        //Assert
        assertNotNull(response);
        assertEquals(mwResponseMock.getAmountCommission(), response.fee());
        verify(provider).getCashAdvanceFee(any());
        verify(mapper).mapperRequest(personId, cmsAccountNumber, amount);
    }

    // List Credit Card Linkser
    @Test
    void givenPersonIdAndCmsAccountWhenGetCreditCardsThenReturnExpectedResponse() throws IOException {
        //Arrange
        LinkserCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultLinkserCreditCardMWResponse();
        List<LinkserCreditCardResponse> expectedResponse = CreditCardResponseFixture.withDefaultLinkserCreditCardResponse();
        when(provider.getCreditCardsFromLinkser(any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LinkserCreditCardResponse> response = service.getCreditCards("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getCreditCardsFromLinkser(any(), any());
        verify(mapper).convertListCreditCard(any());
    }

    @Test
    void givenPersonIdAndCmsAccountWhenGetCreditCardsThenReturnExpectedResponseEmpty() throws IOException {
        //Arrange
        LinkserCreditCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultLinkserCreditCardMWResponseEmpty();
        when(provider.getCreditCardsFromLinkser(any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LinkserCreditCardResponse> response = service.getCreditCards("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getCreditCardsFromLinkser(any(), any());
        verify(mapper).convertListCreditCard(any());
    }

    @Test
    void givenPersonIdAndCmsAccountWhenGetCreditCardsThenReturnExpectedResponseNull() throws IOException {
        //Arrange
        when(provider.getCreditCardsFromLinkser(any(), any())).thenReturn(null);

        //Act
        List<LinkserCreditCardResponse> response = service.getCreditCards("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getCreditCardsFromLinkser(any(), any());
        verify(mapper).convertListCreditCard(any());
    }

    // Advance Cash
    @Test
    void givenCashAdvanceRequestWhenMakeCashAdvanceThenReturnExpectedResponse() throws IOException {
        //Arrange
        CashAdvanceRequest request = CreditCardRequestFixture.withDefaultCashAdvanceRequest();
        CashAdvanceMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultCashAdvanceMWResponse();
        CashAdvanceResponse expectedResponse = CreditCardResponseFixture.withDefaultCashAdvanceResponse();
        when(provider.cashAdvance(any())).thenReturn(mwResponseMock);

        //Act
        CashAdvanceResponse response = service.makeCashAdvance("123", "123", request);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(mapper).mapperRequestAdvance(any(), any());
        verify(provider).cashAdvance(any());
        verify(mapper).convertResponse(any(CashAdvanceMWResponse.class));
    }

    // Credit Card Statements
    @Test
    void givenCreditCardRequestWhenGetStatementsThenReturnExpectedResponse() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequest();
        List<CreditCardStatementsResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultCreditCardStatementsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getStatementsCache(any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<CreditCardStatementsResponse> response = service.creditCardStatements("123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getStatementsCache(any(), any(), eq(true));
    }

    @Test
    void givenPaginationAndOrderRequestNullWhenGetStatementsThenReturnExpectedResponse() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequestNull();
        List<CreditCardStatementsResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultCreditCardStatementsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getStatementsCache(any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<CreditCardStatementsResponse> response = service.creditCardStatements("123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getStatementsCache(any(), any(), eq(true));
    }

    @Test
    void givenOrderAmountRequestNullWhenGetStatementsThenReturnExpectedResponse() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequestAmount();
        List<CreditCardStatementsResponse> expectedResponse = Arrays.asList(
                CreditCardResponseFixture.withDefaultCreditCardStatementsResponse(),
                CreditCardResponseFixture.withDefaultCreditCardStatementsResponse2(),
                CreditCardResponseFixture.withDefaultCreditCardStatementsResponse3());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getStatementsCache(any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<CreditCardStatementsResponse> response = service.creditCardStatements("123", request);

        //Assert
        assertNotNull(response);
        verify(self).getStatementsCache(any(), any(), eq(true));
    }

    @Test
    void givenCreditCardRequestWhenGetStatementsCacheThenReturnExpectedResponse() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequest();
        CreditCardStatementsMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultCreditCardStatementsMWResponse();
        List<CreditCardStatementsResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultCreditCardStatementsResponse());
        when(provider.getStatements(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<CreditCardStatementsResponse> response = service.getStatementsCache("123", request, true);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getStatements(any(), any(), any());
        verify(mapper).convertStatements(any());
    }

    @Test
    void givenCreditCardResponseDataNullWhenGetStatementsCacheThenReturnExpectedResponseNull() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequest();
        CreditCardStatementsMWResponse mwResponseMock = new CreditCardStatementsMWResponse(null);
        when(provider.getStatements(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<CreditCardStatementsResponse> response = service.getStatementsCache("123", request, true);

        //Assert
        assertNotNull(response);
        verify(provider).getStatements(any(), any(), any());
        verify(mapper).convertStatements(any());
    }

    @Test
    void givenCreditCardResponseNullWhenGetStatementsCacheThenReturnExpectedResponseNull() throws IOException {
        //Arrange
        CreditCardStatementRequest request = CreditCardRequestFixture.withDefaultCreditCardStatementRequest();
        when(provider.getStatements(any(), any(), any())).thenReturn(null);

        //Act
        List<CreditCardStatementsResponse> response = service.getStatementsCache("123", request, true);

        //Assert
        assertNotNull(response);
        verify(provider).getStatements(any(), any(), any());
        verify(mapper).convertStatements(any());
    }

    // List Purchases Authorizations
    @Test
    void givenListPurchaseRequestWhenGetPurchasesAuthorizationsThenReturnExpectedResponse() throws IOException {
        //Arrange
        PurchaseAuthMWResponse responseMock = CreditCardMWResponseFixture.withDefaultPurchaseAuthMWResponse();
        List<PurchaseAuthResponse> expectedResponse = Collections.singletonList(CreditCardResponseFixture.withDefaultPurchaseAuthResponse());
        when(provider.getListPurchaseAuth(any(), any())).thenReturn(responseMock);

        //Act
        List<PurchaseAuthResponse> response = service.getPurchasesAuthorizations("123", "123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListPurchaseAuth(any(), any());
        verify(mapper).convertPurchase(any());
    }

    @Test
    void givenListPurchaseRequestWhenGetPurchasesAuthorizationsThenReturnExpectedResponseEmpty() throws IOException {
        //Arrange
        PurchaseAuthMWResponse responseMock = PurchaseAuthMWResponse.builder().build();
        when(provider.getListPurchaseAuth(any(), any())).thenReturn(responseMock);

        //Act
        List<PurchaseAuthResponse> response = service.getPurchasesAuthorizations("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getListPurchaseAuth(any(), any());
    }

    @Test
    void givenListPurchaseRequestWhenGetPurchasesAuthorizationsThenReturnExpectedResponseNull() throws IOException {
        //Arrange
        when(provider.getListPurchaseAuth(any(), any())).thenReturn(null);

        //Act
        List<PurchaseAuthResponse> response = service.getPurchasesAuthorizations("123", "123");

        //Assert
        assertNotNull(response);
        verify(provider).getListPurchaseAuth(any(), any());
    }

    // Pay Credit Card
    @Test
    void givenPayCreditCardRequestWhenPayCreditCardThenReturnExpectedResponse() throws IOException {
        //Arrange
        PayCreditCardRequest requestMock = CreditCardRequestFixture.defaultPayCreditCardRequest();
        PayCreditCardMWResponse responseMock = CreditCardMWResponseFixture.defaultPayCreditCardMWResponse();
        PayCreditCardResponse expectedResponse = CreditCardResponseFixture.defaultPayCreditCardResponse();
        when(transactionProvider.payCreditCard(any())).thenReturn(responseMock);

        //Act
        PayCreditCardResponse response = service.payCreditCard("123", "123", requestMock);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(mapper).mapperRequest(any(), any(), any(PayCreditCardRequest.class));
        verify(transactionProvider).payCreditCard(any());
        verify(mapper).convertPayCC(any());
    }

    @Test
    void givenPayCreditCardRequestWhenPayCreditCardThenReturnResponsePending() throws IOException {
        //Arrange
        PayCreditCardRequest requestMock = CreditCardRequestFixture.defaultPayCreditCardRequest();
        PayCreditCardMWResponse responseMock = CreditCardMWResponseFixture.defaultPayCreditCardMWResponsePending();
        when(transactionProvider.payCreditCard(any())).thenReturn(responseMock);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.payCreditCard("123", "123", requestMock)
        );

        //Assert
        assertNotNull(exception);
        assertEquals(CreditCardTransactionMiddlewareError.PENDING.getMessage(), exception.getMessage());
        verify(mapper).mapperRequest(any(), any(), any(PayCreditCardRequest.class));
        verify(transactionProvider).payCreditCard(any());
    }

    // Authorization Credit Card
    @Test
    void givenRequestAuthorizationCreditCardWhenAuthorizationCreditCardThenResponseExpected() throws IOException {
        //Arrange
        AuthorizationCreditCardRequest request = CreditCardRequestFixture.withDefaultAuthorizationCreditCardRequest();
        GenericResponse expectedResponse = GenericResponse.instance(CreditCardMiddlewareResponse.SUCCESS_AUTHORIZATION_ONLINE);
        when(provider.authorizationCreditCard(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.authorizationCreditCard("123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(mapper).mapperRequest(any(), any(AuthorizationCreditCardRequest.class));
        verify(provider).authorizationCreditCard(any());
    }

    // Comission Prepaid Card
    @Test
    void givenRequestComissionPrepaidCardWhenComissionPrepaidCardThenResponseExpected() throws IOException {
        //Arrange
        FeePrepaidCardRequest request = CreditCardRequestFixture.withDefaultComissionPrepaidCardRequest();
        FeePrepaidCardResponse expectedResponse = CreditCardResponseFixture.withDefaultComissionPrepaidCardResponse();
        FeePrepaidCardMWResponse mwResponseMock = CreditCardMWResponseFixture.withDefaultComissionPrepaidCardMWResponse();
        when(provider.getFeePrepaidCard(any())).thenReturn(mwResponseMock);

        //Act
        FeePrepaidCardResponse response = service.getFeePrepaidCard("123", "123", request);

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(mapper).mapperRequest(any(), any(FeePrepaidCardRequest.class));
        verify(provider).getFeePrepaidCard(any());
    }
}