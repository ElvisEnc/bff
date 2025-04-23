package bg.com.bo.bff.providers.http.client;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ExternalResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.external.services.DefaultExternalError;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HttpClientExternalProviderTest {

    private IHttpClientFactory httpClientFactory;
    private ObjectMapper objectMapper;
    private HttpClientExternalProvider mockHttpClient;

    @BeforeEach
    void setup() {
        httpClientFactory = mock(IHttpClientFactory.class);
        objectMapper = new ObjectMapper();
        mockHttpClient = new HttpClientExternalProvider(httpClientFactory, objectMapper, IExternalError.class);

    }

    @Test
    void handleResponse_shouldReturnValidResponse_whenSuccessResponse() throws IOException {
        // Arrange
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String responseJson = "success";
        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.OK.value(), responseJson);

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        // Act
        DummyResponse response = (DummyResponse) mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());
    }

    @Test
    void handleResponse_shouldReturnValidResponse_whenSuccess201Response() throws IOException {
        // Arrange
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String responseJson = "created";
        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.CREATED.value(), responseJson);

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        // Act
        DummyResponse response = (DummyResponse) mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        // Assert
        assertNotNull(response);
        assertEquals("created", response.getStatus());
    }

    @Test
    void executeGetRequest_shouldThrowHandledException_whenUnauthorized() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String jsonResponse = "{\"error\": \"Unauthorized\"}";
        HttpEntity entity = new StringEntity(jsonResponse, StandardCharsets.UTF_8);
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED.value());
        when(mockResponse.getStatusLine()).thenReturn(statusLine);
        when(mockResponse.getEntity()).thenReturn(entity);
        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);

        Executable executable = () -> mockHttpClient.executeGetRequest(
                "http://localhost", Map.of(), DummyResponse.class
        );

        assertThrows(HandledException.class, executable);
    }

    @Test
    void executeGetRequest_shouldThrowHandledException_whenBadRequest() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String jsonResponse = "{\"error\": \"Usuario no autenticado.\"}";
        HttpEntity entity = new StringEntity(jsonResponse, StandardCharsets.UTF_8);
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST.value());
        when(mockResponse.getStatusLine()).thenReturn(statusLine);
        when(mockResponse.getEntity()).thenReturn(entity);
        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);

        String url = "http://localhost";
        Map<String, String> headers = Map.of();

        Executable executable = () -> mockHttpClient.executeGetRequest(url, headers, DummyResponse.class);

        assertThrows(HandledException.class, executable);
    }


    @Test
    void executePostRequest_shouldThrowHandledException_whenRequestBuildFails() throws JsonProcessingException {
        ObjectMapper failingMapper = mock(ObjectMapper.class);
        when(failingMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("fail") {});

        HttpClientExternalProvider clientWithFailingMapper = new HttpClientExternalProvider(httpClientFactory, failingMapper, IExternalError.class);

        Executable executable = () -> clientWithFailingMapper.executePostRequest(
                "http://localhost", new DummyRequest("fail"), Map.of(), DummyResponse.class
        );

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getCode(), exception.getCode());
    }

    @Test
    void handleResponse_shouldThrowHandledException_whenBadRequestResponse() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String responseJson = "{\"mensaje\":\"Bad request error\"}";
        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.BAD_REQUEST.value(), responseJson);

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        Executable executable = () -> mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), exception.getCode());
    }

    @Test
    void executeAndHandleResponse_shouldThrowHandledException_whenUnauthorizedResponse() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.UNAUTHORIZED.value(), null);
        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        Executable executable = () -> mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), exception.getCode());
    }

    @Test
    void executeRequest_shouldThrowHandledException_whenHttpClientCreationFails() {
        when(httpClientFactory.create()).thenThrow(new RuntimeException("HttpClient creation failed"));

        Executable executable = () -> mockHttpClient.executeGetRequest("http://localhost", Map.of(), DummyResponse.class);

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getCode(), exception.getCode());
    }

    @Test
    void executeAndHandleResponse_shouldThrowHandledException_whenInternalServerError() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String responseJson = "Internal server error";
        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseJson);

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        Executable executable = () -> mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), exception.getCode());
    }

    @Test
    void executeAndHandleResponse_shouldThrowHandledException_whenEmptyResponse() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.OK.value(), null);

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        Executable executable = () -> mockHttpClient.executePostRequest(
                "http://localhost", new DummyRequest("data"), Map.of(), DummyResponse.class);

        HandledException exception = assertThrows(HandledException.class, executable);
        assertEquals(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE.getCode(), exception.getCode());
    }

    @Test
    void mapErrorResponse_shouldReturnMatchingExternalError_whenMessageMatches() {
        // Arrange
        var mockHttpClientFactory = mock(bg.com.bo.bff.commons.interfaces.IHttpClientFactory.class);
        var provider = new HttpClientExternalProvider<>(mockHttpClientFactory, objectMapper, DefaultExternalError.class);

        ExternalResponse response = new ExternalResponse();
        response.setCodigoEstado(400);
        response.setMensaje("Usuario no autenticado.");

        // Act
        IExternalError result = provider.mapErrorResponse(HttpStatus.BAD_REQUEST.value(), response);

        // Assert
        assertEquals(DefaultExternalError.UNAUTHORIZED, result);
    }

    @Test
    void mapErrorResponse_shouldThrowGenericException_whenStatusCodeIsNotBadRequest() {
        // Arrange
        var mockHttpClientFactory = mock(bg.com.bo.bff.commons.interfaces.IHttpClientFactory.class);
        var provider = new HttpClientExternalProvider<>(mockHttpClientFactory, objectMapper, DefaultExternalError.class);

        ExternalResponse response = new ExternalResponse();
        response.setCodigoEstado(401);
        response.setMensaje("Algún otro mensaje");

        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        // Act
        Executable executable = () -> provider.mapErrorResponse(statusCode, response);

        // Assert
        GenericException exception = assertThrows(GenericException.class, executable);

        assertEquals(DefaultExternalError.BAD_REQUEST.getHttpCode(), exception.getStatus());
    }


    @Test
    void mapErrorResponse_shouldThrowGenericException_whenStatusIsNotBadRequest(){
        // Arrange
        ExternalResponse externalResponse = new ExternalResponse(500, "Some error");

        // Act
        Executable executable = () -> mockHttpClient.mapErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), externalResponse);

        // Assert
        assertThrows(GenericException.class, executable);
    }

    @Test
    void findByMessage_shouldReturnBadRequest_whenNoMatchFound() {
        // Arrange
        var provider = new HttpClientExternalProvider<>(null, new ObjectMapper(), DummyExternalError.class);

        String mensaje = "Mensaje inexistente";

        // Act
        IExternalError result = provider.findByMessage(mensaje);

        // Assert
        assertEquals(DefaultExternalError.BAD_REQUEST, result);
    }

    @Test
    void findByMessage_shouldReturnDefaultExternalErrorMatch_whenAppErrorValueDoesNotMatch() {
        // Arrange
        var provider = new HttpClientExternalProvider<>(null, new ObjectMapper(), DummyExternalError.class);

        String mensaje = DefaultExternalError.UNAUTHORIZED.getMsgError();

        // Act
        IExternalError result = provider.findByMessage(mensaje);

        // Assert
        assertEquals(DefaultExternalError.UNAUTHORIZED, result);
    }

    @Test
    void executePostRequest_withTypeReference_shouldReturnValidResponse_whenSuccess() throws IOException {
        // Arrange
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        DummyResponseType dummyResponse = new DummyResponseType("success");
        CloseableHttpResponse mockHttpResponse = mockHttpResponse(HttpStatus.OK.value(), dummyResponse);
        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        // Act
        DummyResponseType response = (DummyResponseType) mockHttpClient.executePostRequest(
                "http://localhost",
                new DummyRequestType("data"),
                Map.of("Authorization", "Bearer token"),
                new TypeReference<DummyResponseType>() {}
        );

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());
    }

    @Test
    void executePostRequest_shouldThrowHandledException_whenHttpClientCreationFails() {
        // Arrange
        when(httpClientFactory.create()).thenThrow(new RuntimeException("Failed to create client"));

        String url = "http://localhost";
        DummyRequestType request = new DummyRequestType("data");
        Map<String, String> headers = Map.of();
        TypeReference<DummyResponseType> responseType = new TypeReference<>() {};

        // Act
        HandledException exception = assertThrows(HandledException.class, () ->
                mockHttpClient.executePostRequest(url, request, headers, responseType)
        );

        // Assert
        assertEquals(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION.getDescription(), exception.getMessage());
    }

    @Test
    void executePostRequest_shouldRethrowHandledException() throws Exception {
        // Arrange
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String url = "http://localhost";
        DummyRequestType request = new DummyRequestType("data");
        Map<String, String> headers = Map.of();
        TypeReference<DummyResponseType> responseType = new TypeReference<>() {};

        when(mockHttpClientInstance.execute(any(HttpUriRequest.class)))
                .thenThrow(new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, new RuntimeException("inner")));

        // Act
        HandledException exception = assertThrows(HandledException.class, () ->
                mockHttpClient.executePostRequest(url, request, headers, responseType)
        );

        // Assert
        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getDescription(), exception.getMessage());
    }

    @Test
    void executePostRequest_shouldThrowHandledException_whenJsonProcessingFails() throws JsonProcessingException {
        // Arrange
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("JSON fail") {});
        String url = "http://localhost";
        DummyRequestType request = new DummyRequestType("data");
        Map<String, String> headers = Map.of();
        TypeReference<DummyResponseType> responseType = new TypeReference<>() {};

        // Act
        HandledException exception = assertThrows(HandledException.class, () ->
                mockHttpClient.executePostRequest(url, request, headers, responseType)
        );

        // Assert
        assertEquals(GenericControllerErrorResponse.REQUEST_EXCEPTION.getDescription(), exception.getMessage());
    }

    @Test
    void handleResponse_shouldThrowGenericException_whenStatusIsUnauthorized() throws Exception {
        // Arrange
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockStatusLine.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED.value());
        when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));

        // Act & Assert
        Exception thrown = assertThrows(Exception.class, () -> invokeHandleResponse(mockHttpResponse));

        Throwable cause = (thrown instanceof InvocationTargetException)
                ? ((InvocationTargetException) thrown).getCause()
                : thrown;

        assertTrue(cause instanceof GenericException);
    }

    @Test
    void executeGetRequest_shouldReturnValidResponse_whenSuccess200Response() throws IOException {
        CloseableHttpClient mockHttpClientInstance = mock(CloseableHttpClient.class);
        when(httpClientFactory.create()).thenReturn(mockHttpClientInstance);

        String jsonResponse = "{\"error\": \"Unauthorized\"}";
        HttpEntity entity = new StringEntity(jsonResponse, StandardCharsets.UTF_8);
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED.value());
        when(mockResponse.getStatusLine()).thenReturn(statusLine);
        when(mockResponse.getEntity()).thenReturn(entity);
        when(mockHttpClientInstance.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);

        Executable executable = () -> mockHttpClient.executeGetRequest(
                "http://localhost", Map.of(), new TypeReference<>() {}
        );

        assertThrows(HandledException.class, executable);
    }

    @SuppressWarnings("unchecked")
    private <R> void invokeHandleResponse(CloseableHttpResponse response) throws Exception {
        Method method = HttpClientExternalProvider.class.getDeclaredMethod(
                "handleResponse",
                CloseableHttpResponse.class,
                TypeReference.class
        );
        method.setAccessible(true);

        Class<?> appErrorValue = DefaultExternalError.class;
        HttpClientExternalProvider instance = new HttpClientExternalProvider(httpClientFactory, objectMapper, appErrorValue);

        method.invoke(instance, response, new TypeReference<DummyResponseType>() {
        });
    }

    private CloseableHttpResponse mockHttpResponse(int statusCode, Object responseBody) throws IOException {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(statusCode);
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
        public DummyRequest(String input) { this.input = input; }
        public String getInput() { return input; }
    }

    public static class DummyResponse {
        private String status;
        public DummyResponse(String status) { this.status = status; }
        public String getStatus() { return status; }
    }

    enum DummyExternalError implements IExternalError {
        FAKE("fake-code", "Mensaje falso");

        private final String code;
        private final String msgError;

        DummyExternalError(String code, String msgError) {
            this.code = code;
            this.msgError = msgError;
        }

        @Override
        public HttpStatus getHttpCode() {
            return null;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsgError() {
            return msgError;
        }

        @Override
        public String getMessage() {
            return "";
        }
    }

    public static class DummyRequestType {
        private String data;

        public DummyRequestType(String data) { this.data = data; }
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }

    public static class DummyResponseType {
        private String status;

        public DummyResponseType() {}
        public DummyResponseType(String status) { this.status = status; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

}

