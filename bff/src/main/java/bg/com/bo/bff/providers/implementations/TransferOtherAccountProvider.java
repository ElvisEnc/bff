package bg.com.bo.bff.providers.implementations;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferOtherAccountProvider;
import bg.com.bo.bff.providers.dtos.responses.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.responses.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;

@Service
public class TransferOtherAccountProvider implements ITransferOtherAccountProvider {

    ITokenMiddlewareProvider tokenMiddlewareProvider;

    private final MiddlewareConfig middlewareConfig;

    private IHttpClientFactory httpClientFactory;

    private static final Logger LOGGER = LogManager.getLogger(TransferOtherAccountProvider.class.getName());


    public TransferOtherAccountProvider(IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig, ITokenMiddlewareProvider tokenMiddlewareProvider) {
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
    }

    @Override
    public TransferResponse transfer(String personId, TransferRequest request) throws IOException {

        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName());


        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String ganamovilChannel = "2";
            String pathGetAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/transfer/same-bank/to-other-account/" + personId;
            HttpPost httpPost = new HttpPost(pathGetAccounts);
            httpPost.setHeader("Authorization", "Bearer " + clientToken.getAccessToken());
            httpPost.setHeader("topaz-channel", ganamovilChannel);
            String jsonMapper = Util.objectToString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            String responseAccounts = EntityUtils.toString(httpResponse.getEntity());
            if (statusCode == 200) {
                ApiDataResponse<TransferResponseMD> response = Util.stringToObject(responseAccounts, ApiDataResponse.class);
                String text = Util.objectToString(response.getData());
                TransferResponse transferResponse = Util.stringToObject(text, TransferResponse.class);
                return transferResponse;
            } else {
                ApiErrorResponse response = Util.stringToObject(responseAccounts, ApiErrorResponse.class);
                throw new GenericException(response.getErrorDetail().toString(), HttpStatus.resolve(response.getCode()));
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el cliente");
        }

    }
}
