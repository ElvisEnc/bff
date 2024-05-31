package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.ErrorDetailResponse;
import bg.com.bo.bff.providers.dtos.response.TransferMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.transfer.TransferMWtMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferACHMiddlewareProviderTest {

    @Spy
    @InjectMocks
    TransferACHMiddlewareProvider provider;

    @Mock
    ITokenMiddlewareProvider tokenMiddlewareProvider;

    @Mock
    MiddlewareConfig middlewareConfig;

    @Mock
    IHttpClientFactory httpClientFactory;

    @Mock
    CloseableHttpClient closeableHttpClientMock;

    @Mock
    CloseableHttpResponse closeableHttpGetResponseMock;

    @Mock
    HttpEntity httpEntityMock;

    @Mock
    StatusLine statusLineMock;

    private Map<String, String> map;
    private final TransferMWtMapper transferMapper = TransferMWtMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        this.provider = new TransferACHMiddlewareProvider(tokenMiddlewareProvider, httpClientFactory, middlewareConfig, transferMapper);
    }

    private static ClientToken getClientToken() {
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjdjNjlmZjNjZjdlNjE5MWU2NGUxMmZhMGVlNmM2ZWNiNTBiODkyY2E5NzIyMmJmZmMxMTc0Yzg5ZTcwNGM5NDQiLCJyb2xlIjoiMTY5YjRlM2IyNzhiYzAzYzZjNWUzNTQ4MDk5ZDUyZTk1MzRmZDRkNjhmMTM0MmEzNzM0OWFjYjQ1NWQ2ZWRjOCIsImdyb3Vwc2lkIjoiMmZhN2MxYjljNjE1ZmU5NThjYmFkODAyNDQzMGNjYjM3ZGE5YTEyMGExMjJiYWI0ZDEyMTFjMGQ3MDMyMTEwYiIsInByaW1hcnlzaWQiOiIyNGI4YjIxNTE1ZTU4ZDdkYTJiZTE1ZWFkZjBhODUyODg5NjEyNTMzODI4ZjkxNDA2YWJmNjRmYjgyYTViNjE2IiwibmJmIjoxNjk5OTIyMzg5LCJleHAiOjE2OTk5MjQxODksImlhdCI6MTY5OTkyMjM4OSwiaXNzIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIiwiYXVkIjoiaHR0cDovL3NlcnZpY2lvcy5iZ2EuY29tLmJvIn0.J-Is_mRLEwwn8Z-RyAe40t0TpkLoppTE7roWe0zXFoc");
        clientToken.setExpiresIn(1699924189);
        return clientToken;
    }

    @Test
    void transferACHAccount() throws IOException {
        ClientToken clientToken = getClientToken();
        when(tokenMiddlewareProvider.generateAccountAccessToken(any(), any(), any())).thenReturn(clientToken);
        Mockito.when(httpClientFactory.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(200);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(TransferMWResponseFixture.withDefaultACH());
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        TransferResponseMD result = provider.transferAchAccount("123455", "123", TransferRequestFixture.withDefault(), map);

        assertNotNull(result);
        verify(tokenMiddlewareProvider).generateAccountAccessToken(any(), any(), any());
    }

    @Test
    void transferErrorACHAccount() throws IOException {
        ClientToken clientToken = getClientToken();
        when(tokenMiddlewareProvider.generateAccountAccessToken(any(), any(), any())).thenReturn(clientToken);
        Mockito.when(httpClientFactory.create()).thenReturn(closeableHttpClientMock);
        Mockito.when(closeableHttpClientMock.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpGetResponseMock);
        Mockito.when(closeableHttpGetResponseMock.getEntity()).thenReturn(httpEntityMock);
        Mockito.when(closeableHttpGetResponseMock.getStatusLine()).thenReturn(statusLineMock);
        Mockito.when(statusLineMock.getStatusCode()).thenReturn(406);

        List<ErrorDetailResponse> responseList = new ArrayList<>();
        responseList.add(ErrorDetailResponse.builder().code("MDW").description("Error").build());
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .errorType("Technical")
                .code(406)
                .errorDetailResponse(responseList).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Mockito.when(httpEntityMock.getContent()).thenReturn(inputStream);

        GenericException response = assertThrows(GenericException.class, () -> provider.transferAchAccount("123455", "123", TransferRequestFixture.withDefault(), map));

        assertNotNull(response);
    }
}