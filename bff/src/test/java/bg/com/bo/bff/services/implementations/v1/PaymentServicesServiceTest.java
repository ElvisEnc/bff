package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.*;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.services.PaymentServicesMapper;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentServicesMWRequestFixture;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
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
class PaymentServicesServiceTest {
    @InjectMocks
    private PaymentServicesService service;
    @Mock
    private IPaymentServicesProvider provider;
    @Spy
    private PaymentServicesMapper mapper = new PaymentServicesMapper();
    @Mock
    private PaymentServicesService self;

    @Test
    void whenGetCategoriesServiceThenListCategories() throws IOException {
        //Arrange
        List<CategoryResponse> expected = PaymentServiceResponseFixture.withDefaultDataListCategoryResponse().getData();
        CategoryMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultCategoryMWResponse();
        when(provider.getCategories(any())).thenReturn(mwResponse);
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        List<CategoryResponse> response = service.getCategories(new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getCategories(any());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void whenGetCategoriesServiceThenListNull() throws IOException {
        //Arrange
        List<CategoryResponse> expected = new ArrayList<>();
        CategoryMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultCategoryMWResponseNull();
        when(provider.getCategories(any())).thenReturn(mwResponse);

        //Act
        List<CategoryResponse> response = service.getCategories(new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getCategories(any());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void whenGetCategoriesServiceThenDataNull() throws IOException {
        //Arrange
        List<CategoryResponse> expected = new ArrayList<>();
        when(provider.getCategories(any())).thenReturn(null);

        //Act
        List<CategoryResponse> response = service.getCategories(new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getCategories(any());
    }

    @Test
    void givenCategoryIdWhenGetSubcategoriesThenSubcategoriesResponse() throws IOException {
        //Arrange
        SubcategoriesResponse expected = PaymentServiceResponseFixture.withDefaultSubcategoriesResponse();
        when(provider.getSubcategories(any(), any())).thenReturn(PaymentServicesMWResponseFixture.withDefaultSubcategoriesMWResponse());
        when(mapper.convertResponse(PaymentServicesMWResponseFixture.withDefaultSubcategoriesMWResponse())).thenReturn(expected);

        //Act
        SubcategoriesResponse actual = service.getSubcategories(1, new HashMap<>());

        //Assert
        assertEquals(expected.getData().size(), actual.getData().size());
    }

    @Test
    void givenSubCategoryIdWhenGetSubcategoryCitiesThenSubCategoryCitiesResponse() throws IOException {
        //Arrange
        SubCategoryCitiesResponse expected = PaymentServiceResponseFixture.withDefaultSubCategoryCitiesResponse();
        when(provider.getSubcategoryCities(any(), any())).thenReturn(PaymentServicesMWResponseFixture.withDefaultSubCategoryCitiesMWResponse());
        when(mapper.convertResponse(PaymentServicesMWResponseFixture.withDefaultSubCategoryCitiesMWResponse())).thenReturn(expected);

        //Act
        SubCategoryCitiesResponse actual = service.getSubcategoryCities(1, new HashMap<>());

        //Assert
        assertEquals(expected.getData().size(), actual.getData().size());
        verify(provider).getSubcategoryCities(any(), any());
        verify(mapper).convertResponse(PaymentServicesMWResponseFixture.withDefaultSubCategoryCitiesMWResponse());
    }

    @Test
    void givenPersonIdWhenGetAffiliateServicesThenListAffiliations() throws IOException {
        //Arrange
        List<AffiliatedServicesResponse> expected = PaymentServiceResponseFixture.withDataDefaultListAffiliateServiceResponse().getData();
        AffiliatedServiceMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliatedServiceMWResponse();
        when(provider.getAffiliatedServices(any(), any())).thenReturn(mwResponse);

        //Act
        List<AffiliatedServicesResponse> response = service.getAffiliatedServices(123, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliatedServices(123, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenPersonIdWhenGetAffiliateServicesThenListAffiliationsContingencyN() throws IOException {
        //Arrange
        List<AffiliatedServicesResponse> expected = PaymentServiceResponseFixture.withDataDefaultListAffiliateServiceResponseContingencyTrue().getData();
        AffiliatedServiceMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliatedServiceMWResponseContingencyN();
        when(provider.getAffiliatedServices(any(), any())).thenReturn(mwResponse);

        //Act
        List<AffiliatedServicesResponse> response = service.getAffiliatedServices(123, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliatedServices(123, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenPersonIdWhenGetAffiliateServicesThenListNull() throws IOException {
        //Arrange
        List<AffiliatedServicesResponse> expected = new ArrayList<>();
        AffiliatedServiceMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliatedServiceMWResponseNull();
        when(provider.getAffiliatedServices(any(), any())).thenReturn(mwResponse);


        //Act
        List<AffiliatedServicesResponse> response = service.getAffiliatedServices(123, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliatedServices(123, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenPersonIdWhenGetAffiliateServicesThenListDataNull() throws IOException {
        //Arrange
        List<AffiliatedServicesResponse> expected = new ArrayList<>();
        when(provider.getAffiliatedServices(any(), any())).thenReturn(null);

        //Act
        List<AffiliatedServicesResponse> response = service.getAffiliatedServices(123, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliatedServices(123, new HashMap<>());
    }

    @Test
    void givenPersonIdWhenPostServiceAffiliationThenNewAffiliationCode() throws IOException {
        //Arrange
        ServiceAffiliationRequest requestMock = PaymentServiceRequestFixture.withDefaultServiceAffiliationRequest();
        ServiceAffiliationMWRequest mwRequest = PaymentServicesMWRequestFixture.withDefaultServiceAffiliationMWRequest();
        ServiceAffiliationMWResponse responseMock = PaymentServicesMWResponseFixture.withDefaultServiceAffiliationMWResponse();
        ServiceAffiliationResponse expected = PaymentServiceResponseFixture.withDefaultServiceAffiliationResponse();
        when(mapper.mapperRequest("123", requestMock)).thenReturn(mwRequest);
        when(provider.serviceAffiliation(any(), any())).thenReturn(responseMock);
        when(mapper.convertServiceAffiliationResponse(responseMock)).thenReturn(expected);

        //Act
        ServiceAffiliationResponse response = service.serviceAffiliation("123", requestMock, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(mapper).mapperRequest("123", requestMock);
        verify(provider).serviceAffiliation(mwRequest, new HashMap<>());
        verify(mapper).convertServiceAffiliationResponse(responseMock);
    }

    @Test
    void givenPersonIdWhenPostServiceAffiliationThenNewAffiliationCodeIsTemporalTrue() throws IOException {
        //Arrange
        ServiceAffiliationRequest requestMock = PaymentServiceRequestFixture.withDefaultServiceAffiliationRequestIsTemporalTrue();
        ServiceAffiliationMWRequest mwRequest = PaymentServicesMWRequestFixture.withDefaultServiceAffiliationMWRequest();
        ServiceAffiliationMWResponse responseMock = PaymentServicesMWResponseFixture.withDefaultServiceAffiliationMWResponse();
        ServiceAffiliationResponse expected = PaymentServiceResponseFixture.withDefaultServiceAffiliationResponse();
        when(mapper.mapperRequest("123", requestMock)).thenReturn(mwRequest);
        when(provider.serviceAffiliation(any(), any())).thenReturn(responseMock);
        when(mapper.convertServiceAffiliationResponse(responseMock)).thenReturn(expected);

        //Act
        ServiceAffiliationResponse response = service.serviceAffiliation("123", requestMock, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(mapper).mapperRequest("123", requestMock);
        verify(provider).serviceAffiliation(mwRequest, new HashMap<>());
        verify(mapper).convertServiceAffiliationResponse(responseMock);
    }

    @Test
    void givenPersonIdAffiliateIdWhenGetAffiliationDebtsThenListAffiliationsDebts() throws IOException {
        //Arrange
        AffiliationDebtsRequest request = PaymentServiceRequestFixture.withDefaultDebtsRequest();
        DebtsConsultationMWRequest mwRequestMock = PaymentServicesMWRequestFixture.withDefaultDebtsRequestMW();
        DebtsConsultationMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultDebtsResponseMW();
        AffiliationDebtsResponse expected = PaymentServiceResponseFixture.withDefaultDebtsResponse();
        when(mapper.mapperRequest(123, 123, request)).thenReturn(mwRequestMock);
        when(provider.debtsConsultation(any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertDebtsResponse(mwResponseMock)).thenReturn(expected);

        //Act
        AffiliationDebtsResponse response = service.getAffiliationDebts(123, 123, request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        verify(mapper).mapperRequest(123, 123, request);
        verify(provider).debtsConsultation(mwRequestMock, new HashMap<>());
        verify(mapper).convertDebtsResponse(mwResponseMock);
    }

    @Test
    void givenPersonIdAffiliateIdWhenGetAffiliationDebtsThenListAffiliationsDebtsModifyS() throws IOException {
        //Arrange
        AffiliationDebtsRequest request = PaymentServiceRequestFixture.withDefaultDebtsRequest();
        DebtsConsultationMWRequest mwRequestMock = PaymentServicesMWRequestFixture.withDefaultDebtsRequestMW();
        DebtsConsultationMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultDebtsResponseMWModifyS();
        AffiliationDebtsResponse expected = PaymentServiceResponseFixture.withDefaultDebtsResponseModifyTrue();
        when(provider.debtsConsultation(any(), any())).thenReturn(mwResponseMock);

        //Act
        AffiliationDebtsResponse response = service.getAffiliationDebts(123, 123, request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        verify(mapper).mapperRequest(123, 123, request);
        verify(provider).debtsConsultation(mwRequestMock, new HashMap<>());
        verify(mapper).convertDebtsResponse(mwResponseMock);
    }

    @Test
    void givenPersonIdAffiliateIdWhenGetAffiliationDebtsThenListAffiliationsDebtsModifyNull() throws IOException {
        //Arrange
        AffiliationDebtsRequest request = PaymentServiceRequestFixture.withDefaultDebtsRequest();
        DebtsConsultationMWRequest mwRequestMock = PaymentServicesMWRequestFixture.withDefaultDebtsRequestMW();
        DebtsConsultationMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultDebtsResponseMWModifyNull();
        AffiliationDebtsResponse expected = PaymentServiceResponseFixture.withDefaultDebtsResponseModifyNull();
        when(provider.debtsConsultation(any(), any())).thenReturn(mwResponseMock);

        //Act
        AffiliationDebtsResponse response = service.getAffiliationDebts(123, 123, request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(mapper).mapperRequest(123, 123, request);
        verify(provider).debtsConsultation(mwRequestMock, new HashMap<>());
        verify(mapper).convertDebtsResponse(mwResponseMock);
    }

    @Test
    void givenBadYearWhenGetAffiliationDebtsThenBadRequest() throws IOException {
        // Arrange
        AffiliationDebtsRequest request = PaymentServiceRequestFixture.withDefaultDebtsRequestBadYear();
        Map<String, String> parameters = new HashMap<>();

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.getAffiliationDebts(123, 123, request, parameters)
        );

        // Assert
        assertEquals("BAD_REQUEST", exception.getCode());
    }

    @Test
    void givenPersonIdAffiliateIdWhenPaymentDebtsThenSuccess() throws IOException {
        //Arrange
        PaymentDebtsRequest request = PaymentServiceRequestFixture.withDefaultPaymentDebtsRequest();
        PaymentDebtsMWRequest mwRequestMock = PaymentServicesMWRequestFixture.withDefaultPaymentDebtsMWRequest();
        PaymentDebtsMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultPaymentDebtsMWResponse();
        PaymentDebtsResponse expectedResponse = PaymentServiceResponseFixture.withDefaultPaymentDebtsResponse();
        when(mapper.mapperRequest("123", "123", request)).thenReturn(mwRequestMock);
        when(provider.paymentDebts(any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertPaymentResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        PaymentDebtsResponse response = service.paymentDebts("123", "123", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(mapper).mapperRequest("123", "123", request);
        verify(provider).paymentDebts(mwRequestMock, new HashMap<>());
        verify(mapper).convertPaymentResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenGetListServicesThenListServices() throws IOException {
        //Arrange
        List<ServiceResponse> expected = PaymentServiceResponseFixture.withDefaultListServicesResponse();
        ListServicesMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultListServicesMWResponse();
        when(provider.getServicesByCategoryAndCity(any(), any(), any())).thenReturn(mwResponse);
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        List<ServiceResponse> response = service.getServicesByCategoryAndCity(3, 2, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getServicesByCategoryAndCity(3, 2, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenValidDataWhenGetListServicesThenListServicesNull() throws IOException {
        //Arrange
        List<ServiceResponse> expected = new ArrayList<>();
        ListServicesMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultListServicesMWResponseNull();
        when(provider.getServicesByCategoryAndCity(any(), any(), any())).thenReturn(mwResponse);

        //Act
        List<ServiceResponse> response = service.getServicesByCategoryAndCity(3, 2, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getServicesByCategoryAndCity(3, 2, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenValidDataWhenGetListServicesThenListServicesDataNull() throws IOException {
        //Arrange
        List<ServiceResponse> expected = new ArrayList<>();
        when(provider.getServicesByCategoryAndCity(any(), any(), any())).thenReturn(null);

        //Act
        List<ServiceResponse> response = service.getServicesByCategoryAndCity(3, 2, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getServicesByCategoryAndCity(3, 2, new HashMap<>());
    }

    @Test
    void givenPersonIdAndAccountNumberAndAffiliateCodeWhenDeleteAffiliationServiceThenOK() throws IOException {
        //Arrange
        String personId = "12345";
        String affiliateServiceId = "20";
        GenericResponse expected = GenericResponse.instance(DeleteAffiliateServiceResponse.SUCCESS);
        when(mapper.convertRequest(anyString(), anyString())).thenReturn(PaymentServicesMWRequestFixture.withDefaultDeleteAffiliateServiceMWRequest());

        //Act
        GenericResponse actual = service.deleteAffiliationService(personId, affiliateServiceId, new HashMap<>());

        //Assert
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
        verify(mapper).convertRequest(anyString(), anyString());
    }

    @Test
    void givenValidDataWhenGetAffiliateCriteriaThenAffiliateCriteria() throws IOException {
        //Arrange
        AffiliateCriteriaResponse expected = PaymentServiceResponseFixture.withDefaultAffiliateCriteriaResponse();
        AffiliateCriteriaMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliateCriteriaMWResponse();
        when(provider.getAffiliateCriteria(any(), any(), any())).thenReturn(PaymentServicesMWResponseFixture.withDefaultAffiliateCriteriaMWResponse());
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        AffiliateCriteriaResponse response = service.getAffiliateCriteria("123", "85", new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliateCriteria("123", "85", new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenValidDataWhenGetAffiliateCriteriaThenAffiliateCriteriaNull() throws IOException {
        //Arrange
        AffiliateCriteriaResponse expected = PaymentServiceResponseFixture.withDefaultAffiliateCriteriaResponseNull();
        AffiliateCriteriaMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliateCriteriaMWResponseNull();
        when(provider.getAffiliateCriteria(any(), any(), any())).thenReturn(mwResponse);

        //Act
        AffiliateCriteriaResponse response = service.getAffiliateCriteria("123", "85", new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliateCriteria("123", "85", new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }


    @Test
    void givenValidDataWhenGetAffiliateCriteriaThenAffiliateCriteriaDataNull() throws IOException {
        //Arrange
        AffiliateCriteriaResponse expected = PaymentServiceResponseFixture.withDefaultAffiliateCriteriaResponseNull();
        when(provider.getAffiliateCriteria(any(), any(), any())).thenReturn(null);

        //Act
        AffiliateCriteriaResponse response = service.getAffiliateCriteria("123", "85", new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliateCriteria("123", "85", new HashMap<>());
    }

    @Test
    void givenValidDataWhenValidateAffiliateCriteriaThenValidateAffiliateCriteria() throws IOException {
        //Arrange
        ValidateAffiliateCriteriaRequest request = PaymentServiceRequestFixture.withDefaultValidateAffiliateCriteria();
        ValidateAffiliateCriteriaResponse expected = PaymentServiceResponseFixture.withDefaultValidateAffiliateCriteriaResponse();
        ValidateAffiliateCriteriaMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultValidateAffiliateCriteriaResponse();
        when(provider.validateAffiliateCriteria(any(), any())).thenReturn(PaymentServicesMWResponseFixture.withDefaultValidateAffiliateCriteriaResponse());
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        ValidateAffiliateCriteriaResponse response = service.validateAffiliateCriteria("123", "85", request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(mapper).mapperRequest("123", "85", request);
        verify(provider).validateAffiliateCriteria(PaymentServicesMWRequestFixture.withDefaultValidateAffiliateCriteria(), new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenServicesRequestWithFiltersWhenGetServicesThenListFilteredServices() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(self).getServiceCache(any(), eq("payment_services"), eq(true));
    }

    @Test
    void givenNullServicesRequestWithFiltersWhenGetServicesThenListFilteredServices() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultNullListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
        verify(self).getServiceCache(any(), eq("payment_services"), eq(true));
    }

    @Test
    void givenEmptyServicesRequestWithFiltersWhenGetServicesThenListFilteredServices() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultEmptyListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
    }

    @Test
    void givenServicesRequestFieldWithFiltersWhenGetServicesThenListFilteredServices() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultSearchListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
    }

    @Test
    void givenServicesRequestOrderFalseWithFiltersWhenGetServicesThenListFilteredServices() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultOrderFalseListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
    }

    @Test
    void givenServicesRequestWithFiltersWhenGetServicesThenListFilteredServicesNull() throws IOException {
        //Arrange
        ListServiceRequest request = PaymentServiceRequestFixture.withDefaultSearchListServiceRequest();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponseNull();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getServiceCache(any(), any(), anyBoolean())).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getListService(request, new HashMap<>());

        //Assert
        assertNotNull(response);
    }

    @Test
    void givenServicesRequestWhenGetServicesThenListServices() throws IOException {
        //Arrange
        ListServicesMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultListServiceMWResponse();
        List<ServiceResponse> expectedResponse = PaymentServiceResponseFixture.withDefaultListServiceResponse();

        when(provider.getListService(any())).thenReturn(mwResponseMock);
        when(mapper.convertResponse(mwResponseMock)).thenReturn(expectedResponse);

        //Act
        List<ServiceResponse> response = service.getServiceCache(new HashMap<>(), "payment_services", false);

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListService(new HashMap<>());
        verify(mapper).convertResponse(mwResponseMock);
    }
}