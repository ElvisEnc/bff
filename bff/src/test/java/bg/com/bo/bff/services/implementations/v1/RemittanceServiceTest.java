package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.RemittanceRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.*;
import bg.com.bo.bff.mappings.providers.remittance.RemittanceMapper;
import bg.com.bo.bff.providers.dtos.request.remittance.RemittanceMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.remittance.RemittanceMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.CheckRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.DepositRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;
import bg.com.bo.bff.providers.interfaces.IRemittanceProvider;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {
    @InjectMocks
    private RemittanceService service;
    @Mock
    private IRemittanceProvider provider;
    @Spy
    private RemittanceMapper mapper = new RemittanceMapper();
    @Mock
    private RemittanceService self;

    @Test
    void givenValidDataWhenGetGeneralParametersByPersonIdThenListGeneralParametersResponse() throws IOException {
        //Arrange
        ListGeneralParametersResponse expectedResponse = RemittanceResponseFixture.withDefaultGeneralParameters();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getGeneralParametersData(any())).thenReturn(expectedResponse);

        //Act
        ListGeneralParametersResponse response = service.getGeneralParameters("169494");

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getGeneralParametersData(any());
    }

    @Test
    void givenValidDataWhenGetGeneralParamtersByPersonIdThenListGeneralParametersResponseCache() throws IOException {
        //Arrange
        ListGeneralParametersMWResponse mwResponseMock = RemittanceMWResponseFixture.withDefaultGeneralParameters();
        ListGeneralParametersResponse expectedResponse = RemittanceResponseFixture.withDefaultGeneralParameters();
        when(provider.getGeneralParameters(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        ListGeneralParametersResponse response = service.getGeneralParametersData("123456");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getGeneralParameters(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenValidateAccountThenGenericResponse() throws IOException {
        //Arrange
        GenericResponse expected = GenericResponse.instance(RemittanceMiddlewareResponse.ACCOUNT_ENABLED);

        when(provider.validateAccount(any())).thenReturn(RemittanceMWResponseFixture.withDefaultValidateAccount());
        when(mapper.mapperRequest(any(), any())).thenReturn(RemittanceMWRequestFixture.withDefaultValidateAccount());

        // Act
        GenericResponse response = service.validateAccount("161616", "123456");

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).validateAccount(RemittanceMWRequestFixture.withDefaultValidateAccount());
    }

    @Test
    void givenValidDataWhenGetMoneyOrdersSentThenListMoneyOrdersSentResponse() throws IOException {
        //Arrange
        MoneyOrderSentMWResponse mwResponseMock = RemittanceMWResponseFixture.withDefaultMoneyOrdersSent();
        List<MoneyOrderSentResponse> expectedResponse = RemittanceResponseFixture.withDataDefaultListMoneyOrderSentResponse();
        when(provider.getMoneyOrdersSent(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<MoneyOrderSentResponse> response = service.getMoneyOrdersSent("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getMoneyOrdersSent(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenGetMoneyOrdersSentThenEmptyListResponse() throws IOException {
        //Arrange
        MoneyOrderSentMWResponse mwResponseMock = MoneyOrderSentMWResponse.builder().build();
        List<MoneyOrderSentResponse> expectedResponse = new ArrayList<>(Collections.emptyList());
        when(provider.getMoneyOrdersSent(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<MoneyOrderSentResponse> response = service.getMoneyOrdersSent("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getMoneyOrdersSent(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenCheckRemittanceThenListCheckRemittanceResponse() throws IOException {
        //Arrange
        CheckRemittanceMWResponse mwResponseMock = RemittanceMWResponseFixture.withDefaultCheckRemittance();
        List<CheckRemittanceResponse> expectedResponse = RemittanceResponseFixture.withDataDefaultListCheckRemittanceResponse();
        when(provider.checkRemittance(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<CheckRemittanceResponse> response = service.checkRemittance("123", "123456789");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).checkRemittance(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidRequestWhenCheckRemittanceThenEmptyListResponse() throws IOException {
        //Arrange
        CheckRemittanceMWResponse mwResponseMock = CheckRemittanceMWResponse.builder().build();
        List<CheckRemittanceResponse> expectedResponse = new ArrayList<>(Collections.emptyList());
        when(provider.checkRemittance(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<CheckRemittanceResponse> response = service.checkRemittance("123", "123456789");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).checkRemittance(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenDepositRemittanceThenListDepositRemittanceResponse() throws IOException {
        //Arrange
        DepositRemittanceMWResponse mwResponseMock = RemittanceMWResponseFixture.withDefaultDepositRemittance();
        DepositRemittanceRequest request = RemittanceRequestFixture.withDefaultDepositRemittanceRequest();
        List<DepositRemittanceResponse> expectedResponse = RemittanceResponseFixture.withDataDefaultListDepositRemittanceResponse();
        when(provider.depositRemittance(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<DepositRemittanceResponse> response = service.depositRemittance("123", "123456789", request);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).depositRemittance(any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidRequestWhenDepositRemittanceThenEmptyListResponse() throws IOException {
        //Arrange
        DepositRemittanceMWResponse mwResponseMock = DepositRemittanceMWResponse.builder().build();
        DepositRemittanceRequest request = RemittanceRequestFixture.withDefaultDepositRemittanceRequest();
        List<DepositRemittanceResponse> expectedResponse = new ArrayList<>(Collections.emptyList());
        when(provider.depositRemittance(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<DepositRemittanceResponse> response = service.depositRemittance("123", "123456789", request);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).depositRemittance(any());
        verify(mapper).convertResponse(mwResponseMock);
    }
}