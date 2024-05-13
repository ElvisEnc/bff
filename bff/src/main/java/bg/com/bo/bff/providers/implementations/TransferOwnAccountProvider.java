package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.providers.dtos.requests.TransferMWRequest;
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
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

import java.io.IOException;
import java.util.Map;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferOwnAccountProvider;

@Service
public class TransferOwnAccountProvider implements ITransferOwnAccountProvider {

    ITokenMiddlewareProvider tokenMiddlewareProvider;

    private final MiddlewareConfig middlewareConfig;
    private final TransferMWtMapper mapper;
    private final IHttpClientFactory httpClientFactory;

    private static final Logger LOGGER = LogManager.getLogger(TransferOwnAccountProvider.class.getName());

    public TransferOwnAccountProvider(IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig, ITokenMiddlewareProvider tokenMiddlewareProvider, TransferMWtMapper mapper) {
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.mapper = mapper;
    }

    @Override
    public TransferResponseMD transfer(String personId, String accountId, TransferRequest request, Map<String, String> parameters) throws IOException {

        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());

        TransferMWRequest requestData = mapper.convert(personId, accountId, request);

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathGetAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/transfer/same-bank/to-own-account/";
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
                    return TransferResponseMD.toFormat(Util.stringToObject(jsonResponse, TransferResponseMD.class));
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
