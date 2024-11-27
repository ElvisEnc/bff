package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.request.transfer.CryptoMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.CryptoMWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import bg.com.bo.bff.services.interfaces.ICryptoService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CryptoService implements ICryptoService {
    private final ITransferProvider transferProvider;

    public CryptoService(ITransferProvider transferProvider) {
        this.transferProvider = transferProvider;
    }

    @Override
    public void validateCrypto(String description, Map<String, String> parameter) throws IOException {
        CryptoMWResponse responseCrypto = transferProvider.validateCrypto(CryptoMWRequest.builder().description(description).build(), parameter);
        if (responseCrypto.getCode().equals("1"))
            throw new GenericException(TransferMiddlewareError.MDWTRM_CRYPTO);
    }
}
