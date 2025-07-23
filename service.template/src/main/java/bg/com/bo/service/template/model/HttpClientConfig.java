package bg.com.bo.service.template.model;

import bg.com.bo.service.template.model.interfaces.IHttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig implements IHttpClientFactory {
    @Bean
    @Override
    public CloseableHttpClient create(){
        return HttpClients.createDefault();
    }
}
