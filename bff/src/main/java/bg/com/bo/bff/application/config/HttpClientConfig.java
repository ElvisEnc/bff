package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class HttpClientConfig implements IHttpClientFactory {
    @Override
    public CloseableHttpClient create() {
        return HttpClients.createDefault();
    }
}