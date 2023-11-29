package bg.com.bo.bff.model.interfaces;

import org.apache.http.impl.client.CloseableHttpClient;

public interface IHttpClientFactory {
    CloseableHttpClient create();
}
