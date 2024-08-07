package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.models.enums.middleware.AchTransactionMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferACHMiddlewareProvider extends MiddlewareProvider<AchTransactionMiddlewareError> implements ITransferACHProvider {

    public TransferACHMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, TransferMWtMapper transferMapper) {
        super(ProjectNameMW.ACH_TRANSFER_MANAGER, AchTransactionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientTransferACH());
    }

    @Override
    public TransferAchMwResponse transferAchAccount(TransferMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_TRANSFER_MANAGER.getName() + TransferMiddlewareServices.TRANSFER_TO_ACH_ACCOUNT.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, TransferAchMwResponse.class);
    }
}

