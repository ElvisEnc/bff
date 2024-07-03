package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.models.enums.middleware.ACHMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferACHMiddlewareProvider extends MiddlewareProvider<ACHMiddlewareError> implements ITransferACHProvider {

    public TransferACHMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, TransferMWtMapper transferMapper) {
        super(ProjectNameMW.ACH_TRANSFER_MANAGER, ACHMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientTransferACH());
    }

    private static final String URL_PATH_COMPLEMENT_TRANSFER_ACH = "/bs/v1/ach/transfers/";

    @Override
    public TransferMWResponse transferAchAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_TRANSFER_MANAGER.getName() + String.format(URL_PATH_COMPLEMENT_TRANSFER_ACH);
        TransferMWRequest requestData = TransferMWtMapper.INSTANCE.convert("ach", personId, accountId, request);
        TransferAchMwResponse response = post(url, HeadersMW.getDefaultHeaders(parameters), requestData,TransferAchMwResponse.class);
        return TransferAchMwResponse.toFormat(response);
    }
}

