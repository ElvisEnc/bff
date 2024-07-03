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
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
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
        Header[] headers = {
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()))
        };
        TransferMWRequest requestData = TransferMWtMapper.INSTANCE.convert("ach", personId, accountId, request);
        TransferAchMwResponse response = post(url, headers, requestData, TransferAchMwResponse.class);
        return TransferAchMwResponse.toFormat(response);
    }
}

