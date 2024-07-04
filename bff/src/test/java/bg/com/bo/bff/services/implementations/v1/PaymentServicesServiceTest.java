package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.DebtsRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentServicesMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.mappings.providers.services.IPaymentServicesMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServicesServiceTest {
    @Spy
    @InjectMocks
    private PaymentServicesService service;
    @Mock
    private IPaymentServicesProvider provider;
    @Mock
    private IPaymentServicesMapper mapper;

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
        verify(provider).getCategories(new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
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
        List<AffiliateServiceResponse> expected = PaymentServiceResponseFixture.withDataDefaultListAffiliateServiceResponse().getData();
        AffiliatedServiceMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultAffiliatedServiceMWResponse();
        when(provider.getAffiliationsServices(any(), any())).thenReturn(mwResponse);
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        List<AffiliateServiceResponse> response = service.getAffiliateServices(123, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getAffiliationsServices(123, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
    }

    @Test
    void givenPersonIdAffiliateIdWhenGetAffiliationDebtsThenListAffiliationsDebts() throws IOException {
        //Arrange
        DebtsRequest request = PaymentServiceResponseFixture.withDefaultDebtsRequest();
        DebtsConsultationMWRequest mwRequestMock = PaymentServicesMWResponseFixture.withDefaultDebtsRequestMW();
        DebtsConsultationMWResponse mwResponseMock = PaymentServicesMWResponseFixture.withDefaultDebtsResponseMW();
        DebtsResponse expected = PaymentServiceResponseFixture.withDefaultDebtsResponse();
        when(mapper.mapperRequest(123, 123, request)).thenReturn(mwRequestMock);
        when(provider.debtsConsultation(any(), any())).thenReturn(mwResponseMock);
        when(mapper.convertDebtsResponse(mwResponseMock)).thenReturn(expected);

        //Act
        DebtsResponse response = service.getAffiliationDebts(123, 123, request, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(mapper).mapperRequest(123, 123, request);
        verify(provider).debtsConsultation(mwRequestMock, new HashMap<>());
        verify(mapper).convertDebtsResponse(mwResponseMock);
    }

    @Test
    void givenValidDataWhenGetListServicesThenListServices() throws IOException {
        //Arrange
        ListServicesResponse expected = PaymentServiceResponseFixture.withDefaultListServicesResponse();
        ListServicesMWResponse mwResponse = PaymentServicesMWResponseFixture.withDefaultListServicesMWResponse();
        when(provider.getServicesByCategoryAndCity(any(), any(), any())).thenReturn(mwResponse);
        when(mapper.convertResponse(mwResponse)).thenReturn(expected);

        //Act
        ListServicesResponse response = service.getServicesByCategoryAndCity(3, 2, new HashMap<>());

        //Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getServicesByCategoryAndCity(3, 2, new HashMap<>());
        verify(mapper).convertResponse(mwResponse);
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
}