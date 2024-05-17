package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseACHMD;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.mappings.transfer.TransferMWtMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TransferACHMiddlewareProvider implements ITransferACHProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final IHttpClientFactory httpClientFactory;
    private final MiddlewareConfig middlewareConfig;
    private final TransferMWtMapper transferMapper;

    private static final Logger LOGGER = LogManager.getLogger(TransferACHMiddlewareProvider.class.getName());

    public TransferACHMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig, TransferMWtMapper transferMapper) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
        this.transferMapper = transferMapper;
    }

    @Override
    public TransferResponseMD transferAchAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameters) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.ACH_TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransferACH(), ProjectNameMW.ACH_TRANSFER_MANAGER.getHeaderKey());
        TransferMWRequest requestData = transferMapper.convert("ach", personId, accountId, request);

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathGetAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_TRANSFER_MANAGER.getName() + "/bs/v1/ach/transfers/";
            HttpPost httpPost = new HttpPost(pathGetAccounts);
            httpPost.setHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            httpPost.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            httpPost.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
            httpPost.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));

            String jsonMapper = Util.objectToString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            httpPost.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return TransferResponseACHMD.toFormat(Util.stringToObject(jsonResponse, TransferResponseACHMD.class));
                }
                LOGGER.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al realizar la transferencia");
        }
    }
}

