package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoansRequestFixture;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoansResponseFixture;
import bg.com.bo.bff.mappings.providers.loans.LoansMapper;
import bg.com.bo.bff.providers.interfaces.ILoansProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}