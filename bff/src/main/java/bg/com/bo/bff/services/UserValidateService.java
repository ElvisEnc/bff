package bg.com.bo.bff.services;

import bg.com.bo.bff.model.*;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.services.interfaces.IUserValidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidateService implements IUserValidateService {
    private IHttpClientFactory httpClientFactory;

    @Autowired
    public UserValidateService(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public Object validarUsuario(UserValidateRequest request) {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String url = "https://devganamovil.bg.com.bo/services/GanaMovilWS/mtdValidarClave4_N";
            String token = "OTQ0M2VhNjZjYTk1OGRjZTQ5MTU4ZmRhMjhkZjNjYjI6NDIxY2Q3ZWY3NTUwMWJiMmQ4NWZmMmFjMjQyYWU4Mjk=";
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Authorization", "Basic " + token);
            postRequest.setHeader("Cookie", "JSESSIONID=HgWPECuXW1_yfi4NZA2Ne05fHcn_s8EZq3Nm2VzW.bgscldevkony97");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            postRequest.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(postRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseData = EntityUtils.toString(httpResponse.getEntity());
                return objectMapper.readValue(responseData, Object.class);
            } catch (Exception e) {
                throw new RuntimeException("ERROR");
            }
        } catch (Exception e) {
            throw new RuntimeException("FULL ERROR");
        }
    }
}
