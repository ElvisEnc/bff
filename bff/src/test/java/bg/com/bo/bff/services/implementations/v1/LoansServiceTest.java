package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoansRequestFixture;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.loans.LoansMapper;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    Map<String, String> map = new HashMap<>();

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

    // Loan Payment
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
        LoanPaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoanPaymentsMWResponse();
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
        LoanPaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultListLoanPaymentsMWResponseNull();
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

    // Loan Insurance Payments
    @Test
    void givenLoanPaymentsRequestWhenGetListLoanInsurancePaymentThenListLoanInsurancePaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanInsurancePaymentsRequest();
        List<LoanInsurancePaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanInsurancePaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getLoanInsurancePaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenDateOrderAndPaginationNullWhenGetListLoanInsurancePaymentThenListLoanInsurancePaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestNull();
        List<LoanInsurancePaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanInsurancePaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getLoanInsurancePaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenOrderDateAndFalseWhenGetListLoanInsurancePaymentThenListLoanInsurancePaymentsResponse() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanInsurancePaymentsRequestOrderFilter();
        List<LoanInsurancePaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanInsurancePaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePayments("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getLoanInsurancePaymentsCache(any(), any(), any(), any(), any());
    }

    @Test
    void givenLoanPaymentsRequestWhenGetLoanInsurancePaymentsThenListLoanInsurancePaymentsCache() throws IOException {
        //Arrange
        LoanInsurancePaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultLoanInsurancePaymentsMWResponse();
        List<LoanInsurancePaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse();
        when(provider.getListLoanInsurancePayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListLoanInsurancePayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenLoanInsurancePaymentsRequestWhenGetLoanPaymentsThenListLoanInsurancePaymentsCacheNull() throws IOException {
        //Arrange
        LoanInsurancePaymentsMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultLoanInsurancePaymentsMWResponseNull();
        when(provider.getListLoanInsurancePayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoanInsurancePayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenLoanInsurancePaymentsRequestWhenGetLoanInsurancePaymentsThenNull() throws IOException {
        //Arrange
        when(provider.getListLoanInsurancePayments(any(), any(), any())).thenReturn(null);

        //Act
        List<LoanInsurancePaymentsResponse> response = service.getLoanInsurancePaymentsCache("123", "123", "123", new HashMap<>(), false);

        //Assert
        assertNotNull(response);
        verify(provider).getListLoanInsurancePayments(any(), any(), any());
    }

    // Generic Exception
    @Test
    void givenOrderInvalidRequestWhenGetListLoanInsurancePaymentThenGenericException() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestOrderFilter();
        List<LoanInsurancePaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanInsurancePaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.getLoanInsurancePayments("123", "123", request, map)
        );

        //Assert
        assertEquals("BAD_REQUEST", exception.getCode());
    }

    @Test
    void givenDateInvalidRequestWhenGetListLoanInsurancePaymentThenGenericException() throws IOException {
        //Arrange
        LoanPaymentsRequest request = LoansRequestFixture.withDefaultLoanPaymentsRequestDateInvalid();
        List<LoanInsurancePaymentsResponse> expectedResponse = new ArrayList<>(LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getLoanInsurancePaymentsCache(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.getLoanInsurancePayments("123", "123", request, map)
        );

        //Assert
        assertEquals("BAD_REQUEST", exception.getCode());
    }

    // Loan Plans
    @Test
    void givenLoanPlanRequestWhenGetListLoanPlansThenListLoanPlansResponse() throws IOException {
        //Arrange
        LoanPlanMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultLoanPlanMWResponse();
        List<LoanPlanResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPlanResponse();
        when(provider.getLoanPlansPayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanPlanResponse> response = service.getLoanPlans("123", "123", map);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getLoanPlansPayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenLoanPlanRequestWhenGetListLoanPlansThenListLoanPlansResponseNull() throws IOException {
        //Arrange
        LoanPlanMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultLoanPlanMWResponseNull();
        when(provider.getLoanPlansPayments(any(), any(), any())).thenReturn(mwResponseMock);

        //Act
        List<LoanPlanResponse> response = service.getLoanPlans("123", "123", map);

        //Assert
        assertNotNull(response);
        verify(provider).getLoanPlansPayments(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }

    @Test
    void givenLoanPlanRequestWhenGetListLoanPlansThenNull() throws IOException {
        //Arrange
        when(provider.getLoanPlansPayments(any(), any(), any())).thenReturn(null);

        //Act
        List<LoanPlanResponse> response = service.getLoanPlans("123", "123", map);

        //Assert
        assertNotNull(response);
        verify(provider).getLoanPlansPayments(any(), any(), any());
    }

    @Test
    void givenLoanIdAndPersonIdWhenGetLoanDetailPaymentThenLoanDetailPaymentResponse() throws IOException {
        //Arrange
        LoanDetailPaymentMWResponse mwResponseMock = LoansMWResponseFixture.withDefaultLoanDetailPaymentMWResponse();
        LoanDetailPaymentResponse expectedResponse = LoansResponseFixture.withDefaultLoanDetailPaymentResponse();
        when(provider.getLoanDetailPayment(any(), any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        LoanDetailPaymentResponse response = service.getLoanDetailPayment("123", "123", new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(provider).getLoanDetailPayment(any(), any(), any());
        verify(mapper).convertResponse(mwResponseMock);
    }
}