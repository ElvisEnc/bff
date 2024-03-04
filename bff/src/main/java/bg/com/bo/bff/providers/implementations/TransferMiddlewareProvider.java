package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.commons.enums.HttpError;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.Pcc01MWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.pcc01.Pcc01Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransferMiddlewareProvider implements ITransferProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private IHttpClientFactory httpClientFactory;
    private Pcc01Mapper pcc01Mapper;
    private final MiddlewareConfig middlewareConfig;

    private static final Logger LOGGER = LogManager.getLogger(TransferMiddlewareProvider.class.getName());

    public TransferMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, IHttpClientFactory httpClientFactory, Pcc01Mapper pcc01Mapper, MiddlewareConfig middlewareConfig) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.httpClientFactory = httpClientFactory;
        this.pcc01Mapper = pcc01Mapper;
        this.middlewareConfig = middlewareConfig;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public Pcc01Response validateControl(Pcc01Request request) throws IOException {
        ClientToken token = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.TRANSFER_MANAGER.getName());

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String channel = "2";
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_MANAGER.getName() + "/bs/v1/money-laundering/validate-digital";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost requestPost = new HttpPost(path);
            requestPost.setHeader("Content-Type", "application/json");
            requestPost.setHeader("Authorization", "Bearer " + token.getAccessToken());
            requestPost.setHeader("topaz-channel", channel);
            requestPost.setHeader("Accept", "application/json");
            requestPost.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(requestPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                switch (statusCode) {
                    case 200: {
                        Pcc01MWResponse pcc01MWResponse = objectMapper.readValue(responseEntity, Pcc01MWResponse.class);
                        Pcc01Response response = pcc01Mapper.convert(pcc01MWResponse);
                        return response;
                    }
                    case 406:
                        throw new NotAcceptableException(HttpError.ERROR_406.getDescription());
                    default: {
                        throw new UnsupportedOperationException(HttpError.ERROR_500.getDescription());
                    }
                }
            } catch (NotAcceptableException | UnsupportedOperationException e) {
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el Get");
            } catch (Exception e) {
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el getListThridAccounts");
            }
        } catch (NotAcceptableException | UnsupportedOperationException e) {
            LOGGER.error(e);
            throw new RequestException("Hubo un error no controlado al realizar el Get");
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteGetAccounts");
        }
    }
}

