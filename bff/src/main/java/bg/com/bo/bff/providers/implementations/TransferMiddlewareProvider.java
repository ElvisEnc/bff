package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.Pcc01MWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.pcc01.Pcc01Mapper;
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

@Service
public class TransferMiddlewareProvider implements ITransferProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private IHttpClientFactory httpClientFactory;
    private Pcc01Mapper pcc01Mapper;
    private final MiddlewareConfig middlewareConfig;

    private static final Logger LOGGER = LogManager.getLogger(TransferMiddlewareProvider.class.getName());

    public TransferMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, IHttpClientFactory httpClientFactory, Pcc01Mapper pcc01Mapper, MiddlewareConfig middlewareConfig) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.httpClientFactory = httpClientFactory;
        this.pcc01Mapper = pcc01Mapper;
        this.middlewareConfig = middlewareConfig;
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
            httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + token.getAccessToken());
            httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(Headers.APP_ID.getName(), ApplicationId.GANAMOVIL.getCode());
            httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            httpRequest.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                if(statusCode == HttpStatus.SC_OK){
                    Pcc01MWResponse pcc01MWResponse = objectMapper.readValue(responseEntity, Pcc01MWResponse.class);
                    return pcc01Mapper.convert(pcc01MWResponse);
                }else {
                    AppError error = Util.mapProviderError(responseEntity);
                    LOGGER.error(responseEntity);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        }  catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}

