package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponseFixture;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubCategoryCitiesResponseFixture;
import bg.com.bo.bff.application.dtos.response.payment.services.SubcategoriesResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubCategoryCitiesMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.mappings.payment.services.IPaymentServicesMapper;
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
        List<CategoryResponse> expected = CategoryResponseFixture.withDefaultData().getData();
        CategoryMWResponse mwResponse = CategoryMWResponseFixture.withDefault();
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
        SubcategoriesResponse expected = SubcategoriesResponseFixture.withDefault();
        when(provider.getSubcategories(any(), any())).thenReturn(SubcategoriesMWResponseFixture.withDefault());

        when(mapper.convertResponse(SubcategoriesMWResponseFixture.withDefault())).thenReturn(expected);
        //Act
        SubcategoriesResponse actual = service.getSubcategories(1, new HashMap<>());

        //Assert
        assertEquals(expected.getData().size(), actual.getData().size());

    }

    @Test
    void givenSubCategoryIdWhenGetSubcategoryCitiesThenSubCategoryCitiesResponse() throws IOException {
        //Arrange
        SubCategoryCitiesResponse expected = SubCategoryCitiesResponseFixture.withDefault();
        when(provider.getSubcategoryCities(any(), any())).thenReturn(SubCategoryCitiesMWResponseFixture.withDefault());
        when(mapper.convertResponse(SubCategoryCitiesMWResponseFixture.withDefault())).thenReturn(expected);

        //Act
        SubCategoryCitiesResponse actual = service.getSubcategoryCities(1, new HashMap<>());

        //Assert
        assertEquals(expected.getData().size(), actual.getData().size());
        verify(provider).getSubcategoryCities(any(), any());
        verify(mapper).convertResponse(SubCategoryCitiesMWResponseFixture.withDefault());
    }
}