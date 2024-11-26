package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.request.tracing.LoggingHttpClientInterceptor;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpClientConfig implements IHttpClientFactory {
    private final LoggingHttpClientInterceptor loggingInterceptor;

    @Autowired
    public HttpClientConfig(LoggingHttpClientInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public CloseableHttpClient create() {
        return HttpClients.custom()
                .addInterceptorFirst((HttpRequestInterceptor) loggingInterceptor)
                .addInterceptorFirst((HttpResponseInterceptor) loggingInterceptor)
                .build();
    }
}