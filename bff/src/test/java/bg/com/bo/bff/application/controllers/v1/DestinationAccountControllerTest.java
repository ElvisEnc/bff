package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.requests.AddThirdAccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddThirdAccountResponse;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
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

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DestinationAccountControllerTest {

    private static final String URL = "/api/v1/destination-accounts/1234567/third-accounts";
    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private DestinationAccountController controller;

    @Mock
    private IDestinationAccountService service;

    @Mock
    private  HttpServletRequest httpServletRequest;

    private ObjectMapper objectMapper;
    private HttpHeaders headers = new HttpHeaders();

    private Enumeration <String> enumerations;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        headers.add("topaz-channel", "2");

        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(),"1234",
                DeviceMW.DEVICE_IP.getCode(),"12344",
                DeviceMW.DEVICE_NAME.getCode(),"OS",
                DeviceMW.GEO_POSITION_X.getCode(),"121.11",
                DeviceMW.GEO_POSITION_Y.getCode(),"121.11",
                DeviceMW.APP_VERSION.getCode(),"1.0.0"
        );

        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        this.enumerations = lists.elements();


    }

    @Test
    void addThirdAccounts() throws Exception {
        GenericResponse expected = GenericResponse.instance(AddThirdAccountResponse.SUCCESS);
        AddThirdAccountRequest request = AddThirdAccountRequestFixture.withDefault();
        when(httpServletRequest.getHeaderNames()).thenReturn(this.enumerations);
        when(service.addThirdAccount(any(),any(),any())).thenReturn(expected);
        MvcResult result = mockMvc
                .perform(put(URL)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .headers(this.headers)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();
        assertEquals(response,actual);
    }
}