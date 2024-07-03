package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.PaymentServiceResponseFixture;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.ListServicesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.DeleteAffiliateServiceResponse;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentServicesMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.PaymentServicesMWResponseFixture;
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
}