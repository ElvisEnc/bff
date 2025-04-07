package bg.com.bo.bff.providers.models.external.services;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.external.services.exception.RequestBuildException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class HttpClient {
    private final IHttpClientFactory httpClientFactory;
    private final ObjectMapper objectMapper;

    @Autowired
    public HttpClient(IHttpClientFactory httpClientFactory, ObjectMapper objectMapper) {
        this.httpClientFactory = httpClientFactory;
        this.objectMapper = objectMapper;
    }

    public <T> T executeGetRequest(String url, Map<String, String> headers, Class<T> responseType) throws IOException {
        return executeRequest(() -> {
            HttpGet request = new HttpGet(url);
            headers.forEach(request::addHeader);
            return request;
        }, responseType);
    }

    public <T> T executePostRequest(String url, Object requestBody, Map<String, String> headers, Class<T> responseType) throws IOException {
        return executeRequest(() -> {
            HttpPost request = new HttpPost(url);
            headers.forEach(request::addHeader);
            String json = objectMapper.writeValueAsString(requestBody);
            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.addHeader("Content-Type", "application/json");
            return request;
        }, responseType);
    }
    private <T> T executeRequest(RequestBuilder builder, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpUriRequest request = builder.build();
            return executeAndHandleResponse(httpClient, request, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (RequestBuildException e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    private <T> T executeAndHandleResponse(CloseableHttpClient httpClient, HttpUriRequest request, Class<T> responseType) throws IOException {
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            return handleResponse(httpResponse, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        }
    }

    private <T> T handleResponse(CloseableHttpResponse httpResponse, Class<T> responseType) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String responseJson = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);

        if (statusCode == HttpStatus.OK.value() || statusCode == HttpStatus.BAD_REQUEST.value()) {
            return objectMapper.readValue(responseJson, responseType);
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            throw new HandledException(GenericControllerErrorResponse.UNAUTHORIZED);
        } else {
            throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
        }
    }

    @FunctionalInterface
    private interface RequestBuilder {
        HttpUriRequest build() throws RequestBuildException, JsonProcessingException;
    }
}
