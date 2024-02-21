package bg.com.bo.bff.provider;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.config.exception.GenericException;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.model.util.Util;
import bg.com.bo.bff.provider.interfaces.ILoginBiometricProvider;
import bg.com.bo.bff.provider.request.LoginBiometricRequestDTO;
import bg.com.bo.bff.provider.response.ApiErrorResponse;

@Service
public class LoginBiometricProvider implements ILoginBiometricProvider {

    private IHttpClientFactory httpClientFactory;

    private static final Logger LOGGER = LogManager.getLogger(LoginBiometricProvider.class.getName());


    public LoginBiometricProvider(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public Object loginBiometric(LoginBiometricRequestDTO requestDTO) throws IOException {

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathGetAccounts = "https://devganamovil.bg.com.bo/services/GanaMovilWS/mtdLoginBiometrico";
            HttpPost httpPost = new HttpPost(pathGetAccounts);
            httpPost.setHeader("Authorization", "Basic OTQ0M2VhNjZjYTk1OGRjZTQ5MTU4ZmRhMjhkZjNjYjI6NDIxY2Q3ZWY3NTUwMWJiMmQ4NWZmMmFjMjQyYWU4Mjk=");
            httpPost.setHeader("Cookie", "JSESSIONID=HgWPECuXW1_yfi4NZA2Ne05fHcn_s8EZq3Nm2VzW.bgscldevkony97");
            String jsonMapper = Util.objectToString(requestDTO);
            StringEntity entity = new StringEntity(jsonMapper);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            String responseAccounts = EntityUtils.toString(httpResponse.getEntity());
            if (statusCode == 200) {
                return Util.stringToObject(responseAccounts, Object.class);
            } else {
                ApiErrorResponse response = Util.stringToObject(responseAccounts, ApiErrorResponse.class);
                throw new GenericException(response.getErrorDetail().toString(), HttpStatus.resolve(response.getCode()));
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el cliente");
        }
    }
}
