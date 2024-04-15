package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.requests.AddThirdAccountRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddThirdAccountResponse;
import bg.com.bo.bff.providers.interfaces.IDestinationAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.services.implementations.v1.DestinationAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinationAccountServiceTest {

    @Spy
    @InjectMocks
    private DestinationAccountService service;

    @Mock
    private  IDestinationAccountProvider destinationAccountProvider;

    @Mock
    private  IThirdAccountProvider thirdAccountProvider;



    @Test
    void addThirdAccount() throws IOException {
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        AddThirdAccountRequest request = AddThirdAccountRequestFixture.withDefault();
        when(destinationAccountProvider.
                addThirdAccount(any(),any(), any())).thenReturn(GenericResponse.instance(AddThirdAccountResponse.SUCCESS));
        when(thirdAccountProvider.generateAccessToken()).thenReturn(clientToken);
        GenericResponse response = service.addThirdAccount("1212", request, new HashMap<>());
        assertNotNull(response);
    }
}