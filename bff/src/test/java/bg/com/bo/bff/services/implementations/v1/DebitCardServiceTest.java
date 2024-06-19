package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequestFixture;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequestFixture;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLockStatusRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLockStatusRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.*;
import bg.com.bo.bff.application.dtos.response.InternetAuthorizationResponseFixture;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponseFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLockStatusMWRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DebitCardMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.DebitCardMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.CreateAuthorizationOnlinePurchaseMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.providers.mappings.debit.card.IDebitCardMapper;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceTest {
    @Spy
    @InjectMocks
    private DebitCardService service;

    @Mock
    private IDebitCardProvider provider;

    @Mock
    private IDebitCardMapper mapper;

    @Test
    void givenValidDataWhenChangeAmountThenReturnSuccess() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLimitsRequest request = DCLimitsRequestFixture.withDefault();
        DCLimitsMWRequest expectedRequest = DebitCardMWRequestFixture.withDefault();
        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);

        when(provider.changeAmount(any(), any())).thenReturn(GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT));
        when(mapper.mapToLimitsRequest(request, personId, cardId)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.changeAmount(personId, cardId, request, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).changeAmount(expectedRequest, new HashMap<>());
        verify(mapper).mapToLimitsRequest(request, personId, cardId);
    }

    @Test
    void givenPersonCodeWhenGetListDebitCardThenSuccess() throws IOException {
        // Arrange
        ListDebitCardMWResponse mwResponseMock = ListDebitCardMWResponseFixture.withDefault();
        ListDebitCardResponse expectedResponse = ListDebitCardResponseFixture.withDefault();
        when(provider.listDebitCard(any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertResponseListDebitCard(any())).thenReturn(List.of(DebitCardFixture.withDefault(), DebitCardFixture.withDefault()));

        // Act
        ListDebitCardResponse response = service.getListDebitCard(123, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).listDebitCard(123, new HashMap<>());
        verify(mapper).convertResponseListDebitCard(mwResponseMock);
    }

    @Test
    void givenPersonCodeAndCardIdWhenGetAccountListDebitCardThenSuccess() throws IOException {
        // Arrange
        AccountsDebitCardMWResponse mwResponseMock = AccountsDebitCardMWResponseFixture.withDefault();
        ListAccountTDResponse expectedResponse = ListAccountTDResponseFixture.withDefault();
        when(provider.accountListDebitCard(any(),any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertResponseAccountListTD(any())).thenReturn(List.of(AccountTDFixture.withDefault(),AccountTDFixture.withDefault()));

        // Act
        ListAccountTDResponse response = service.getAccountsTD(123,123, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).accountListDebitCard(123,123, new HashMap<>());
        verify(mapper).convertResponseAccountListTD(mwResponseMock);
    }

    @Test
    void givenPersonIdAndCardIdWhenGetListAuthorizationsThenDCInternetAuthorizationNWResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        InternetAuthorizationResponse expected = InternetAuthorizationResponseFixture.withDefault();

        when(provider.getListAuthorizations(any(), any(), any())).thenReturn(DCInternetAuthorizationNWResponseFixture.withDefault());
        when(mapper.mapToInternetAuthorizationResponse(any())).thenReturn(expected);

        // Act
        InternetAuthorizationResponse response = service.getListAuthorizations(personId, cardId, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getListAuthorizations(any(), any(), any());
    }

    @Test
    void givenValidDataWhenDetailThenReturnDCDetailResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCDetailResponse expected = DCDetailResponseFixture.withDefault();

        Mockito.when(provider.detail(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(DebitCardMWResponseFixture.withDefaultDetail());
        Mockito.when(mapper.mapToDetailResponse(DebitCardMWResponseFixture.withDefaultDetail())).thenReturn(expected);

        // Act
        DCDetailResponse response = service.detail(personId, cardId, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).detail(personId, cardId, new HashMap<>());
        verify(mapper).mapToDetailResponse(DebitCardMWResponseFixture.withDefaultDetail());
    }

    @Test
    void givenValidDataWhenLockStatusThenReturnGenericResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLockStatusRequest request = DCLockStatusRequestFixture.withDefault();
        DCLockStatusMWRequest expectedRequest = DebitCardMWRequestFixture.withDefaultLockStatus();

        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);

        when(provider.lockStatus(Mockito.any(), Mockito.any())).thenReturn(expected);
        when(mapper.mapToLockStatusRequest(personId, cardId, request)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.lockStatus(personId, cardId, request, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).lockStatus(expectedRequest, new HashMap<>());
        verify(mapper).mapToLockStatusRequest(personId, cardId, request);
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenCreateAuthorizationOnlinePurchaseResponse() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        GenericResponse expected = GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.SUCCESS_CREATE);
        CreateAuthorizationOnlinePurchaseRequest request = CreateAuthorizationOnlinePurchaseRequestFixture.withDefault();

        Mockito.when(provider.createAuthorizationOnlinePurchase(Mockito.any(), Mockito.any())).thenReturn(CreateAuthorizationOnlinePurchaseMWResponseFixture.withDefault());
        Mockito.when(mapper.mapToCreateAuthorizationOnlinePurchaseMWRequest(any(), any(),any(),any(),any())).thenReturn(CreateAuthorizationOnlinePurchaseMWRequestFixture.withDefault());

        // Act
        GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request, new HashMap<>());

        //Assert
        assertEquals(expected, actual);
        Mockito.verify(provider).createAuthorizationOnlinePurchase(Mockito.any(), Mockito.any());
        Mockito.verify(mapper).mapToCreateAuthorizationOnlinePurchaseMWRequest(any(),any(),any(),any(),any());
    }

    @Test
    void givenCreateAuthorizationOnlinePurchaseMWRequestWhenCreateAuthorizationOnlinePurchaseThenErrorCreate() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        CreateAuthorizationOnlinePurchaseRequest request = CreateAuthorizationOnlinePurchaseRequestFixture.errorCreate();

        // Act
        try {
            GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request, new HashMap<>());
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
        CreateAuthorizationOnlinePurchaseRequest request = CreateAuthorizationOnlinePurchaseRequestFixture.withDefault();

        Mockito.when(provider.createAuthorizationOnlinePurchase(Mockito.any(), Mockito.any())).thenReturn(CreateAuthorizationOnlinePurchaseMWResponseFixture.errorMW());
        Mockito.when(mapper.mapToCreateAuthorizationOnlinePurchaseMWRequest(any(),any(),any(),any(),any())).thenReturn(CreateAuthorizationOnlinePurchaseMWRequestFixture.withDefault());

        // Act
        GenericResponse actual = service.createAuthorizationOnlinePurchase(personId, cardId, request, new HashMap<>());

        //Assert
        assertEquals(expected, actual);
        Mockito.verify(provider).createAuthorizationOnlinePurchase(Mockito.any(), Mockito.any());
        Mockito.verify(mapper).mapToCreateAuthorizationOnlinePurchaseMWRequest(any(),any(),any(),any(),any());
    }


}