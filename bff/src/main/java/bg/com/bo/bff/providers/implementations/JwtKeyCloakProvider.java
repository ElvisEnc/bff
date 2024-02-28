package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.*;
import bg.com.bo.bff.providers.mappings.IGenericsMapper;
import bg.com.bo.bff.providers.mappings.keycloak.KeyCloakMapper;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.providers.dtos.requests.keycloak.CustomClaimsData;
import bg.com.bo.bff.providers.dtos.responses.keycloak.ErrorKCResponse;
import bg.com.bo.bff.models.dtos.login.CreateTokenServiceResponse;
import bg.com.bo.bff.models.jwt.*;
import bg.com.bo.bff.providers.dtos.responses.keycloak.KeyCloakCertListResponse;
import bg.com.bo.bff.providers.dtos.responses.keycloak.CreateTokenKCResponse;
import bg.com.bo.bff.commons.enums.UserRole;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.keycloak.KeyCloakKeyResponse;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JwtKeyCloakProvider implements IJwtProvider {
    private static final Logger logger = LogManager.getLogger(JwtKeyCloakProvider.class.getName());

    @Value("${keycloak.base.url}")
    private String urlBase;

    @Value("${keycloak.token.url}")
    private String urlTokenComplement;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.issuer}")
    private String issuer;

    @Value("${keycloak.audience}")
    private String audience;

    @Value("${keycloak.authorized.party}")
    private String authorizedParty;

    @Value("${keycloak.subject}")
    private String subject;

    @Value("${keycloak.certs.url}")
    private String urlCertsComplement;

    @Autowired
    private JwtKeyCloakProvider self;

    private IHttpClientFactory httpClientFactory;
    private KeyCloakMapper keyCloakMapper;
    private IGenericsMapper genericsMapper;

    @Autowired
    public JwtKeyCloakProvider(IHttpClientFactory httpClientFactory, KeyCloakMapper keyCloakMapper, IGenericsMapper genericsMapper) {
        this.httpClientFactory = httpClientFactory;
        this.keyCloakMapper = keyCloakMapper;
        this.genericsMapper = genericsMapper;
    }

    /**
     * Crea un nuevo Access JWT y Refresh JWT a través de KeyCloak. Esto genera una nueva sesión en KeyCloak.
     *
     * @param personId correspondiente al usuario.
     * @param userRole rol del usuario.
     * @return TokenData con un nuevo Access JWT y un nuevo Refersh JWT.
     * @throws IOException en caso de un problema o la conexión a KeyCloak fue cancelada.
     * @see <a href="https://acelerabga.atlassian.net/wiki/spaces/NGM/pages/2025685033">Integración de KeyCloak con BFF</a>
     */
    @Override
    public CreateTokenServiceResponse generateToken(String personId, UserRole userRole) {
        CustomClaimsData customClaimsData = CustomClaimsData.builder().personId(personId).role(userRole).build();

        ObjectMapper mapper = new ObjectMapper();
        String customClaimsJson;
        try {
            customClaimsJson = mapper.writeValueAsString(customClaimsData);
        } catch (JsonProcessingException e) {
            logger.error(e);
            throw new JwtServiceException();
        }

        String customClaimsEncoded = Base64.getEncoder().encodeToString(customClaimsJson.getBytes());

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "urn:ietf:params:oauth:grant-type:uma-ticket"));
        params.add(new BasicNameValuePair("claim_token", customClaimsEncoded));
        params.add(new BasicNameValuePair("claim_token_format", "urn:ietf:params:oauth:token-type:jwt"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("audience", audience));

        return createTokenKCEndpoint(params);
    }

    /**
     * Obtiene todas las keys utilizadas por KeyCloak. Es un método que se almacena en caché.
     *
     * @return un HashMap de los actuales key de KeyCloak.
     */
    @Cacheable(value = Constants.CERTS_CACHE_NAME, key = "#root.methodName")
    public Map<String, JwtKey> certs() {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = urlBase + urlCertsComplement;

            HttpGet request = new HttpGet(pathPostToken);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                ObjectMapper objectMapper = new ObjectMapper();
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                KeyCloakCertListResponse certListResponse = objectMapper.readValue(responseToken, KeyCloakCertListResponse.class);
                Function<KeyCloakKeyResponse, JwtKey> conv = ((KeyCloakKeyResponse kck) -> keyCloakMapper.getObjectMapper().convert(kck));
                Function<JwtKey, String> func = (JwtKey::getId);
                return genericsMapper.convert(certListResponse.getKeys(), func, conv);
            } catch (Exception e) {
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al consultar los certificados.");
            }
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new RequestException("Hubo un error no controlado al crear el cliente para la obtencion de certificados.");
        }
    }

    /**
     * Realiza un "Refresh token" con uno ya existente a través de KeyCloak.
     *
     * @param refreshToken token para poder realizar el "Refresh token".
     * @return TokenData con un nuevo Access JWT y un nuevo Refersh JWT.
     * @see <a href="https://acelerabga.atlassian.net/wiki/spaces/NGM/pages/2025685033">Integración de KeyCloak con BFF</a>
     */
    @Override
    public CreateTokenServiceResponse refreshToken(String refreshToken) {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("refresh_token", refreshToken));

        return createTokenKCEndpoint(params);
    }

    /**
     * Llamado al endpoint de creación de tokens de Keycloak. Puede realizarse para la creación de token desde 0 o bien
     * para realizar el refresh token. En el primer caso se generará una nueva sesión en KeyCloak.
     *
     * @param params los parámetros necesarios para la creación de los tokens en KeyCloak.
     * @return un TokenData creado en KeyCloak que contiene el Access JWT y Refresh JWT.
     * @see <a href="https://acelerabga.atlassian.net/wiki/spaces/NGM/pages/2025685033">Integración de KeyCloak con BFF</a>
     */
    private CreateTokenServiceResponse createTokenKCEndpoint(List<BasicNameValuePair> params) {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = urlBase + urlTokenComplement;

            HttpPost postCreateToken = new HttpPost(pathPostToken);
            postCreateToken.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity httpEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
            postCreateToken.setEntity(httpEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(postCreateToken)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                CreateTokenServiceResponse createTokenResponse = new CreateTokenServiceResponse();
                ObjectMapper objectMapper = new ObjectMapper();
                switch (statusCode) {
                    case 200:
                        String responseToken = EntityUtils.toString(httpResponse.getEntity());
                        CreateTokenKCResponse createTokenKCResponse = objectMapper.readValue(responseToken, CreateTokenKCResponse.class);
                        createTokenResponse.setTokenData(keyCloakMapper.getObjectMapper().convert(createTokenKCResponse));
                        createTokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.SUCCESS);
                        break;
                    case 400:
                        String errorResponse = EntityUtils.toString(httpResponse.getEntity());

                        ErrorKCResponse errorKCResponse = getErrorKCResponse(objectMapper, errorResponse);

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.UNAUTHORIZED_CLIENT.getCode()))
                            throw new CreateTokenServiceException(ErrorKCResponse.Error.UNAUTHORIZED_CLIENT.name(), String.format("Cliente no autorizado. Descripcion: %s", errorKCResponse.getErrorDescription()));

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_CLIENT.getCode()))
                            throw new CreateTokenServiceException(ErrorKCResponse.Error.INVALID_CLIENT.name(), String.format("Cliente invalido. Descripcion: %s", errorKCResponse.getErrorDescription()));

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_REQUEST.getCode()))
                            throw new CreateTokenServiceException(ErrorKCResponse.Error.INVALID_REQUEST.name(), String.format("Request invalido. Descripcion: %s", errorKCResponse.getErrorDescription()));

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.UNSUPPORTED_GRANT_TYPE.getCode()))
                            throw new CreateTokenServiceException(ErrorKCResponse.Error.UNSUPPORTED_GRANT_TYPE.name(), String.format("Grant type invalido. Descripcion: %s", errorKCResponse.getErrorDescription()));

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_GRANT.getCode()))
                            createTokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.INVALID_DATA);
                        else
                            throw new NotHandledResponseException(errorKCResponse.getError());
                        break;
                    case 404:
                        String response = EntityUtils.toString(httpResponse.getEntity());
                        throw new CreateTokenServiceException(ErrorKCResponse.Error.NOT_FOUND_404.name(), String.format("Error en el request. Respuesta: %s", response));
                    default:
                        throw new NotHandledResponseException(EntityUtils.toString(httpResponse.getEntity()));
                }

                return createTokenResponse;
            } catch (CreateTokenServiceException | NotHandledResponseException e) {
                throw e;
            } catch (Exception e) {
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el createToken");
            }
        } catch (CreateTokenServiceException | NotHandledResponseException | RequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new RequestException("Hubo un error no controlado al crear el clienteToken");
        }
    }

    private static ErrorKCResponse getErrorKCResponse(ObjectMapper objectMapper, String errorResponse) {
        ErrorKCResponse errorKCResponse;
        try {
            errorKCResponse = objectMapper.readValue(errorResponse, ErrorKCResponse.class);
        } catch (Exception e) {
            throw new NotHandledResponseException(errorResponse);
        }
        return errorKCResponse;
    }

    /**
     * Convierte un JWT encodeado en un objeto JwtAccess. Internamente valida los valores de los claims esperados y si esta correctamente firmado.
     *
     * @param token el JWT en base64.
     * @return JwtAccess con los datos del token provisto.
     */
    @Override
    public JwtAccess parseJwtAccess(String token) throws JwtException, JwtValidationException {
        JwtAccess jwtAccess = getSignedToken(token);

        if (!validateToken(jwtAccess)) throw new JwtValidationException();

        return jwtAccess;
    }

    /**
     * Valida los claims esperados.
     *
     * @param jwtAccess el token a validar.
     * @return el resultado de la validación.
     */
    private boolean validateToken(JwtAccess jwtAccess) {
        try {
            boolean issuerValidation = issuer.equals(jwtAccess.getPayload().getIssuer());
            boolean authorizedPartyValidation = authorizedParty.equals(jwtAccess.getPayload().getAuthorizedParty());
            boolean subjectValidation = subject.equals(jwtAccess.getPayload().getSubject());
            boolean rolesValidation = jwtAccess.getPayload().getAudience().contains(audience);

            return rolesValidation && authorizedPartyValidation && subjectValidation && issuerValidation;
        } catch (Exception e) {
            logger.error("Hubo un error inesperado al validar el token.");
            return false;
        }
    }

    /**
     * Devuelve el objeto JwtAccess verificando la firma del mismo pero sin validación de los claims.
     *
     * @param token el JWT en base64.
     * @return JwtAccess con los datos del token provisto.
     */
    private JwtAccess getSignedToken(String token) {
        try {
            Map<String, JwtKey> keyList = self.certs();
            return keyCloakMapper.getJsonMapper().convertToJwtAccess(token, keyList);
        } catch (Exception e) {
            logger.error("Hubo un error al obtener el Access JWT.");
            logger.error(e);
            throw new JwtException("Hubo un error al obtener el Access JWT.");
        }
    }
}
