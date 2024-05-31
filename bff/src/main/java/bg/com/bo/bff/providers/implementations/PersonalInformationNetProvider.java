package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.personal.information.PersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.DynamicAppError;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.providers.mappings.personal.information.IPersonalInformationMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PersonalInformationNetProvider implements IPersonalInformationNetProvider {
    @Value("${url.personal.information.net}")
    private String urlProviderPersonalInformationNet;
    private final IHttpClientFactory httpClientFactory;
    private final IPersonalInformationMapper iPersonalInformationMapper;
    private static final Logger LOGGER = LogManager.getLogger(PersonalInformationNetProvider.class.getName());

    public PersonalInformationNetProvider(IHttpClientFactory httpClientFactory, IPersonalInformationMapper iPersonalInformationMapper) {
        this.httpClientFactory = httpClientFactory;
        this.iPersonalInformationMapper = iPersonalInformationMapper;
    }

    @Override
    public PersonalResponse getPersonalInformation(String personId, Map<String, String> parameters) throws IOException {
        PersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(personId);
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderPersonalInformationNet + "/obtenerDatosClienteGanaSueldo";
            String jsonMapper = Util.objectToString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    PersonalInformationNetResponse response = Util.stringToObject(jsonResponse, PersonalInformationNetResponse.class);
                    if (response.getErrorCode().equals("COD000")) {
                        return iPersonalInformationMapper.convertResponse(response);
                    } else {
                        LOGGER.error(jsonResponse);
                        DynamicAppError error = Util.mapNetProviderError(jsonResponse);
                        throw new GenericException(error.getMessage(), error.getStatus(), error.getProviderCode());
                    }
                }
                LOGGER.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}
