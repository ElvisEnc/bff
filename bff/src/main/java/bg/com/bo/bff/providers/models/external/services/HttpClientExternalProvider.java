package bg.com.bo.bff.providers.models.external.services;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ExternalResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.external.services.exception.RequestBuildException;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
@Log4j2
public class HttpClientExternalProvider<T extends IExternalError> {
    private final IHttpClientFactory httpClientFactory;
    private final ObjectMapper objectMapper;
    private final Class<T> appErrorValue;

    @Autowired
    public HttpClientExternalProvider(IHttpClientFactory httpClientFactory, ObjectMapper objectMapper, Class<T> appErrorValue) {
        this.httpClientFactory = httpClientFactory;
        this.objectMapper = objectMapper;
        this.appErrorValue = appErrorValue;
    }

    public <R> R executeGetRequest(String url, Map<String, String> headers, Class<R> responseType) throws IOException {
        return executeRequest(() -> {
            HttpGet request = new HttpGet(url);
            headers.forEach(request::addHeader);
            return request;
        }, responseType);
    }

    public <R> R executePostRequest(String url, Object requestBody, Map<String, String> headers, Class<R> responseType) throws IOException {
        return executeRequest(() -> {
            HttpPost request = new HttpPost(url);
            headers.forEach(request::addHeader);
            String json = objectMapper.writeValueAsString(requestBody);
            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.addHeader("Content-Type", "application/json");
            return request;
        }, responseType);
    }

    public <R> R executePostRequest(String url, Object requestBody, Map<String, String> headers, TypeReference<R> responseType) throws IOException {
        return executeRequest(() -> {
            HttpPost request = new HttpPost(url);
            headers.forEach(request::addHeader);
            String json = objectMapper.writeValueAsString(requestBody);
            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.addHeader("Content-Type", "application/json");
            return request;
        }, responseType);
    }

    private <R> R executeRequest(RequestBuilder builder, Class<R> responseType) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpUriRequest request = builder.build();
            return executeAndHandleResponse(httpClient, request, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (RequestBuildException | JsonProcessingException e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    private <R> R executeRequest(RequestBuilder builder, TypeReference<R> responseType) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpUriRequest request = builder.build();
            return executeAndHandleResponse(httpClient, request, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (RequestBuildException | JsonProcessingException e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    private <R> R executeAndHandleResponse(CloseableHttpClient httpClient, HttpUriRequest request, Class<R> responseType) throws IOException {
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            return handleResponse(httpResponse, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        }
    }

    private <R> R executeAndHandleResponse(CloseableHttpClient httpClient, HttpUriRequest request, TypeReference<R> responseType) throws IOException {
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            return handleResponse(httpResponse, responseType);
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
        }
    }

    private <R> R handleResponse(CloseableHttpResponse httpResponse, Class<R> responseType) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String responseJson = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);

        if (statusCode >= 200 && statusCode < 300) {
            return objectMapper.readValue(responseJson, responseType);
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            throw new GenericException(DefaultExternalError.UNAUTHORIZED);
        } else {
            ExternalResponse externalResponse = Util.stringToObject(responseJson, ExternalResponse.class);
            IExternalError error = mapErrorResponse(statusCode, externalResponse);
            throw new HandledException(error, new Exception("Error processing request"));
        }
    }

    private <R> R handleResponse(CloseableHttpResponse httpResponse, TypeReference<R> responseType) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String responseJson = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);

        if (statusCode >= 200 && statusCode < 300) {
            return objectMapper.readValue(responseJson, responseType);
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            throw new GenericException(DefaultExternalError.UNAUTHORIZED);
        } else {
            ExternalResponse externalResponse = Util.stringToObject(responseJson, ExternalResponse.class);
            IExternalError error = mapErrorResponse(statusCode, externalResponse);
            throw new HandledException(error, new Exception("Error processing request"));
        }
    }

    public IExternalError mapErrorResponse(int statusCode, ExternalResponse externalResponse) {
        IExternalError error = null;

        if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            String providerMessageError = externalResponse.getMensaje();
            error = this.findByMessage(providerMessageError);
        } else {
            throw new GenericException(DefaultExternalError.BAD_REQUEST);
        }

        return error;
    }

    public IExternalError findByMessage(String message) {
        for (IExternalError constant : appErrorValue.getEnumConstants()) {
            if (constant.getMsgError() != null && constant.getMsgError().equals(message)) {
                return constant;
            }
        }
        for (IExternalError constant : DefaultExternalError.values()) {
            if (constant.getMsgError() != null && constant.getMsgError().equals(message)) {
                return constant;
            }
        }
        return DefaultExternalError.BAD_REQUEST;
    }

    @FunctionalInterface
    private interface RequestBuilder {
        HttpUriRequest build() throws RequestBuildException, JsonProcessingException;
    }
}