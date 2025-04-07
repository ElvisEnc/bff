package bg.com.bo.bff.providers.http.client;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.external.services.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.apache.http.HttpVersion.HTTP_1_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpClientTest {

    private IHttpClientFactory httpClientFactory;
    private ObjectMapper objectMapper;
    private HttpClient httpClient;
    private CloseableHttpClient mockHttpClient;

    @BeforeEach
    void setup() {
        httpClientFactory = mock(IHttpClientFactory.class);
        objectMapper = new ObjectMapper();
        httpClient = new HttpClient(httpClientFactory, objectMapper);
        mockHttpClient = mock(CloseableHttpClient.class);
    }

    @Test
    void executeGetRequest_shouldReturnValidResponse() throws Exception {
        // Arrange
        String url = "http://localhost/test";
        Map<String, String> headers = Map.of("header", "value");
        DummyResponse expected = new DummyResponse("ok");

        CloseableHttpResponse httpResponse = mockHttpResponse(HttpStatus.OK.value(), expected);

        when(httpClientFactory.create()).thenReturn(mockHttpClient);
        when(mockHttpClient.execute(any())).thenReturn(httpResponse);

        // Act
        DummyResponse result = httpClient.executeGetRequest(url, headers, DummyResponse.class);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void executePostRequest_shouldReturnValidResponse() throws Exception {
        String url = "http://localhost/test";
        DummyRequest request = new DummyRequest("input");
        Map<String, String> headers = Map.of("Authorization", "Bearer token");
        DummyResponse expected = new DummyResponse("ok");

        CloseableHttpResponse httpResponse = mockHttpResponse(HttpStatus.OK.value(), expected);

        when(httpClientFactory.create()).thenReturn(mockHttpClient);
        when(mockHttpClient.execute(any())).thenReturn(httpResponse);

        DummyResponse result = httpClient.executePostRequest(url, request, headers, DummyResponse.class);

        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void handleResponse_shouldThrowUnauthorized() throws Exception {
        CloseableHttpResponse httpResponse = mockHttpResponse(HttpStatus.UNAUTHORIZED.value(), null);
        when(httpClientFactory.create()).thenReturn(mockHttpClient);
        when(mockHttpClient.execute(any())).thenReturn(httpResponse);

        assertThrows(HandledException.class, () ->
                httpClient.executeGetRequest("http://localhost", Map.of(), DummyResponse.class));
    }

    @Test
    void handleResponse_shouldThrowNotHandledResponse() throws Exception {
        CloseableHttpResponse httpResponse = mockHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        when(httpClientFactory.create()).thenReturn(mockHttpClient);
        when(mockHttpClient.execute(any())).thenReturn(httpResponse);

        HandledException ex = assertThrows(HandledException.class, () ->
                httpClient.executeGetRequest("http://localhost", Map.of(), DummyResponse.class));
        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), ex.getCode());
    }


    private CloseableHttpResponse mockHttpResponse(int statusCode, Object responseBody) throws IOException {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = new BasicStatusLine(HTTP_1_1, statusCode, "");
        when(response.getStatusLine()).thenReturn(statusLine);

        if (responseBody != null) {
            String json = objectMapper.writeValueAsString(responseBody);
            HttpEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
            when(response.getEntity()).thenReturn(entity);
        } else {
            when(response.getEntity()).thenReturn(new StringEntity("", StandardCharsets.UTF_8));
        }

        return response;
    }

    public static class DummyRequest {
        private String input;
        public DummyRequest() {}
        public DummyRequest(String input) { this.input = input; }
        public String getInput() { return input; }
        public void setInput(String input) { this.input = input; }
    }

    public static class DummyResponse {
        private String status;
        public DummyResponse() {}
        public DummyResponse(String status) { this.status = status; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}