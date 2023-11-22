package bg.com.bo.service.template.model.interfaces;

import org.apache.http.impl.client.CloseableHttpClient;


public interface IHttpClientFactory {
     CloseableHttpClient create();
}
