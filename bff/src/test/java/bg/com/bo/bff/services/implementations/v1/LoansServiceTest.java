package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoansRequestFixture;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoansResponseFixture;
import bg.com.bo.bff.mappings.providers.loans.LoansMapper;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoanPaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoansMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoansServiceTest {
    @InjectMocks
    private LoansService service;
    @Mock
    private ILoansProvider provider;
    @Spy
    private LoansMapper mapper = new LoansMapper();
    @Mock
    private LoansService self;

    @Test
    void givenValidDataWhenGetListLoansByPersonIdThenListLoansResponse() throws IOException {
        //Arrange
        ListLoansRequest request = LoansRequestFixture.withDefaultListLoansRequest();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ListLoansResponse> response = service.getListLoansByPerson("123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getServiceCache(any(), any(), eq(true));
    }

    @Test
    void givenOrderAndPaginationNullWhenGetListLoansByPersonIdThenListLoansResponse() throws IOException {
        //Arrange
        ListLoansRequest request = LoansRequestFixture.withDefaultListLoansRequestNull();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ListLoansResponse> response = service.getListLoansByPerson("123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getServiceCache(any(), any(), eq(true));
    }

    @Test
    void givenFieldLoanNumberWhenGetListLoansByPersonIdThenListLoansResponse() throws IOException {
        //Arrange
        ListLoansRequest request = LoansRequestFixture.withDefaultListLoansRequestLoanNumber();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ListLoansResponse> response = service.getListLoansByPerson("123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getServiceCache(any(), any(), eq(true));
    }

    @Test
    void givenValidDataWhenGetListLoansByPersonIdThenListLoansResponseCache() throws IOException {
        //Arrange
        ListLoansMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoansMWResponse();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        when(provider.getListLoansByPerson(any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<ListLoansResponse> response = service.getServiceCache("123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListLoansByPerson(any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenGetListLoansByPersonIdThenListLoansResponseCacheNull() throws IOException {
        //Arrange
        ListLoansMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoansMWResponseNull();
        when(provider.getListLoansByPerson(any(), any())).thenReturn(mwResponseMock);

        //Act
        List<ListLoansResponse> response = service.getServiceCache("123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoansByPerson(any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenGetListLoansByPersonIdThenNull() throws IOException {
        //Arrange
        when(provider.getListLoansByPerson(any(), any())).thenReturn(null);

        //Act
        List<ListLoansResponse> response = service.getServiceCache("123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoansByPerson(any(), any());
    }

    @Test
    void givenLoanPaymentsRequestWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequest();
        List<LoanPaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanPaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanPaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getLoanPaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenOrderAndPaginationNullWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestNull();
        List<LoanPaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanPaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanPaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getLoanPaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenOrderFilterRequestWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestOrderFilter();
        List<LoanPaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanPaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanPaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(self).getLoanPaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenOrderCapitalPaidRequestWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestOrderFilterCapitalPaid();
        List<LoanPaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanPaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanPaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(self).getLoanPaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenOrderDateRequestWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestOrderFilterDate();
        List<LoanPaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanPaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanPaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(self).getLoanPaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenLoanPaymentsRequestWhenGetLoanPaymentsThenListLoansPaymentsCache() throws IOException {
        //Arrange
        ListLoanPaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoanPaymentsMWResponse();
        List<LoanPaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPaymentsResponse();
        when(provider.getListLoanPayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListLoanPayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }


    @Test
    void givenLoanPaymentsRequestWhenGetLoanPaymentsThenListLoansPaymentsCacheNull() throws IOException {
        //Arrange
        ListLoanPaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoanPaymentsMWResponseNull();
        when(provider.getListLoanPayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoanPayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenLoanPaymentsRequestWhenGetLoanPaymentsThenNull() throws IOException {
        //Arrange
        when(provider.getListLoanPayments(any(), any(), any())).thenReturn(null);

        //Act
        List<LoanPaymentsResponse> response = service.getLoanPaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoanPayments(any(), any(), any());
    }
}