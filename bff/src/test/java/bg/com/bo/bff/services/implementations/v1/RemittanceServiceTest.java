package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.RemittanceResponseFixture;
import bg.com.bo.bff.mappings.providers.remittance.RemittanceMapper;
import bg.com.bo.bff.providers.interfaces.IRemittanceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {
    @InjectMocks
    private RemittanceService service;
    @Mock
    private IRemittanceProvider provider;
    @Spy
    private RemittanceMapper mapper = new RemittanceMapper();
    @Mock
    private RemittanceService self;

    @Test
    void givenValidDataWhenGetGeneralParametersByPersonIdThenListGeneralParametersResponse() throws IOException {
        //Arrange
        ListGeneralParametersResponse expectedResponse = RemittanceResponseFixture.withDefaultGeneralParameters();
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getGeneralParametersData(any())).thenReturn(expectedResponse);

        //Act
        ListGeneralParametersResponse response = service.getGeneralParameters("169494");

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(self).getGeneralParametersData(any());
    }
}