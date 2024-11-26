package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AddAccountMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.Pcc01MWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferMiddlewareProvider extends MiddlewareProvider<TransferMiddlewareError> implements ITransferProvider {
    public TransferMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.TRANSFER_MANAGER, TransferMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientTransfer());
    }

    public Pcc01Response validateControl(Pcc01MWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + TransferMiddlewareServices.VALIDATE_PCC01.getServiceURL();
        ApiDataResponse<Pcc01Response> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), Pcc01Response.class);
    }

    @Override
    public TransferMWResponse transferOwnAccount(TransferMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + TransferMiddlewareServices.TRANSFER_TO_OWN_ACCOUNT.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, TransferMWResponse.class);
    }

    @Override
    public TransferMWResponse transferThirdAccount(TransferMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + TransferMiddlewareServices.TRANSFER_TO_THIRD_ACCOUNT.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, TransferMWResponse.class);
    }

    @Override
    public TransferWalletMWResponse transferWalletAccount(TransferMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + TransferMiddlewareServices.TRANSFER_TO_WALLET_ACCOUNT.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, TransferWalletMWResponse.class);
    }
}

