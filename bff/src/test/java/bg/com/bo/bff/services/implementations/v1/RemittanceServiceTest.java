package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.application.dtos.response.remittance.RemittanceResponseFixture;
import bg.com.bo.bff.mappings.providers.remittance.RemittanceMapper;
import bg.com.bo.bff.providers.dtos.request.remittance.RemittanceMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.remittance.RemittanceMWResponseFixture;
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
}