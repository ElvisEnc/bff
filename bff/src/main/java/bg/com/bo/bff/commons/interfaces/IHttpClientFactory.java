package bg.com.bo.bff.commons.interfaces;

import org.apache.http.impl.client.CloseableHttpClient;

public interface IHttpClientFactory {
    CloseableHttpClient create();
}
