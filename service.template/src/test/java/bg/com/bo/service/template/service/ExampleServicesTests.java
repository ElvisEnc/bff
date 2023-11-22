package bg.com.bo.service.template.service;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.model.interfaces.IHttpClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class ExampleServicesTests {
    @Autowired
    private ExampleService exampleService;

    @Test
    void givenObjectExistsWhenGetServiceIsCalledThenExternalServiceReturnsOkResponse() throws IOException {
        // Arrange
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClient = Mockito.mock(CloseableHttpClient.class);
        String exampleJson = "{ \"id\" : \"1\", \"description\" : \"lalala\" }";
        CloseableHttpResponse responseMock = Mockito.mock(CloseableHttpResponse.class);
        InputStream anyInputStream = new ByteArrayInputStream(exampleJson.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClient);
        Mockito.when(closeableHttpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        Mockito.when(responseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(anyInputStream);

        // Act
        int id = 1;
        Example result = exampleService.getExample(id);

        // Assert
        assert result.getId() == id;
    }

    @Test
    void givenNewExampleWhenPostSerivceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        String responseJson = "{ \"statusCode\" : \"200\", \"message\" : \"It's created\" }";
        InputStream anyInputStream = new ByteArrayInputStream(responseJson.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(anyInputStream);

        // Act
        Example example = new Example(5, "TestPost Service");
        Response response = exampleService.createExample(example);

        // Assert
        assert response.getStatusCode().equals("200");
    }

    @Test
    void givenAnExampleObjectWhenPutServiceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        String responseJson = "{ \"statusCode\" : \"200\", \"message\" : \"It's updated\" }";
        InputStream anyInputStream = new ByteArrayInputStream(responseJson.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPut.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(anyInputStream);

        // Act
        Example example = new Example(5, "TestUpdate");
        Response response = exampleService.updateExample(example);

        // Assert
        assert response.getStatusCode().equals("200");
    }

    @Test
    void givenToDeleteAnExampleWhenDeleteServiceThenExternalServiceReturnOkResponse() throws IOException {
        // Arrange
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(IHttpClientFactory.class);
        exampleService = new ExampleService(httpClientFactoryMock);
        CloseableHttpClient closeableHttpClientMock = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        String responseJson = "{ \"statusCode\" : \"200\", \"message\" : \"It's deleted\" }";
        InputStream inputStream = new ByteArrayInputStream(responseJson.getBytes());
        HttpEntity httpEntityMock = Mockito.mock(HttpEntity.class);

        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpDelete.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        // Act
        String id = "5";
        Response response = exampleService.deleteExample(id);

        // Assert
        assert response.getStatusCode().equals("200");
    }
}
