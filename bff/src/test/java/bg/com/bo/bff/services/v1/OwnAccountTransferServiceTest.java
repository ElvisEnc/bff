package bg.com.bo.bff.services.v1;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import bg.com.bo.bff.controllers.models.TransferRequestFixture;
import bg.com.bo.bff.controllers.models.TransferResponseFixture;
import bg.com.bo.bff.controllers.response.TransferResponse;
import bg.com.bo.bff.provider.interfaces.ITransferOwnAccountProvider;

@ExtendWith(MockitoExtension.class)
class OwnAccountTransferServiceTest {

    @Mock
    private OwnAccountTransferService service;

    @Mock
    private ITransferOwnAccountProvider provider;


    @BeforeEach
    void setUp() {
        this.service = new OwnAccountTransferService(provider);
    }

    @Test
    void transfer() throws IOException {
        TransferResponse expected = TransferResponseFixture.withDefault();
        when(provider.transfer(any(), any())).thenReturn(expected);

        TransferResponse response = service.transfer("123456", TransferRequestFixture.withDefault());
        assertNotNull(response);

        verify(provider).transfer(any(), any());

    }
}