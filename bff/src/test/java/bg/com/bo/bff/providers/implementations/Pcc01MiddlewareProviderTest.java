package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Pcc01MiddlewareProviderTest {
    @Spy
    @InjectMocks
    TransferMiddlewareProvider pcc01MiddlewareProvider;
    @Mock
    ITokenMiddlewareProvider tokenMiddlewareProviderMock;
    @Mock
    MiddlewareConfig middlewareConfigMock;
    @Mock
    IHttpClientFactory httpClientFactoryMock;
    @Mock
    CloseableHttpClient closeableHttpClientMock;
    @Mock
    CloseableHttpResponse closeableHttpResponseMock;
    @Mock
    HttpEntity httpEntityMock;
    @Mock
    StatusLine statusLineMock;
    private static Pcc01MWRequest pcc01MWRequest;
    private static ClientToken clientToken;
    static String project;
    static String clientSecret;
    static String headerKeyToke;

    private Map<String, String> map;

    @BeforeEach
    void setup() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        pcc01MiddlewareProvider = new TransferMiddlewareProvider(tokenMiddlewareProviderMock, middlewareConfigMock, httpClientFactoryMock);
        clientToken = new ClientToken();
        clientToken.setAccessToken("fglkjhdfg9d87fgd98f09gd");
        clientToken.setExpiresIn(1234);
        project = "/manager";
        clientSecret = "2s4f2s4d3f4sd";
        headerKeyToke = "secret";
    }

    @Test
    void givenPersonIdWhenRequestGetAccountsThenListOwnAccounts() throws IOException {
        // Arrange
        ApiDataResponse<Pcc01Response> mwResponse = new ApiDataResponse<Pcc01Response>();
        mwResponse.setData(Pcc01Response.builder().requiresPcc01("S").build());
        ObjectMapper objectMapperMWResponse = new ObjectMapper();
        String jsonAccountsResponseMock = objectMapperMWResponse.writeValueAsString(mwResponse);
        InputStream accountsResponseMock = new ByteArrayInputStream(jsonAccountsResponseMock.getBytes());

        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(httpEntityMock.getContent()).thenReturn(accountsResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        // Act
        Pcc01Response response = pcc01MiddlewareProvider.validateControl(pcc01MWRequest, map);

        // Assert
        Assertions.assertNotNull(response);
    }


    @Test
    void givenStatusLineIs406WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        // Act
        assertThrows(RuntimeException.class, () -> pcc01MiddlewareProvider.validateControl(pcc01MWRequest, map));
    }

    @Test
    void givenStatusLineIs500WhenClientRequestRespondThenRuntimeException() throws IOException {
        // Arrange
        when(tokenMiddlewareProviderMock.generateAccountAccessToken(any(), any(), any())).thenReturn(clientToken);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        Mockito.when(closeableHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(500);

        // Act
        assertThrows(RuntimeException.class, () -> pcc01MiddlewareProvider.validateControl(pcc01MWRequest, map));
    }
}