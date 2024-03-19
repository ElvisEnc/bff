package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AccountReportBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.dtos.responses.ApiErrorResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
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
public class AccountStatementProvider implements IAccountStatementProvider {
    private final MiddlewareConfig middlewareConfig;

    ITokenMiddlewareProvider tokenMiddlewareProvider;

    private IHttpClientFactory httpClientFactory;

    private static final Logger LOGGER = LogManager.getLogger(AccountStatementProvider.class.getName());

    public AccountStatementProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
    }

    public ClientToken generateToken() throws IOException {
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.OWN_ACCOUNT_MANAGER.getName(), middlewareConfig.getClientOwnManager(), ProjectNameMW.OWN_ACCOUNT_MANAGER.getHeaderKey());
    }

    @Override
    public AccountReportBasicResponse getAccountStatement(AccountReportBasicRequest request, String token) {

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName() + "/bs/v1/accounts/reports/generate-basic";
            HttpPost httpRequest = new HttpPost(path);
            String jsonMapper = Util.objectToString(request);

            StringEntity entity = new StringEntity(jsonMapper);
            httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + token);
            httpRequest.setHeader(Headers.CHA_MW.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            httpRequest.setEntity(entity);

            CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String responseMW = EntityUtils.toString(httpResponse.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            if (statusCode == HttpStatus.SC_OK) {
                AccountReportBasicResponse basicResponse = objectMapper.readValue(responseMW, AccountReportBasicResponse.class);
                return basicResponse;
            } else {
                ApiErrorResponse response = Util.stringToObject(responseMW, ApiErrorResponse.class);
                throw new GenericException(response.getErrorDetailResponse().toString(), org.springframework.http.HttpStatus.resolve(response.getCode()));
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
