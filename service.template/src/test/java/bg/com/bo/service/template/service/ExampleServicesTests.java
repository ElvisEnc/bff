package bg.com.bo.service.template.service;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.model.exceptions.ExceptionNotFound;
import bg.com.bo.service.template.model.interfaces.IHttpClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExampleServicesTests {
    @Autowired
    private ExampleService exampleService;

    // GET
    @Test
    void givenObjectExistsWhenGetServiceIsCalledThenExternalServiceReturnsOkResponse() throws IOException {
        // Arrange
        int id = 5;
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        Example example = new Example(id, "example description");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(example);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Example result = exampleService.getExample(id);

        // Assert
        assert result.getId() == id;
    }

    @Test
    void givenExceptionNotFoundWhenExampleServiceGetRequestThenExceptionNotFound() throws IOException {
        // Arrange
        int id = 5;
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        ExceptionNotFound exceptionNotFound = assertThrows(ExceptionNotFound.class, () -> exampleService.getExample(id));

        // Assert
        assert exceptionNotFound.getMessage().equals(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void givenExceptionWhenExampleServiceGetRequestThenException() throws IOException {
        // Arrange
        int id = 5;
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        Exception exception = assertThrows(Exception.class, () -> exampleService.getExample(id));

        // Assert
        assert exception.getMessage().equals(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }


    // POST
    @Test
    void givenNewExampleWhenPostSerivceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        Example example = new Example(5, "TestPost Service");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        Response responseMock = new Response();
        responseMock.setStatusCode("200");
        responseMock.setMessage("Created");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponseMock = objectMapper.writeValueAsString(responseMock);
        InputStream inputStream = new ByteArrayInputStream(jsonResponseMock.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Response response = exampleService.createExample(example);

        // Assert
        assert response.getStatusCode().equals("200");
    }

    @Test
    void givenExceptionNotFoundWhenExampleServicePostRequestThenExceptionNotFound() throws IOException {
        // Arrange
        Example example = new Example(5, "TestPost Service");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        ExceptionNotFound exceptionNotFound = assertThrows(ExceptionNotFound.class, () -> exampleService.createExample(example));

        // Assert
        assert exceptionNotFound.getMessage().equals(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void givenExceptionWhenExampleServicePostRequestThenException() throws IOException {
        // Arrange
        Example example = new Example(5, "TestPost Service");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        Exception exception = assertThrows(Exception.class, () -> exampleService.createExample(example));

        // Assert
        assert exception.getMessage().equals(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }


    // PUT
    @Test
    void givenAnExampleObjectWhenPutServiceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        Example example = new Example(5, "TestUpdate");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        Response responseMock = new Response();
        responseMock.setStatusCode("200");
        responseMock.setMessage("Updated");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponseMock = objectMapper.writeValueAsString(responseMock);
        InputStream inputStream = new ByteArrayInputStream(jsonResponseMock.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPut.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Response response = exampleService.updateExample(example);

        // Assert
        assert response.getStatusCode().equals("200");
    }

    @Test
    void givenExceptionNotFoundWhenExampleServicePutRequestThenExceptionNotFound() throws IOException {
        // Arrange
        Example example = new Example(5, "TestPost Service");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPut.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        ExceptionNotFound exceptionNotFound = assertThrows(ExceptionNotFound.class, () -> exampleService.updateExample(example));

        // Assert
        assert exceptionNotFound.getMessage().equals(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void givenExceptionWhenExampleServicePutRequestThenException() throws IOException {
        // Arrange
        Example example = new Example(5, "TestPost Service");
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPut.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        Exception exception = assertThrows(Exception.class, () -> exampleService.updateExample(example));

        // Assert
        assert exception.getMessage().equals(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }


    // DELETE
    @Test
    void givenToDeleteAnExampleWhenDeleteServiceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        String id = "5";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
//        String responseJson = "{ \"statusCode\" : \"200\", \"message\" : \"It's deleted\" }";
        Response responseMock = new Response();
        responseMock.setStatusCode("200");
        responseMock.setMessage("Deleted");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponseMock = objectMapper.writeValueAsString(responseMock);
        InputStream inputStream = new ByteArrayInputStream(jsonResponseMock.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpDelete.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Response response = exampleService.deleteExample(id);

        // Assert
        assert response.getStatusCode().equals("200");
    }

    @Test
    void givenExceptionNotFoundWhenExampleServiceDeleteRequestThenExceptionNotFound() throws IOException {
        // Arrange
        String id = "5";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpDelete.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(404);

        // Act
        ExceptionNotFound exceptionNotFound = assertThrows(ExceptionNotFound.class, () -> exampleService.deleteExample(id));

        // Assert
        assert exceptionNotFound.getMessage().equals(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void givenExceptionWhenExampleServiceDeleteRequestThenException() throws IOException {
        // Arrange
        String id = "5";
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        StatusLine statusLineMock = Mockito.mock(StatusLine.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpDelete.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        Exception exception = assertThrows(Exception.class, () -> exampleService.deleteExample(id));

        // Assert
        assert exception.getMessage().equals(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
