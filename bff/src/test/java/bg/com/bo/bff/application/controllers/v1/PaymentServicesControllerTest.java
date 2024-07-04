package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.DebtsRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentServicesControllerTest {
    private static final String GET_SUB_CATEGORIES = "/api/v1/payment-services/categories/{categoryId}/subcategories";
    private static final String GET_SUBCATEGORY_CITIES = "/api/v1/payment-services/subcategories/{subCategoryId}/cities";
    private static final String DELETE_AFFILIATE_SERVICE = "/api/v1/payment-services/persons/{personId}/affiliate-services/{affiliateServiceId}";

    private MockMvc mockMvc;
    @InjectMocks
    private PaymentServicesController controller;
    @Mock
    private IPaymentServicesService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    Enumeration<String> enumerations;
    private ObjectMapper objectMapper;
    private final HttpHeaders headers = new HttpHeaders();
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), DEVICE_ID,
                DeviceMW.DEVICE_IP.getCode(), DEVICE_IP,
                DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME,
                DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X,
                DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y,
                DeviceMW.APP_VERSION.getCode(), APP_VERSION
        );
        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        enumerations = lists.elements();
        headers.add(DeviceMW.DEVICE_ID.getCode(), DEVICE_ID);
        headers.add(DeviceMW.DEVICE_IP.getCode(), DEVICE_IP);
        headers.add(DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME);
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X);
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y);
        headers.add(DeviceMW.APP_VERSION.getCode(), APP_VERSION);
    }

    @Test
    void whenGetCategoriesThenResponseListCategories() throws Exception {
        //Arrange
        ApiDataResponse<List<CategoryResponse>> expectedResponse = PaymentServiceResponseFixture.withDefaultDataListCategoryResponse();
        when(service.getCategories(any())).thenReturn(expectedResponse.getData());
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        // Act
        String path = "/api/v1/payment-services/categories";
        MvcResult result = mockMvc.perform(get(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getCategories(any());
    }

    @Test
    void givenCategoryIdWhenGetSubcategoriesThenSubcategoriesResponse() throws Exception {

        //Arrange
        SubcategoriesResponse expected = PaymentServiceResponseFixture.withDefaultSubcategoriesResponse();
        when(service.getSubcategories(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_SUB_CATEGORIES, "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getSubcategories(any(), any());
        assertNotNull(result);
    }

    @Test
    void givenSubCategoryIdWhenGetSubcategoryCitiesThenSubCategoryCitiesResponse() throws Exception {

        //Arrange
        SubCategoryCitiesResponse expected = PaymentServiceResponseFixture.withDefaultSubCategoryCitiesResponse();
        when(service.getSubcategoryCities(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_SUBCATEGORY_CITIES, "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getSubcategoryCities(any(), any());
        assertNotNull(result);
    }

    @Test
    void givenPersonIdWhenGetAffiliateServiceThenResponseListAffiliations() throws Exception {
        //Arrange
        ApiDataResponse<List<AffiliateServiceResponse>> expectedResponse = PaymentServiceResponseFixture.withDataDefaultListAffiliateServiceResponse();
        when(service.getAffiliateServices(any(), any())).thenReturn(expectedResponse.getData());
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        // Act
        String path = "/api/v1/payment-services/persons/{personId}/affiliate-services";
        MvcResult result = mockMvc.perform(get(path, 123)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getAffiliateServices(any(), any());
    }

    @Test
    void givenPersonIdAffiliateIdWhenGetAffiliationDebtsServiceThenResponseListDebts() throws Exception {
        //Arrange
        DebtsRequest requestMock = PaymentServiceResponseFixture.withDefaultDebtsRequest();
        DebtsResponse expectedResponse = PaymentServiceResponseFixture.withDefaultDebtsResponse();
        when(service.getAffiliationDebts(any(), any(), any(), any())).thenReturn(expectedResponse);
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        // Act
        String path = "/api/v1/payment-services/persons/{personId}/affiliate-services/{affiliateId}/debts";
        MvcResult result = mockMvc.perform(post(path, 123, 123)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getAffiliationDebts(any(), any(), any(), any());
    }

    @Test
    void givenValidDataWhenGetListServicesByCityThenResponse() throws Exception {
        //Arrange
        ListServicesResponse expectedResponse = PaymentServiceResponseFixture.withDefaultListServicesResponse();
        when(service.getServicesByCategoryAndCity(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/payment-services/subcategories/{subCategoryId}/cities/{cityId}";
        MvcResult result = mockMvc.perform(get(path, 1, 2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getServicesByCategoryAndCity(any(), any(), any());
    }

    @Test
    void givenPersonIdAndAccountNumberAndAffiliateCodeWhenDeleteAffiliationServiceThenOK() throws Exception {
        // Arrange
        String personId = "12345";
        String affiliateServiceId = "20";
        GenericResponse expected = GenericResponse.instance(DeleteAffiliateServiceResponse.SUCCESS);
        when(service.deleteAffiliationService(anyString(), anyString(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(delete(DELETE_AFFILIATE_SERVICE, personId, affiliateServiceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).deleteAffiliationService(anyString(), anyString(), any());

    }

    @Test
    void givenValidDataWhenGetAffiliateCriteriaThenResponse() throws Exception {
        //Arrange
        AffiliateCriteriaResponse expectedResponse = PaymentServiceResponseFixture.withDefaultAffiliateCriteriaResponse();
        when(service.getAffiliateCriteria(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/payment-services/persons/{personId}/affiliate-criteria";
        MvcResult result = mockMvc.perform(get(path, 1)
                        .param("serviceCode", "85")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expectedResponse);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getAffiliateCriteria(any(), any(), any());
    }
}