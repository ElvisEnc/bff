package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatus;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.commons.enums.user.AppDataYoloNet;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.generic.DynamicAppError;
import bg.com.bo.bff.providers.dtos.response.personal.information.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalUpdateNetResponse;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.mappings.providers.information.IPersonalInformationMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class PersonalInformationNetProvider implements IPersonalInformationNetProvider {

    @Value("${url.personal.information.net}")
    private String urlProviderPersonalInformationNet;
    private final IHttpClientFactory httpClientFactory;
    private final IPersonalInformationMapper iPersonalInformationMapper;
    private static final Logger LOGGER = LogManager.getLogger(PersonalInformationNetProvider.class.getName());
    private static final String URL_GET_DISTRICTS = "/obtenerLocalidadesSegunDepartamento";
    private static final String UPDATE_PERSONAL_INFORMATION = "/actualizarDatosClienteGanaSueldo";
    private static final String UPDATE_SUCCESS = "1";

    public PersonalInformationNetProvider(IHttpClientFactory httpClientFactory, IPersonalInformationMapper iPersonalInformationMapper) {
        this.httpClientFactory = httpClientFactory;
        this.iPersonalInformationMapper = iPersonalInformationMapper;
    }

    @Override
    public PersonalInformationNetResponse getPersonalInformation(ApiPersonalInformationNetRequest request, Map<String, String> parameters) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderPersonalInformationNet + "/obtenerDatosClienteGanaSueldo";
            String jsonMapper = Util.objectToString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    PersonalInformationNetResponse response = Util.stringToObject(jsonResponse, PersonalInformationNetResponse.class);
                    if (response.getErrorCode().equals(AppDataYoloNet.CODIGO_EXITO.getValue())) {
                        return response;
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

    @Override
    public EconomicActivityResponse getEconomicalActivity(Integer personId) {
        ApiPersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(String.valueOf(personId));
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderPersonalInformationNet + "/actividadEconomica";
            String jsonMapper = Util.objectToString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    ProviderNetResponse netResponse = Util.stringToObject(jsonResponse, ProviderNetResponse.class);
                    if (netResponse.getErrorCode().equals(AppDataYoloNet.CODIGO_EXITO.getValue())) {
                        return iPersonalInformationMapper.convertEconomicActivity(netResponse);
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

    @Override
    public DistrictsNetResponse getDistricts(DistrictsNetRequest request, Map<String, String> parameter) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderPersonalInformationNet + URL_GET_DISTRICTS;
            String jsonMapper = Util.objectToString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    DistrictsNetResponse response = Util.stringToObject(jsonResponse, DistrictsNetResponse.class);
                    if (response.getErrorCode().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
                        return response;
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

    @Override
    public MaritalStatusResponse getMaritalStatuses() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<MaritalStatus>> typeReference = new TypeReference<List<MaritalStatus>>() {
        };
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/files/MaritalStatusResponse.json")) {
            List<MaritalStatus> maritalStatusList = objectMapper.readValue(inputStream, typeReference);
            return MaritalStatusResponse.builder()
                    .data(maritalStatusList)
                    .build();
        } catch (IOException e) {
            throw new GenericException();
        }
    }

    @Override
    public PersonalUpdateNetResponse updatePersonalInformation(UpdatePersonalInformationNetRequest request, Map<String, String> parameter) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = urlProviderPersonalInformationNet + UPDATE_PERSONAL_INFORMATION;
            String jsonMapper = Util.objectToString(request);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    PersonalUpdateNetResponse response = Util.stringToObject(jsonResponse, PersonalUpdateNetResponse.class);
                    if(!response.getErrorCode().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())){
                        LOGGER.error(jsonResponse);
                        AppError error = AppError.BAD_REQUEST;
                        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                    }
                    if (!response.getUpdateResponse().equals(UPDATE_SUCCESS)) {
                        LOGGER.error(jsonResponse);
                        AppError error = AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION;
                        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                    }
                    return response;
                }
                LOGGER.error(jsonResponse);
                AppError error = AppError.DEFAULT;
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
