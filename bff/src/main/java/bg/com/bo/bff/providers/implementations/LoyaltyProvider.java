package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import bg.com.bo.bff.providers.interfaces.ILoyaltyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyServices;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class LoyaltyProvider implements ILoyaltyProvider {

    private final IHttpClientFactory httpClientFactory;

    @Value("${loyalty.server.url}")
    private String baseUrl;

    @Autowired
    public LoyaltyProvider(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public LoyaltySystemCodeServerResponse getSystemCode(String personId, Map<String, String> headers) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String url = baseUrl + String.format(LoyaltyServices.GET_SYSTEM_CODE.getServiceURL(), personId);
            HttpGet request = new HttpGet(url);
            headers.forEach(request::addHeader);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.OK.value()) {
                    String responseJson = EntityUtils.toString(httpResponse.getEntity());
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(responseJson, LoyaltySystemCodeServerResponse.class);
                } else {
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
                }
            } catch (HandledException e) {
                throw e;
            } catch (Exception e) {
                throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }


    @Override
    public LoyaltySumPointServerResponse getSumPoint(String codeSystem, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.GET_SUM_POINT.getServiceURL(), codeSystem);
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpGet request = new HttpGet(url);
            headers.forEach(request::addHeader);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.OK.value()) {
                    String responseJson = EntityUtils.toString(httpResponse.getEntity());

                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(responseJson, LoyaltySumPointServerResponse.class);
                } else {
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
                }
            } catch (HandledException e) {
                throw e;
            } catch (Exception e) {
                throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    @Override
    public LoyaltyRegisterSubscriptionResponse registerSubscription(LoyaltyRegisterSubscriptionRequest requestServer, Map<String, String> headers) throws IOException {
        String url = baseUrl + String.format(LoyaltyServices.POST_REGISTER_SUBSCRIPTION.getServiceURL());
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpPost request = new HttpPost(url);
            headers.forEach(request::addHeader);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(requestServer);
            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.OK.value()) {
                    String responseJson = EntityUtils.toString(httpResponse.getEntity());
                    return objectMapper.readValue(responseJson, LoyaltyRegisterSubscriptionResponse.class);
                } else {
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
                }
            } catch (HandledException e) {
                throw e;
            } catch (Exception e) {
                throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }
}
