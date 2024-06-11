package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.InternetAuthorizationResponseFixture;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class DebitCardControllerTest {
    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private DebitCardController controller;
    @Mock
    private IDebitCardService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    HttpHeaders headers = new HttpHeaders();

    private ObjectMapper objectMapper;

    private static String GET_LIST_AUTHORIZATIONS = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/authorizations";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    }

    @Test
    void givenValidDataWhenChangeAmountThenThenGenericResponseSuccess() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        DCLimitsRequest request = DCLimitsRequestFixture.withDefault();
        GenericResponse mockResponse = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);
        when(service.changeAmount(any(), any(), any(), any())).thenReturn(mockResponse);

        // Act
        String URL_PATCH_CHANGE_AMOUNT = "/api/v1/debit-cards/persons/{personId}/cards/{cardId}/limits";
        mockMvc.perform(patch(URL_PATCH_CHANGE_AMOUNT, personId, cardId)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        verify(service).changeAmount(any(), any(), any(), any());
    }

    @Test
    void givenPersonIdAndCardIdWhengetListAuthorizationsThenDCInternetAuthorizationNWResponse() throws Exception {
        // Arrange
        String personId = "169494";
        String cardId = "123456";
        InternetAuthorizationResponse expected = InternetAuthorizationResponseFixture.withDefault();
        when(service.getListAuthorizations(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get(GET_LIST_AUTHORIZATIONS, personId, cardId)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();
        // Assert
        assertEquals(response, actual );
        verify(service).getListAuthorizations(any(), any(), any());
    }
}
