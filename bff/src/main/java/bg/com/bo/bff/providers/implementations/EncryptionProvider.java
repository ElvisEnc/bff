package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.EncryptInfo;
import bg.com.bo.bff.models.UserEncryptionKeys;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Service
public class EncryptionProvider implements IEncryptionProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactory;

    private static final Logger logger = LogManager.getLogger(LoginMiddlewareProvider.class.getName());

    public EncryptionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
    }

    @Cacheable(value = CacheConstants.ENCRYPTION_KEYS_CACHE_NAME)
    public UserEncryptionKeys getEncryptionKeys(EncryptInfo encodeInfo) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.LOGIN_MANAGER.getName(), middlewareConfig.getClientLogin(), ProjectNameMW.LOGIN_MANAGER.getHeaderKey());

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/device/get-keys";

            HttpGet request = new HttpGet(path);

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("personId", encodeInfo.getPersonId().toString())
                    .build();
            request.setURI(uri);

            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + clientToken.getAccessToken());
            request.setHeader("middleware-channel", CanalMW.GANAMOVIL.getCanal());
            request.setHeader("application-id", CanalMW.GANAMOVIL.getCanal());
            request.setHeader("deviceId", encodeInfo.getUniqueId());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                    String filteredJson = jsonNode.get("data").toString();

                    return Util.stringToObject(filteredJson, UserEncryptionKeys.class);
                } else {
                    logger.error(jsonResponse);
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
                }
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    public KeyPair createKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(EncryptionAlgorithm.RSA.getCode());
        generator.initialize(EncryptionAlgorithm.RSA.getKeySize());
        return generator.generateKeyPair();
    }
}
