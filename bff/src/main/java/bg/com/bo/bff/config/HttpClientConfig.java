package bg.com.bo.bff.config;

import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
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