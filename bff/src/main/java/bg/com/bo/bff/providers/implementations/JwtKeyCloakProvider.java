package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.*;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.providers.IGenericsMapper;
import bg.com.bo.bff.mappings.providers.keycloak.KeyCloakMapper;
import bg.com.bo.bff.providers.dtos.request.keycloak.CustomClaimsData;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtKey;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.response.keycloak.*;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.commons.enums.user.UserRole;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.models.enums.keycloak.KeyCloakService;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.ISessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Log4j2
public class JwtKeyCloakProvider implements IJwtProvider {

    @Value("${keycloak.base.url}")
    private String urlBase;

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

    private final ISessionService sessionManager;

    @Autowired
    private JwtKeyCloakProvider self;

    private IHttpClientFactory httpClientFactory;
    private KeyCloakMapper keyCloakMapper;
    private IGenericsMapper genericsMapper;

    @Autowired
    public JwtKeyCloakProvider(IHttpClientFactory httpClientFactory, KeyCloakMapper keyCloakMapper, IGenericsMapper genericsMapper, ISessionService sessionManager) {
        this.httpClientFactory = httpClientFactory;
        this.keyCloakMapper = keyCloakMapper;
        this.genericsMapper = genericsMapper;
        this.sessionManager = sessionManager;
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
    public CreateTokenServiceResponse generateToken(String personId, String sid, UserRole userRole) {
        CustomClaimsData customClaimsData = CustomClaimsData.builder()
                .personId(personId)
                .sid(sid)
                .role(userRole)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String customClaimsJson;
        try {
            customClaimsJson = mapper.writeValueAsString(customClaimsData);
        } catch (JsonProcessingException e) {
            log.error(e);
            throw new GenericException(e.getMessage());
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
    @Cacheable(value = CacheConstants.CERTS_CACHE_NAME, key = "#root.methodName")
    public Map<String, JwtKey> certs() {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = urlBase + KeyCloakService.CERTS.getServiceUrl();

            HttpGet request = new HttpGet(pathPostToken);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                ObjectMapper objectMapper = new ObjectMapper();
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                KeyCloakCertListResponse certListResponse = objectMapper.readValue(responseToken, KeyCloakCertListResponse.class);
                Function<KeyCloakKeyResponse, JwtKey> conv = ((KeyCloakKeyResponse kck) -> keyCloakMapper.getObjectMapper().convert(kck));
                Function<JwtKey, String> func = (JwtKey::getId);
                return genericsMapper.convert(certListResponse.getKeys(), func, conv);
            } catch (Exception e) {
                log.error(e);
                throw new GenericException("Hubo un error no controlado al consultar los certificados.");
            }
        } catch (GenericException e) {
            throw e;
        } catch (UnknownHostException e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.KC_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e);
            throw new GenericException("Hubo un error no controlado al crear el cliente para la obtencion de certificados.");
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
            String pathPostToken = urlBase + KeyCloakService.CREATE_TOKEN.getServiceUrl();

            HttpPost postCreateToken = new HttpPost(pathPostToken);
            postCreateToken.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity httpEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
            postCreateToken.setEntity(httpEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(postCreateToken)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                CreateTokenServiceResponse createTokenResponse = new CreateTokenServiceResponse();
                ObjectMapper objectMapper = new ObjectMapper();

                String response = EntityUtils.toString(httpResponse.getEntity());
                switch (statusCode) {
                    case 200:
                        CreateTokenKCResponse createTokenKCResponse = objectMapper.readValue(response, CreateTokenKCResponse.class);
                        createTokenResponse.setTokenData(keyCloakMapper.getObjectMapper().convert(createTokenKCResponse));
                        createTokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.SUCCESS);
                        break;
                    case 400:
                        ErrorKCResponse errorKCResponse = getErrorKCResponse(objectMapper, response);

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.UNAUTHORIZED_CLIENT.getCode()))
                            throw new GenericException(String.format("Cliente no autorizado. Descripcion: %s", errorKCResponse.getErrorDescription()), HttpStatus.UNAUTHORIZED, ErrorKCResponse.Error.UNAUTHORIZED_CLIENT.name());

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_CLIENT.getCode()))
                            throw new GenericException(String.format("Cliente invalido. Descripcion: %s", errorKCResponse.getErrorDescription()), HttpStatus.UNAUTHORIZED, ErrorKCResponse.Error.INVALID_CLIENT.name());

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_REQUEST.getCode()))
                            throw new GenericException(String.format("Request invalido. Descripcion: %s", errorKCResponse.getErrorDescription()), HttpStatus.BAD_REQUEST, ErrorKCResponse.Error.INVALID_REQUEST.name());

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.UNSUPPORTED_GRANT_TYPE.getCode()))
                            throw new GenericException(String.format("Grant type invalido. Descripcion: %s", errorKCResponse.getErrorDescription()), HttpStatus.BAD_REQUEST, ErrorKCResponse.Error.UNSUPPORTED_GRANT_TYPE.name());

                        if (errorKCResponse.getError().equals(ErrorKCResponse.Error.INVALID_GRANT.getCode()))
                            createTokenResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.INVALID_DATA);
                        else throw new GenericException(errorKCResponse.getError());
                        break;
                    case 404:
                        throw new GenericException(String.format("Error en el request. Respuesta: %s", response), HttpStatus.NOT_FOUND, ErrorKCResponse.Error.NOT_FOUND_404.name());
                    case 503:
                        log.error(response);
                        throw new GenericException(DefaultMiddlewareError.KC_UNAVAILABLE);
                    default:
                        log.error(response);
                        throw new GenericException(DefaultMiddlewareError.KC_FAILURE);
                }
                return createTokenResponse;
            } catch (GenericException e) {
                throw e;
            } catch (UnknownHostException e) {
                log.error(e);
                throw new GenericException(DefaultMiddlewareError.KC_UNAVAILABLE);
            } catch (Exception e) {
                log.error(e);
                throw new GenericException(DefaultMiddlewareError.KC_FAILURE);
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.KC_FAILURE);
        }
    }

    private static ErrorKCResponse getErrorKCResponse(ObjectMapper objectMapper, String errorResponse) {
        ErrorKCResponse errorKCResponse;
        try {
            errorKCResponse = objectMapper.readValue(errorResponse, ErrorKCResponse.class);
        } catch (Exception e) {
            throw new GenericException(errorResponse);
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
    public JwtAccess parseJwtAccess(String token) throws JwtException, GenericException {
        if (sessionManager.isOnBlacklist(token))
            throw new GenericException(DefaultMiddlewareError.BLACKLISTED_ACCESS_JWT);

        JwtAccess jwtAccess = getSignedToken(token);

        if (!validateToken(jwtAccess)) throw new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);

        return jwtAccess;
    }

    /**
     * Convierte un JWT encodeado en un objeto JwtRefresh. Internamente valida los valores de los claims esperados y si esta correctamente firmado.
     *
     * @param token el JWT en base64.
     * @return JwtAccess con los datos del token provisto.
     */
    @Override
    public JwtRefresh parseJwtRefresh(String token) throws JwtException, GenericException {
        JwtRefresh jwtRefresh = getRefreshSignedToken(token);

        if (!validateToken(jwtRefresh)) throw new GenericException("El Refresh Token no es válido.");

        return jwtRefresh;
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

            return authorizedPartyValidation && issuerValidation;
        } catch (Exception e) {
            log.error("Hubo un error inesperado al validar el token.");
            return false;
        }
    }

    /**
     * Valida los claims esperados.
     *
     * @param jwtRefresh el token a validar.
     * @return el resultado de la validación.
     */
    private boolean validateToken(JwtRefresh jwtRefresh) {
        try {
            boolean issuerValidation = issuer.equals(jwtRefresh.getPayload().getIssuer());
            boolean authorizedPartyValidation = authorizedParty.equals(jwtRefresh.getPayload().getAuthorizedParty());

            return authorizedPartyValidation && issuerValidation;
        } catch (Exception e) {
            log.error("Hubo un error inesperado al validar el token.");
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
        } catch (ExpiredJwtException e) {
            throw new GenericException(DefaultMiddlewareError.EXPIRED_ACCESS_JWT);
        } catch (InvalidKeyException | SignatureException e) {
            throw new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);
        } catch (Exception e) {
            log.error("Hubo un error al obtener el Access JWT.");
            log.error(e);
            log.error(token);
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Devuelve el objeto JwtRefresh verificando la firma del mismo pero sin validación de los claims.
     *
     * @param token el JWT en base64.
     * @return JwtRefresh con los datos del token provisto.
     */
    private JwtRefresh getRefreshSignedToken(String token) {
        try {
            return keyCloakMapper.getJsonMapper().convertToJwtRefresh(token);
        } catch (Exception e) {
            log.error("Hubo un error al obtener el Refresh JWT.");
            log.error(e);
            log.error(token);
            throw new JwtException("Hubo un error al obtener el Refresh JWT.");
        }
    }

    @Override
    public boolean revokeAccessToken(String token) {
        sessionManager.blacklist(token);

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token_type_hint", "access_token"));
        params.add(new BasicNameValuePair("token", token));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));

        return revokeKeyCloak(params);
    }

    @Override
    public boolean revokeRefreshToken(String token) {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token_type_hint", "refresh_token"));
        params.add(new BasicNameValuePair("token", token));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));

        return revokeKeyCloak(params);
    }


    private boolean revokeKeyCloak(List<BasicNameValuePair> params) {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = urlBase + KeyCloakService.REVOKE_TOKEN.getServiceUrl();
            HttpPost postRevoke = new HttpPost(pathPostToken);
            postRevoke.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity httpEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
            postRevoke.setEntity(httpEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(postRevoke)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String response = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.OK.value()) return true;
                else {
                    log.error(response);
                    return false;
                }
            }
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public IntrospectTokenKCResponse introspect(String token) {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = urlBase + KeyCloakService.INTROSPECT.getServiceUrl();
            HttpPost request = new HttpPost(pathPostToken);
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity httpEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
            request.setEntity(httpEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String stringResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.OK.value()) {
                    IntrospectTokenKCResponse response = Util.stringToObject(stringResponse, IntrospectTokenKCResponse.class);
                    if (response.isActive())
                        return IntrospectTokenKCResponse.instance(IntrospectTokenKCResponse.Result.SUCCESS);
                    else
                        return IntrospectTokenKCResponse.instance(IntrospectTokenKCResponse.Result.EXPIRED_TOKEN);
                } else {
                    ErrorKCResponse errorResponse = Util.stringToObject(stringResponse, ErrorKCResponse.class);
                    if (Objects.equals(errorResponse.getError(), ErrorKCResponse.Error.INVALID_REQUEST.getCode()))
                        return IntrospectTokenKCResponse.instance(IntrospectTokenKCResponse.Result.INVALID_TOKEN);
                    else {
                        log.error(stringResponse);
                        throw new GenericException(DefaultMiddlewareError.KC_FAILURE);
                    }
                }
            }
        } catch (GenericException e) {
            throw e;
        } catch (UnknownHostException e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.KC_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.KC_FAILURE);
        }
    }
}
