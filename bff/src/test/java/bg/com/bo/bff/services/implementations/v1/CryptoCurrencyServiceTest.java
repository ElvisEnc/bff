package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.mappings.providers.crypto.currency.CryptoCurrencyMapper;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyServiceTest {

    @InjectMocks
    private CryptoCurrencyService service;
    @Mock
    private ICryptoCurrencyProvider provider;
    @Spy
    private CryptoCurrencyMapper mapper = new CryptoCurrencyMapper();
}
