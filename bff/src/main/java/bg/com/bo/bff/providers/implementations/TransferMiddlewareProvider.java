package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.Pcc01MWResponse;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.pcc01.Pcc01Mapper;
import bg.com.bo.bff.providers.mappings.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;

@Service
public class TransferMiddlewareProvider implements ITransferProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private IHttpClientFactory httpClientFactory;
    private Pcc01Mapper pcc01Mapper;
    private final MiddlewareConfig middlewareConfig;
    private final TransferMWtMapper transferMapper;

    private static final Logger LOGGER = LogManager.getLogger(TransferMiddlewareProvider.class.getName());

    public TransferMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, IHttpClientFactory httpClientFactory, Pcc01Mapper pcc01Mapper, MiddlewareConfig middlewareConfig, TransferMWtMapper transferMapper) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.httpClientFactory = httpClientFactory;
        this.pcc01Mapper = pcc01Mapper;
        this.middlewareConfig = middlewareConfig;
        this.transferMapper = transferMapper;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public Pcc01Response validateControl(Pcc01Request request) throws IOException {
        ClientToken token = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/money-laundering/validate-digital";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpRequest = new HttpPost(path);
            httpRequest.setHeader(HeadersMW.AUT.getName(), "Bearer " + token.getAccessToken());
            httpRequest.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(HeadersMW.APP_ID.getName(), ApplicationId.GANAMOVIL.getCode());
            httpRequest.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpRequest.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    Pcc01MWResponse pcc01MWResponse = objectMapper.readValue(responseEntity, Pcc01MWResponse.class);
                    return pcc01Mapper.convert(pcc01MWResponse);
                } else {
                    AppError error = Util.mapProviderError(responseEntity);
                    LOGGER.error(responseEntity);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }

    @Override
    public TransferResponseMD transferOwnAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameters) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());
        TransferMWRequest requestData = transferMapper.convert("own", personId, accountId, request);

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathGetAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/transfer/same-bank/to-own-account/";
            HttpPost httpPost = new HttpPost(pathGetAccounts);

            httpPost.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            httpPost.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
            httpPost.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));

            String jsonMapper = Util.objectToString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            httpPost.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    TransferResponseMD response = Util.stringToObject(jsonResponse, TransferResponseMD.class);
                    if (!Objects.equals(response.getData().getStatus(), "PENDING")) {
                        return TransferResponseMD.toFormat(response);
                    } else {
                        LOGGER.error(jsonResponse);
                        AppError error = AppError.MDWTRM_PENDING;
                        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                    }
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

    @Override
    public TransferResponseMD transferThirdAccount(String personId, String accountId, TransferRequest request, Map<String, String> parameters) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName(), middlewareConfig.getClientTransfer(), ProjectNameMW.TRANSFER_MANAGER.getHeaderKey());
        TransferMWRequest requestData = transferMapper.convert("own", personId, accountId, request);

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathGetAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/transfer/same-bank/to-other-accounts/";
            HttpPost httpPost = new HttpPost(pathGetAccounts);
            httpPost.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            httpPost.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
            httpPost.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));

            String jsonMapper = Util.objectToString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            httpPost.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    TransferResponseMD response = Util.stringToObject(jsonResponse, TransferResponseMD.class);
                    if (!Objects.equals(response.getData().getStatus(), "PENDING")) {
                        return TransferResponseMD.toFormat(response);
                    } else {
                        LOGGER.error(jsonResponse);
                        AppError error = AppError.MDWTRM_PENDING;
                        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                    }
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

