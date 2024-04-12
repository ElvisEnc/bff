package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.request.accountStatement.ExtractPagination;
import bg.com.bo.bff.application.dtos.request.accountStatement.ExtractRequest;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AccountReportBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountStatementProvider implements IAccountStatementProvider {
    @Value("${account.statement.init}")
    private String init;
    @Value("${account.statement.total}")
    private String total;
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

    @Caching(cacheable = {@Cacheable(value = Constants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == false")},
            put = {@CachePut(value = Constants.ACCOUNTS_STATEMENTS, key = "#extractId", condition = "#clearCache == true")})
    @Override
    public AccountReportBasicResponse getAccountStatement(ExtractRequest request, String token, String accountId, String extractId, Boolean clearCache) {
        ExtractPagination pagination = request.getFilters().getPagination();
        AccountReportBasicRequest reportBasicRequest = AccountReportBasicRequest.builder()
                .accountId(accountId)
                .startDate(pagination.getStartDate())
                .endDate(pagination.getEndDate())
                .initCount(init)
                .totalCount(total)
                .build();

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName() + "/bs/v1/accounts/reports/generate-basic";
            HttpPost httpRequest = new HttpPost(path);
            String jsonMapper = Util.objectToString(reportBasicRequest);

            StringEntity entity = new StringEntity(jsonMapper);
            httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + token);
            httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            httpRequest.setEntity(entity);

            CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String responseMW = EntityUtils.toString(httpResponse.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            if (statusCode == HttpStatus.SC_OK) {
                return objectMapper.readValue(responseMW, AccountReportBasicResponse.class);
            } else {
                AppError error = Util.mapProviderError(responseMW);
                String noRecords = error.getDescription();
                if (Objects.equals(AppError.MDWACM_008.getDescription(), noRecords)) {
                    AccountReportBasicResponse basicResponse = new AccountReportBasicResponse();
                    List<AccountReportBasicResponse.AccountReportData> dataEmpty = new ArrayList<>();
                    basicResponse.setData(dataEmpty);
                    return basicResponse;
                }
                LOGGER.error(responseMW);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el cliente");
        }
    }


    @Override
    public AccountReportBasicResponse getAccountStatementForExport(ExportRequest request, String accountId, String token) {
        AccountReportBasicRequest reportBasicRequest = AccountReportBasicRequest.builder()
                .accountId(accountId)
                .startDate(request.getFilters().getStartDate())
                .endDate(request.getFilters().getEndDate())
                .initCount(init)
                .totalCount(total)
                .build();

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName() + "/bs/v1/accounts/reports/generate-basic";
            HttpPost httpRequest = new HttpPost(path);
            String jsonMapper = Util.objectToString(reportBasicRequest);

            StringEntity entity = new StringEntity(jsonMapper);
            httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + token);
            httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
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
                AppError error = Util.mapProviderError(responseMW);
                LOGGER.error(responseMW);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
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
