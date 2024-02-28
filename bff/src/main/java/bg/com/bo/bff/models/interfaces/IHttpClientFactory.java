package bg.com.bo.bff.models.interfaces;

import org.apache.http.impl.client.CloseableHttpClient;

public interface IHttpClientFactory {
    CloseableHttpClient create();
}
