package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.request.registry.RegistryCredentialsRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryDeviceIdentificatorRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryOldDeviceIdentificatorRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.exceptions.GlobalExceptionHandler;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.CredentialsType;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.UserEncryptionKeys;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.loginagn.*;
import bg.com.bo.bff.providers.dtos.responses.RegistryDataResponse;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Objects;

@Service
public class LoginAGNProvider implements ILoginAGNProvider {
    public static final String SERVER_ERROR_CODE_KEY = "a:intCodError";
    public static final String SERVER_STATUS_KEY = "a:strEstado";
    public static final String SERVER_COD_ERROR_SUCCESSFUL = "0";
    public static final String SERVER_STATUS_SUCCESSFUL = "A";
    public static final String VALID_REGISTRATION_VALUE = "REGISTERED";
    private IHttpClientFactory httpClientFactory;

    @Value("${agm.login.server.url}")
    private String agnLoginUrlServer;

    private static final Logger logger = LogManager.getLogger(LoginAGNProvider.class.getName());

    @Autowired
    public LoginAGNProvider(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public Boolean login(RegistryRequest registryRequest) {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path;
            if (registryRequest.getCredentials().getType() == CredentialsType.PASSWORD.getValue())
                path = agnLoginUrlServer + "/Service1.svc/mtdValidarClave4_N";
            else
                path = agnLoginUrlServer + "/Service1.svc/mtdValidarIngresoxHD_N";

            HttpGet requestGet = new HttpGet(path);
            URIBuilder uriBuilder = new URIBuilder(requestGet.getURI())
                    .addParameter("pStrNombreUsuario", registryRequest.getCredentials().getPersonId().toString())
                    .addParameter("pStrClave", registryRequest.getCredentials().getPassword())
                    .addParameter("pStrCanal", registryRequest.getOldDeviceIdentificator().getChannel())
                    .addParameter("pStrImei", registryRequest.getOldDeviceIdentificator().getImei())
                    .addParameter("pStrModelo", registryRequest.getOldDeviceIdentificator().getModel())
                    .addParameter("pStrSistemaOperativo", registryRequest.getOldDeviceIdentificator().getOs())
                    .addParameter("did", registryRequest.getOldDeviceIdentificator().getId())
                    .addParameter("rsid", registryRequest.getOldDeviceIdentificator().getRsid())
                    .addParameter("version", registryRequest.getOldDeviceIdentificator().getVersion())
                    .addParameter("didbga", registryRequest.getOldDeviceIdentificator().getDidbga())
                    .addParameter("pStrKsBga", registryRequest.getOldDeviceIdentificator().getKsBga());

            if (registryRequest.getCredentials().getType() == CredentialsType.PASSWORD.getValue())
                uriBuilder.addParameter("version", registryRequest.getOldDeviceIdentificator().getVersion());
            else
                uriBuilder.addParameter("aver", registryRequest.getOldDeviceIdentificator().getVersion());

            requestGet.setURI(uriBuilder.build());

            try (CloseableHttpResponse httpResponse = httpClient.execute(requestGet)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.OK.value()) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    Document doc = builder.parse(inputStream);

                    Element docEle = doc.getDocumentElement();
                    NodeList nl = docEle.getChildNodes();
                    Element result = (Element) nl.item(0);
                    String codError = result.getElementsByTagName(SERVER_ERROR_CODE_KEY).item(0).getTextContent();
                    String status = result.getElementsByTagName(SERVER_STATUS_KEY).item(0).getTextContent();

                    return codError.equals(SERVER_COD_ERROR_SUCCESSFUL) && status.equals(SERVER_STATUS_SUCCESSFUL);
                } else {
                    String response = EntityUtils.toString(httpResponse.getEntity());
                    logger.error(response);
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
                }
            } catch (HandledException e) {
                throw e;
            } catch (Exception e) {
                throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    public Boolean registerDevice(RegistryRequest registryRequest, UserEncryptionKeys userEncryptionKeys) {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String path = agnLoginUrlServer + "/Service1.svc/mtdActualizarDatosDispositivo";

            RegistryDataRequest registryData = mapRegistryDataRequest(registryRequest, userEncryptionKeys);

            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(registryData);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.OK.value()) {
                    String response = EntityUtils.toString(httpResponse.getEntity());
                    RegistryDataResponse registryDataResponse = objectMapper.readValue(response, RegistryDataResponse.class);

                    return Objects.equals(registryDataResponse.getStatusCode(), VALID_REGISTRATION_VALUE);
                } else
                    throw new HandledException(GenericControllerErrorResponse.NOT_HANDLED_RESPONSE);
            } catch (HandledException e) {
                throw e;
            } catch (Exception e) {
                throw new HandledException(GenericControllerErrorResponse.REQUEST_EXCEPTION, e);
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.HTTP_CLIENT_CREATION_EXCEPTION, e);
        }
    }

    private static RegistryDataRequest mapRegistryDataRequest(RegistryRequest registryRequest, UserEncryptionKeys userEncryptionKeys) {
        RegistryOldDeviceIdentificatorRequest oldDeviceIdentificator = registryRequest.getOldDeviceIdentificator();
        RegistryDeviceIdentificatorRequest deviceIdentificator = registryRequest.getDeviceIdentificator();
        RegistryCredentialsRequest credentials = registryRequest.getCredentials();

        return RegistryDataRequest.builder()
                .deviceDataRequest(
                        DeviceDataRequest.builder()
                                .konyDeviceIdentificator(KonyDeviceIdentificatorRequest.builder()
                                        .deviceId(oldDeviceIdentificator.getId())
                                        .imei(oldDeviceIdentificator.getImei())
                                        .model(oldDeviceIdentificator.getModel())
                                        .os(oldDeviceIdentificator.getOs())
                                        .build())
                                .reactDeviceIdentificator(ReactDeviceIdentificatorRequest.builder()
                                        .uniqueId(deviceIdentificator.getUniqueId())
                                        .bootLoader(deviceIdentificator.getBoot())
                                        .osCode(deviceIdentificator.getSoCodeName())
                                        .osName(deviceIdentificator.getSystemName())
                                        .osVersion(deviceIdentificator.getSystemVersion())
                                        .osBuildId(deviceIdentificator.getSystemBuildId())
                                        .userAgent(deviceIdentificator.getUserAgent())
                                        .firstInstallTime(deviceIdentificator.getFirstInstallTime())
                                        .debug(deviceIdentificator.getDebug() ? 1 : 0)
                                        .emulator(deviceIdentificator.getEmulator() ? 1 : 0)
                                        .build())
                                .credentials(CredentialsRequest.builder()
                                        .personId(credentials.getPersonId().toString())
                                        .password(credentials.getPassword())
                                        .biometricToken(credentials.getPasswordBiometric())
                                        .biometricStatus(credentials.getType() == CredentialsType.BIOMETRIC.getValue() ? "A" : "B")
                                        .build())
                                .userKeys(UserKeysRequest.builder()
                                        .appPrivateKey(userEncryptionKeys.getAppPrivateKey())
                                        .appPrivateKey(userEncryptionKeys.getAppPrivateKey())
                                        .userPublicKey(userEncryptionKeys.getUserPublicKey())
                                        .build())
                                .build())
                .build();
    }
}
