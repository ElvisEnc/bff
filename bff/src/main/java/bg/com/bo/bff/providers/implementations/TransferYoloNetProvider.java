package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.response.DynamicAppError;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.interfaces.ITransferYoloNetProvider;
import bg.com.bo.bff.mappings.providers.transfer.IYoloMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransferYoloNetProvider implements ITransferYoloNetProvider {
    @Value("${url.autoservicio.net}")
    private String urlProviderYoloNet;
    private final IHttpClientFactory httpClientFactory;
    private final IYoloMapper iYoloMapper;
    private static final Logger LOGGER = LogManager.getLogger(TransferYoloNetProvider.class.getName());

    public TransferYoloNetProvider(IHttpClientFactory httpClientFactory, IYoloMapper iYoloMapper) {
        this.httpClientFactory = httpClientFactory;
        this.iYoloMapper = iYoloMapper;
    }

    @Override
    public TransferResponseMD transferToYolo(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request) throws IOException {
        TransferYoloNetRequest requestDataYolo = iYoloMapper.mapperRequest(personId, accountId, accountNumber, request);
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderYoloNet + "Transferencias/RealizarTranferenciaBilletera";
            String jsonMapper = Util.objectToString(requestDataYolo);
            StringEntity entity = new StringEntity(jsonMapper);

            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    ProviderNetResponse response = Util.stringToObject(jsonResponse, ProviderNetResponse.class);
                    if (response.getErrorCode().equals(AppDataYoloNet.CODIGO_EXITO.getValue())) {
                        return TransferResponseMD.toFormat(iYoloMapper.convertResponse(response));
                    } else {
                        LOGGER.error(jsonResponse);
                        DynamicAppError error = Util.mapNetProviderError(jsonResponse);
                        throw new GenericException(error.getMessage(), error.getStatus(), error.getProviderCode());
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
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}
