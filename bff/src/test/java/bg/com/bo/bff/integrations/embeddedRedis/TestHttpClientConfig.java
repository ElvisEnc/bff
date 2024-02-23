package bg.com.bo.bff.integrations.embeddedRedis;

import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import org.apache.http.impl.client.*;
import org.springframework.boot.test.context.TestConfiguration;

import org.apache.http.impl.client.CloseableHttpClient;

@TestConfiguration
public class TestHttpClientConfig implements IHttpClientFactory {
    @Override
    public CloseableHttpClient create() {
        return HttpClientBuilder.create().useSystemProperties().build();
    }
}
