package bg.com.bo.bff.services;

import bg.com.bo.bff.model.*;
import bg.com.bo.bff.model.exceptions.UnauthorizedException;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService implements ILoginServices {
    @Value("${middleware}")
    private String baseUrl;

    @Value("${login}")
    private String urlComplement;

    private IHttpClientFactory httpClientFactory;

    public LoginService(IHttpClientFactory httpClientFactoryMock) {
        this.httpClientFactory = httpClientFactoryMock;
    }

    public LoginResponse loginRequest(LoginRequest loginRequest) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = baseUrl + urlComplement;
            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(loginRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                switch (httpResponse.getStatusLine().getStatusCode()) {
                    case 200: {
                        String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                        return objectMapper.readValue(jsonResponse, LoginResponse.class);
                    }
                    case 401:
                        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name());
                    default:
                        throw new UnsupportedOperationException(HttpStatus.INTERNAL_SERVER_ERROR.name());
                }
            }
        }
    }
}
