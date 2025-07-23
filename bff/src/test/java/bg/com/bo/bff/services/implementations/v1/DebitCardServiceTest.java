package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.*;
import bg.com.bo.bff.application.dtos.response.debit.card.InternetAuthorizationResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.card.DebitCardMapper;
import bg.com.bo.bff.providers.dtos.request.debit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.*;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.mappings.providers.card.IDebitCardMapper;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.CreateAuthorizationOnlinePurchaseResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceTest {
    @InjectMocks
    private DebitCardService service;
    @Mock
    private IDebitCardProvider provider;
    @Spy
    private IDebitCardMapper mapper = new DebitCardMapper();

    @Test
    void givenValidDataWhenChangeAmountThenReturnSuccess() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLimitsRequest request = DebitCardRequestFixture.withDefaultDCLimitsRequest();
        DCLimitsMWRequest expectedRequest = DebitCardMWRequestFixture.withDefault();
        DCLimitsMWResponse mwResponseMock = DebitCardMWResponseFixture.withDefaultDCLimitsMWResponse();
        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);

        when(provider.changeAmount(any())).thenReturn(mwResponseMock);
        when(mapper.mapToLimitsRequest(request, personId, cardId)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.changeAmount(personId, cardId, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).changeAmount(expectedRequest);
        verify(mapper).mapToLimitsRequest(request, personId, cardId);
    }

    @Test
    void givenValidDataWhenChangeAmountThenReturnErrorChangeAmount() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLimitsRequest request = DebitCardRequestFixture.withDefaultDCLimitsRequest();
        DCLimitsMWRequest expectedRequest = DebitCardMWRequestFixture.withDefault();
        DCLimitsMWResponse mwResponseMock = DebitCardMWResponseFixture.withDefaultDCLimitsMWResponse();
        mwResponseMock.getData().setPciId(null);
        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_CHANGE_AMOUNT);

        when(provider.changeAmount(any())).thenReturn(mwResponseMock);
        when(mapper.mapToLimitsRequest(request, personId, cardId)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.changeAmount(personId, cardId, request);

        //Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).changeAmount(expectedRequest);
        verify(mapper).mapToLimitsRequest(request, personId, cardId);
    }

    @Test
    void givenPersonCodeWhenGetListDebitCardThenSuccess() throws IOException {
        // Arrange
        ListDebitCardMWResponse mwResponseMock = DebitCardMWResponseFixture.withDefaultListDebitCardMWResponse();
        when(provider.listDebitCard(any())).thenReturn(mwResponseMock);
        ListDebitCardResponse expectedResponse = DebitCardResponseFixture.withDefaultListDebitCardResponse();

        // Act
        ListDebitCardResponse response = service.getListDebitCard(123);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).listDebitCard(any());
        verify(mapper).convertResponseListDebitCard(any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenGetAccountListDebitCardThenSuccess() throws IOException {
        // Arrange
        AccountsDebitCardMWResponse mwResponseMock = DebitCardMWResponseFixture.withDefaultAccountsDebitCardMWResponse();
        ListAccountTDResponse expectedResponse = DebitCardResponseFixture.withDefaultListAccountTDResponse();
        when(provider.accountListDebitCard(any(), any())).thenReturn(mwResponseMock);

        // Act
        ListAccountTDResponse response = service.getAccountsTD(123, 123);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).accountListDebitCard(123, 123);
        verify(mapper).convertResponseAccountListTD(mwResponseMock);
    }

    @Test
    void convertResponseAccountListTDWhenMWResponseNullTest() throws IOException{
        //Arrange
        when(provider.accountListDebitCard(any(), any())).thenReturn(null);

        //Act
        ListAccountTDResponse response = service.getAccountsTD(5432, 96511);

        //Assert
        assertNotNull(response);
        verify(provider).accountListDebitCard(any(), any());
    }


    @Test
    void mapToInternetAuthorizationResponseWhenMWResponseNullTest() throws IOException{
        //Arrange
        when(provider.getListAuthorizations(any(), any())).thenReturn(null);

        //Act
        InternetAuthorizationResponse response = service.getListAuthorizations("123", "54321");

        //Assert
        assertNotNull(response);
        verify(provider).getListAuthorizations(any(), any());
    }

    @Test
    void givenPersonIdAndCardIdWhenGetListAuthorizationsThenDCInternetAuthorizationNWResponse() throws IOException {
        // Arrange
        DCInternetAuthorizationNWResponse mwResponseMock = DebitCardMWResponseFixture.withDefaultDCInternetAuthorizationNWResponse();
        InternetAuthorizationResponse expected = DebitCardResponseFixture.withDefaultInternetAuthorizationResponse();
        when(provider.getListAuthorizations(any(), any())).thenReturn(mwResponseMock);

        // Act
        InternetAuthorizationResponse response = service.getListAuthorizations("169494", "169494");

        // Assert
        Assertions.assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        verify(provider).getListAuthorizations(any(), any());
        verify(mapper).mapToInternetAuthorizationResponse(mwResponseMock);
    }

    @Test
    void givenPersonCodeAndCardIdAndAuthIdWhenDeleteAuthThenSuccess() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_DELETE_AUTH_PURCHASE);
        when(provider.deleteAuth(any())).thenReturn(expectedResponse);
        when(mapper.mapDeleteAuthRequest(any(), any(), any())).thenReturn(DebitCardMWRequestFixture.withDefaultDeleteAuthPurchaseMWRequest());

        // Act
        GenericResponse response = service.deleteAuthOnlinePurchases(123, 123, 123);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).deleteAuth(DebitCardMWRequestFixture.withDefaultDeleteAuthPurchaseMWRequest());
        verify(mapper).mapDeleteAuthRequest(123, 123, 123);
    }

    @Test
    void givenPersonCodeAndCardIdWhenActiveDebitCardAssuranceThenSuccess() throws IOException {
        // Arrange
        UpdateDebitCardAssuranceRequest request = DebitCardRequestFixture.withDefaultUpdateDebitCardAssuranceRequest();
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        when(provider.activeDebitCardSecure(any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.activeDebitCardAssurance(123, 123, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).activeDebitCardSecure(any());
        verify(mapper).mapActiveAssuranceRequest(123, 123, request);
    }

    @Test
    void givenPersonCodeAndCardIdWhenActiveDebitCardAssuranceThenSuccessN() throws IOException {
        // Arrange
        UpdateDebitCardAssuranceRequest request = DebitCardRequestFixture.withDefaultUpdateDebitCardAssuranceRequestTrue();
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        when(provider.activeDebitCardSecure(any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.activeDebitCardAssurance(123, 123, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).activeDebitCardSecure(any());
        verify(mapper).mapActiveAssuranceRequest(any(), any(), any());
    }

    @Test
    void givenPersonCodeAndCardIdWhenActivateDebitCardThenSuccess() throws IOException {
        // Arrange
        GenericResponse expectedResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVATE_DEBIT_CARD);
        when(provider.activateDebitCard(any())).thenReturn(expectedResponse);
        when(mapper.mapActivateDebitCardRequest(any(), any())).thenReturn(DebitCardMWRequestFixture.withDefaultActivateDebitCardMWRequest());

        // Act
        GenericResponse response = service.activateDebitCard(123, 123, DebitCardRequestFixture.withDefaultActivateDebitCardRequest());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).activateDebitCard(DebitCardMWRequestFixture.withDefaultActivateDebitCardMWRequest());
        verify(mapper).mapActivateDebitCardRequest(123, 123);
    }

    @Test
    void givenValidDataWhenDetailThenReturnDCDetailResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCDetailResponse expected = DebitCardResponseFixture.withDefaultDCDetailResponse();

        Mockito.when(provider.detail(Mockito.any(), Mockito.any())).thenReturn(DebitCardMWResponseFixture.withDefaultDetail());
        Mockito.when(mapper.mapToDetailResponse(DebitCardMWResponseFixture.withDefaultDetail())).thenReturn(expected);

        // Act
        DCDetailResponse response = service.detail(personId, cardId);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).detail(personId, cardId);
        verify(mapper).mapToDetailResponse(DebitCardMWResponseFixture.withDefaultDetail());
    }

    @Test
    void givenValidDataWhenLockStatusThenReturnGenericResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLockStatusRequest request = DebitCardRequestFixture.withDefaultDCLockStatusRequest();
        DCLockStatusMWRequest expectedRequest = DebitCardMWRequestFixture.withDefaultLockStatus();

        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);

        when(provider.lockStatus(Mockito.any())).thenReturn(expected);
        when(mapper.mapToLockStatusRequest(personId, cardId, request)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.lockStatus(personId, cardId, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).lockStatus(expectedRequest);
        verify(mapper).mapToLockStatusRequest(personId, cardId, request);
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenCreateAuthorizationOnlinePurchaseResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        GenericResponse expected = GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.SUCCESS_CREATE);
        CreateAuthorizationOnlinePurchaseRequest request = DebitCardRequestFixture.withDefaultCreateAuthOnlinePurchaseRequest();
        Mockito.when(provider.createAuthorizationOnlinePurchase(Mockito.any())).thenReturn(DebitCardMWResponseFixture.withDefaultCreateAuthorizationOnlinePurchaseMWResponse());

        // Act
        GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request);

        //Assert
        assertEquals(expected, actual);
        Mockito.verify(provider).createAuthorizationOnlinePurchase(any());
        Mockito.verify(mapper).mapToCreateAuthorizationOnlinePurchaseMWRequest(any(), any(), any(), any(), any());
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenErrorCreate() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        CreateAuthorizationOnlinePurchaseRequest request = DebitCardRequestFixture.errorCreateAuthOnlinePurchaseRequest();

        // Act
        try {
            GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request);
        } catch (GenericException ex) {
            //Assert
            assertEquals(ex.getCode(), DebitCardMiddlewareError.END_DATE_MUST_BE_GREATER_THAN_START_DATE.getCode());
            assertEquals(ex.getMessage(), DebitCardMiddlewareError.END_DATE_MUST_BE_GREATER_THAN_START_DATE.getMessage());
            assertEquals(ex.getStatus(), DebitCardMiddlewareError.END_DATE_MUST_BE_GREATER_THAN_START_DATE.getHttpCode());

        }
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenErrorMW() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        GenericResponse expected = GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.ERROR_CREATE);
        CreateAuthorizationOnlinePurchaseRequest request = DebitCardRequestFixture.withDefaultCreateAuthOnlinePurchaseRequest();
        Mockito.when(provider.createAuthorizationOnlinePurchase(Mockito.any())).thenReturn(DebitCardMWResponseFixture.errorCreateAuthorizationOnlinePurchaseMWResponse());

        // Act
        GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request);

        //Assert
        assertEquals(expected, actual);
        Mockito.verify(provider).createAuthorizationOnlinePurchase(any());
        Mockito.verify(mapper).mapToCreateAuthorizationOnlinePurchaseMWRequest(any(), any(), any(), any(), any());
    }

    @Test
    void givenValidDataWhenModifyAccountsOrderThenReturnGenericResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCAccountsOrderRequest request = DebitCardRequestFixture.withDefaultDCAccountsOrderRequest();
        DCAccountsOrderMWRequest expectedRequest = DebitCardMWRequestFixture.withDefaultAccountsOrder();

        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_MODIFY_ACCOUNTS_ORDER);

        when(provider.modifyAccountsOrder(Mockito.any())).thenReturn(expected);
        when(mapper.mapToAccountsOrderRequest(personId, cardId, request)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.modifyAccountsOrder(personId, cardId, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).modifyAccountsOrder(expectedRequest);
        verify(mapper).mapToAccountsOrderRequest(personId, cardId, request);
    }

    @Test
    void givenValidDataWhenChangePinCardThenReturnGenericResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "1234";
        ChangePinRequest request = DebitCardRequestFixture.withDefaultChangePinRequest();
        ChangePinMWRequest expectedRequest = DebitCardMWRequestFixture.withDefaultChangePinCard();

        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_PIN_CARD);

        when(provider.changePinCard(Mockito.any())).thenReturn(expected);
        when(mapper.mapToChangePinRequest(personId, cardId, request)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.changePinCard(personId, cardId, request);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).changePinCard(expectedRequest);
        verify(mapper).mapToChangePinRequest(personId, cardId, request);
    }
}