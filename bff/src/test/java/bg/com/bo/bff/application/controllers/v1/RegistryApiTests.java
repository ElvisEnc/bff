package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.requests.RegistryRequestFixture;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;
import bg.com.bo.bff.application.dtos.responses.RegistryResponseFixture;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistryApiTests {

    private MockMvc mockMvc;

    @Mock
    private IRegistryService registryService;

    @InjectMocks
    private RegistryController registryController;

    private static String URL_REGISTER_DEVICE = "/api/v1/registry/device/migration";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registryController).build();
    }

    @Test
    void givenValidRegistryDataWhenRegisterDeviceThenReturnOk() throws Exception {
        RegistryRequest request = RegistryRequestFixture.withDefault();
        RegistryResponse expected = RegistryResponseFixture.withDefault();

        when(registryService.registerByMigration(any())).thenReturn(expected);

        mockMvc.perform(post(URL_REGISTER_DEVICE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andReturn();

        verify(registryService).registerByMigration(any());
    }

    @Test
    void givenInvalidRegistryDataWhenRegisterDeviceThenReturnBadRequest() throws Exception {
        RegistryRequest request = RegistryRequestFixture.withDefault();
        request.setCredentials(null);

        mockMvc.perform(post(URL_REGISTER_DEVICE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isBadRequest());
    }
}
